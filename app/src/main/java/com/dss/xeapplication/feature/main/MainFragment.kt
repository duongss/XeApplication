package com.dss.xeapplication.feature.main


import android.app.Activity.RESULT_OK
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.base.ViewPagerAdapter
import com.dss.xeapplication.base.extension.showChildDialog
import com.dss.xeapplication.databinding.FragmentMainBinding
import com.dss.xeapplication.feature.diaglog.UpgradeVersionDialog
import com.dss.xeapplication.feature.main.page.HomeFragment
import com.dss.xeapplication.feature.main.viewmodel.MainViewModel
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(), UpgradeVersionDialog.OnUpdateAppListener {
    override fun bindingView() = FragmentMainBinding.inflate(layoutInflater)


    private lateinit var viewPagerAdapter: ViewPagerAdapter

    private lateinit var arrayTabFragment: ArrayList<BaseFragment<*>>

    private val viewModel by activityViewModels<MainViewModel>()

    companion object {
        const val PAGE_HOME = 0

        const val PAGE_TOOL = 1

        fun newInstance() = MainFragment()
    }

    private var firstJoin: Boolean = false

    private var blockUpdate = false

    private lateinit var appUpdateManager: AppUpdateManager

    val updateLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode != RESULT_OK) {
                blockUpdate = true
            }
        }

    override fun initConfig() {
        super.initConfig()
        initPage()
        binding.ivHome.isActivated = true
        appUpdateManager = AppUpdateManagerFactory.create(requireActivity())
        checkUpdateApp()

    }


    private fun initPage() {
        arrayTabFragment = arrayListOf(
            HomeFragment.newInstance(), HomeFragment.newInstance()
        )
        viewPagerAdapter = ViewPagerAdapter(
            childFragmentManager, lifecycle, arrayTabFragment
        )
        binding.vpData.isUserInputEnabled = false
        binding.vpData.offscreenPageLimit = arrayTabFragment.size
        binding.vpData.adapter = viewPagerAdapter
    }

    override fun initListener() {
        super.initListener()
        binding.vpData.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    PAGE_HOME -> {
                        binding.ivHome.isActivated = true
                        binding.ivTool.isActivated = false
                        binding.tvHome.clicked()
                        binding.tvTool.nclicked()
                    }
                    PAGE_TOOL -> {
                        binding.ivHome.isActivated = false
                        binding.ivTool.isActivated = true
                        binding.tvTool.clicked()
                        binding.tvHome.nclicked()
                    }

                }
            }
        })

        binding.btnHome.setOnClickListener {
//            viewModel.setCurrentPage(PAGE_HOME)
        }

        binding.btnTool.setOnClickListener {
//            viewModel.setCurrentPage(PAGE_TOOL)
        }

        backListener {
            requireActivity().finish()
        }
    }

    override fun initObserver() {
        super.initObserver()
        viewModel.currentPage.observe(this) {
            binding.vpData.currentItem = it
        }
    }

    private fun checkUpdateApp() {
        if (!firstJoin && !blockUpdate) {
            firstJoin = true
            val appUpdateInfoTask = appUpdateManager.appUpdateInfo
            appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                    lifecycleScope.launch {
                        showChildDialog(UpgradeVersionDialog.newInstance())
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability()
                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                ) {
                    // If an in-app update is already running, resume the update.
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        updateLauncher,
                        AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
                    )
                }
            }
    }

    override fun onUpdateApp() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener {
            appUpdateManager.startUpdateFlowForResult(
                it,
                updateLauncher,
                AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).setAllowAssetPackDeletion(true)
                    .build()
            )
        }

    }

}
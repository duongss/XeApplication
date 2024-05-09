package com.dss.xeapplication.ui.main


import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity.RESULT_OK
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.base.ViewPagerAdapter
import com.dss.xeapplication.base.extension.addFragment
import com.dss.xeapplication.base.extension.gone
import com.dss.xeapplication.base.extension.onAvoidDoubleClick
import com.dss.xeapplication.base.extension.showChildDialog
import com.dss.xeapplication.base.extension.visible
import com.dss.xeapplication.databinding.FragmentMainBinding
import com.dss.xeapplication.ui.compare.CompareFragment
import com.dss.xeapplication.ui.compare.ComparePreBottomDialog
import com.dss.xeapplication.ui.diaglog.UnlockRewardDialog
import com.dss.xeapplication.ui.diaglog.UpgradeVersionDialog
import com.dss.xeapplication.ui.main.page.home.HomeFragment
import com.dss.xeapplication.ui.main.page.setting.SettingFragment
import com.dss.xeapplication.ui.main.viewmodel.MainViewModel
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.wavez.p27_pdf_scanner.data.local.SharedPref
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(), UpgradeVersionDialog.OnUpdateAppListener,ComparePreBottomDialog.PreCompareListener,UnlockRewardDialog.UnlockForFreeListener {
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

    private var isViewMode: Boolean = false

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
            HomeFragment.newInstance(), SettingFragment.newInstance()
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
            viewModel.setCurrentPage(PAGE_HOME)
        }

        binding.btnTool.setOnClickListener {
            viewModel.setCurrentPage(PAGE_TOOL)
        }

        binding.btnCompare.onAvoidDoubleClick {
            showChildDialog(ComparePreBottomDialog.newInstance())
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

        viewModel.isHiddenBottom.observe(this) {
            if (it) openViewMode() else closeViewMode()
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

    private val durationViewMode = 1000L

    private val durationCloseViewMode = 500L
    private fun closeViewMode() {
        if (!isViewMode) {
            return
        }

        val translationYAnimator1 =
            ObjectAnimator.ofFloat(binding.bottomAppBar, "translationY", 300f, 0f)
        translationYAnimator1.duration = durationCloseViewMode

        val fadeIn1 = ObjectAnimator.ofFloat(binding.bottomAppBar, "alpha", 0f, 1f)
        fadeIn1.duration = durationCloseViewMode

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(translationYAnimator1, fadeIn1)

        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                binding.bottomAppBar.visible()
            }

            override fun onAnimationEnd(animation: Animator) {
                isViewMode = false
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })

        animatorSet.start()
    }

    private fun openViewMode() {
        if (isViewMode) {
            return
        }

        val translationYAnimator1 =
            ObjectAnimator.ofFloat(binding.bottomAppBar, "translationY", 0f, 300f)
        translationYAnimator1.duration = durationViewMode

        val fadeOut1 = ObjectAnimator.ofFloat(binding.bottomAppBar, "alpha", 1f, 0f)
        fadeOut1.duration = durationViewMode

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(translationYAnimator1, fadeOut1)

        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                isViewMode = true
            }

            override fun onAnimationEnd(animation: Animator) {
                binding.bottomAppBar.gone()
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })

        animatorSet.start()
    }

    override fun onNext() {
        if (SharedPref.isVip){
            onUnlockedFromUser()
        }else{
            showChildDialog(UnlockRewardDialog.newInstance())
        }
    }

    override fun onUnlockedFromUser() {
        viewModel.createListCompare()
        addFragment(CompareFragment.newInstance())
    }
}
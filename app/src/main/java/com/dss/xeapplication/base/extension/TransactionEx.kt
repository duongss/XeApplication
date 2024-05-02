package com.dss.xeapplication.base.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.dss.xeapplication.R
import com.dss.xeapplication.base.BaseActivity
import com.dss.xeapplication.base.BaseFragment


inline fun FragmentManager.inTransaction(
    stateChange: Boolean = true,
    isAnim: Boolean = true,
    animEnter: Int = R.anim.enter_from_left,
    animExit: Int = R.anim.exit_from_left,
    func: FragmentTransaction.() -> Unit
) {
    val fragmentTransaction = beginTransaction()
    if (isAnim) {
        fragmentTransaction.setCustomAnimations(animEnter, animExit)
    }
    fragmentTransaction.func()
    if (stateChange) {
        fragmentTransaction.commit()
    } else {
        fragmentTransaction.commitAllowingStateLoss()
    }
}

fun BaseActivity<*>.replaceFragment(
    fragment: Fragment,
    frameId: Int = R.id.frContainer,
    backStack: Boolean = true,
    isAnim: Boolean = true
) {
    supportFragmentManager.inTransaction(canChangeFragmentManagerState(), isAnim = isAnim) {
        replace(frameId, fragment, fragment.javaClass.simpleName)
        if (backStack) addToBackStack(fragment.javaClass.simpleName)
    }
}

fun BaseActivity<*>.addFragment(
    fragment: Fragment,
    frameId: Int = R.id.frContainer,
    isSave: Boolean = false,
    isAnim: Boolean = true
) {
    val manager = supportFragmentManager
    manager.inTransaction(canChangeFragmentManagerState(), isAnim = isAnim) {
        if (!isSave) {
            add(frameId, fragment, fragment.javaClass.simpleName)
        } else {
            manager.findFragmentByTag(fragment.javaClass.simpleName)?.let {
                if (it.isAdded)
                    show(fragment)
            } ?: kotlin.run {
                add(frameId, fragment, fragment.javaClass.simpleName)
            }
        }
    }
}

fun BaseFragment<*>.replaceFragment(
    fragment: Fragment,
    frameId: Int = R.id.frContainer,
    backStack: Boolean = true,
    isAnim: Boolean = true
) {
    requireActivity().supportFragmentManager.inTransaction(
        canChangeFragmentManagerState(),
        isAnim
    ) {
        replace(frameId, fragment, fragment.javaClass.simpleName)
        if (backStack) addToBackStack(fragment.javaClass.simpleName)
    }
}


fun BaseFragment<*>.addFragment(
    fragment: Fragment,
    frameId: Int = R.id.frContainer,
    isSave: Boolean = false,
    isAnim: Boolean = true
) {
    val manager = requireActivity().supportFragmentManager
    manager.inTransaction(canChangeFragmentManagerState(), isAnim) {
        if (!isSave) {
            add(frameId, fragment, fragment.javaClass.simpleName)
        } else {
            manager.findFragmentByTag(fragment.javaClass.simpleName)?.let {
                if (it.isAdded) show(it)
            } ?: kotlin.run {
                add(frameId, fragment, fragment.javaClass.simpleName)
            }
        }
    }
}

/**
 * using when add fragment
 */
fun Fragment.removeSelf() {
    requireActivity().supportFragmentManager.inTransaction {
        remove(this@removeSelf)
    }
}

fun Fragment.removeFragmentByTag(fragmentRemove: Fragment) {
    val manager = requireActivity().supportFragmentManager
    manager.findFragmentByTag(fragmentRemove::class.simpleName)?.let {
        manager.beginTransaction().remove(fragmentRemove).commit()
    }
}

fun Fragment.hide() {
    requireActivity().supportFragmentManager.inTransaction {
        if (this@hide.isAdded)
            hide(this@hide)
    }
}

fun Fragment.show() {
    requireActivity().supportFragmentManager.inTransaction {
        if (this@show.isAdded)
            show(this@show)
    }
}

fun Fragment.backStackPress() {
    val manager = requireActivity().supportFragmentManager
    if (manager.backStackEntryCount >= 1) {
        manager.popBackStack()
    } else {
        requireActivity().finish()
    }
}

fun AppCompatActivity.backStackPress() {
    val manager = supportFragmentManager
    if (manager.backStackEntryCount >= 1) {
        manager.popBackStack()
    } else {
        finish()
    }
}

fun Fragment.showChildDialog(dialogFragment: DialogFragment) {
    lifecycleScope.launchWhenResumed {
        if (childFragmentManager.findFragmentByTag(dialogFragment::class.simpleName) != null) {
            return@launchWhenResumed
        }
        dialogFragment.show(childFragmentManager, dialogFragment.javaClass.simpleName)
    }
}
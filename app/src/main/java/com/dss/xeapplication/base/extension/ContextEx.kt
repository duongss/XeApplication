package com.dss.xeapplication.base.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.OpenableColumns
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.dss.xeapplication.R
import com.dss.xeapplication.base.BaseActivity
import java.io.File


fun Context.getDisplayWidth() = resources.displayMetrics.widthPixels

fun Context.getDisplayHeight() = resources.displayMetrics.heightPixels

fun Context.dpToPx(dp: Float) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, dp, this.resources.displayMetrics
).toInt()

fun Context.toastMsg(msg: Int) =
    Toast.makeText(this, this.getString(msg), Toast.LENGTH_SHORT).show()

fun Context.toastMsg(msg: String) =
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

@ColorInt
fun Context.color(@ColorRes res: Int): Int {
    return ContextCompat.getColor(this, res)
}

fun Context.drawable(@DrawableRes res: Int): Drawable? {
    return ContextCompat.getDrawable(this, res)
}

fun Context.tintedDrawable(@DrawableRes drawableId: Int, @ColorRes colorId: Int): Drawable? {
    val tint: Int = color(colorId)
    val drawable = drawable(drawableId)
    drawable?.mutate()
    drawable?.let {
        it.mutate()
        DrawableCompat.setTint(it, tint)
    }
    return drawable
}

fun Context.string(@StringRes res: Int): String {
    return getString(res)
}

fun Context.hideSoftKeyboard(v: View) {
    val inputMethodManager: InputMethodManager =
        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
}

fun Context.showSoftKeyboard(v: View) {
    try {
        v.post {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
        }
    } catch (e: Exception) {

    }
}

fun AppCompatActivity.hideSoftKeyboard() {
    val inputMethodManager = getSystemService(
        Activity.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    if (inputMethodManager.isAcceptingText) {
        inputMethodManager.hideSoftInputFromWindow(
            currentFocus!!.windowToken,
            0
        )
    }
}

fun Fragment.hideSoftKeyboard() {
    val inputMethodManager = requireActivity().getSystemService(
        Activity.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    if (inputMethodManager.isAcceptingText) {
        inputMethodManager.hideSoftInputFromWindow(
            requireActivity().currentFocus!!.windowToken,
            0
        )
    }
}

inline fun <reified VM : ViewModel> Fragment.fragmentViewModels() =
    viewModels<VM>({ requireParentFragment() })

inline fun <reified T : Activity> Context.runActivity(block: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class.java).apply(block))
}

inline fun <reified T : Activity> Fragment.runActivity(block: Intent.() -> Unit = {}) {
    requireActivity().runActivity<T>(block)
}

fun Context.getResourceColor(color: Int): Int {
    return ContextCompat.getColor(this, color)
}

fun Context.linkAppStore() {
    val uri: Uri = Uri.parse("market://details?id=${this.packageName}")
    val goToMarket = Intent(Intent.ACTION_VIEW, uri)
    goToMarket.addFlags(
        Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK
    )
    try {
        this.startActivity(goToMarket)
    } catch (e: ActivityNotFoundException) {
        this.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=${this.packageName}")
            )
        )
    }
}

fun Context.getFileName(uri: Uri): String? = when (uri.scheme) {
    ContentResolver.SCHEME_CONTENT -> getContentFileName(uri)
    else -> uri.path?.let(::File)?.name
}

private fun Context.getContentFileName(uri: Uri): String? = runCatching {
    contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        cursor.moveToFirst()
        return@use cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME).let(cursor::getString)
    }
}.getOrNull()



fun BaseActivity<*>.showDialog(dialogFragment: DialogFragment) {
    lifecycleScope.launchWhenResumed {
        if (supportFragmentManager.findFragmentByTag(dialogFragment::class.simpleName) != null) {
            return@launchWhenResumed
        }
        if (canChangeFragmentManagerState()) {
            dialogFragment.show(supportFragmentManager, dialogFragment.javaClass.simpleName)
        } else {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.add(dialogFragment, dialogFragment.javaClass.simpleName)
            ft.commitAllowingStateLoss()
        }
    }
}




fun Context.gotoWebsite(link: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(link)
    startActivity(intent)
}


fun Fragment.toastMsg(msg: Int) =
    Toast.makeText(requireActivity(), this.getString(msg), Toast.LENGTH_SHORT).show()
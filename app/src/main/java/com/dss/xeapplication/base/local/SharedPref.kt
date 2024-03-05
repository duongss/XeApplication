package com.wavez.p27_pdf_scanner.data.local

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import com.dss.xeapplication.App
import com.dss.xeapplication.base.extension.setBoolean
import com.dss.xeapplication.base.extension.setInt
import com.dss.xeapplication.base.extension.setLong
import java.util.Locale


object SharedPref {
    private const val PREFERENCE_FILE_KEY = "preference_file_key"

    private const val CLOUD_KEY = "preference_cloud_api_key"

    private const val LOADED_INTER_NONE_REWARD = "loaded_inter_none_reward"

    private const val SHOW_LAST_TIME_INTER_NONE_REWARD = "show_last_time_inter_none_reward"

    private const val LOADED_INTER_REWARD = "loaded_inter_reward"
    private const val SHOW_LAST_TIME_INTER_REWARD = "show_last_time_inter_reward"

    private const val SHOW_LAST_TIME_OPEN_AD = "show_last_time_open_ad"
    private const val FILTER_SHARE_KEY = "filter_share_key"

    const val KEY_INTERSTITIAL_AD_LOADED = "interstitial_ad_loaded"

    private const val KEY_ACCEPT_ADS = "accept_ads"

    private const val KEY_ACCEPT_OPEN_APP = "open_app"

    const val INTERSTITIAL_DISPLAY = "interstitial_display"

    const val APP_OPEN_AD_DISPLAY = "app_open_ad_display"

    const val OPEN_APP_FIRST_TIME = "open_app_first_time"

    private const val DATE_CURRENT_NOTI = "date_current"

    private const val VIEW_FILE_COUNT = "view_file_count"

    private const val VIP = "vip"

    private const val RATE_APP = "rate_app"

    private const val SELECT_RULE = "select_rule"

    const val RULE_NONE = 0

    const val RULE_HOME = 1

    const val RULE_CAMERA = 2

    const val RULE_WALKTHROUGH = 3

    private const val TYPE_GRID = "type_grid"

    private const val WIDTH_CAM = "width_cam"

    private const val HEIGHT_CAM = "height_cam"

    const val TYPE_GRID_1 = 1

    const val TYPE_GRID_2 = 2

    const val TYPE_GRID_3 = 3

    private const val FIRST_PRINT = "first_print"

    val oneDay = 86400000
    val sevenDay = oneDay * 7

    private const val RATE_LATER = "rate_later"

    private const val SHOW_RATE_TIME = "show_rate_time"

    private const val CLOSE_APP_FIRST_TIME = "close_app_first_time"

    private const val START_WITH_CAMERA = "start_with_camera"

    private const val AUTO_SAVE_SCAN = "auto_save_scan"

    private const val DEFAULT_FILTER = "default_filter"

    private const val LANGUAGE_SETTING = "language_setting"

    private const val LIST_LANGUAGE_EXTRACT = "list_language_extract"

    private const val MIN_TIME_GAP_KEY = "min_time_gap_key"

    private const val MAX_TIME_GAP_KEY = "max_time_gap_key"

    private const val MONETIZATION_KEY = "monetization_key"

    private const val IGNORE_GAP_TIME_RELOAD_NUMBER = "ignore_gap_time_reload_number"

    private const val NUMBER_NATIVE_NEED_LOAD_KEY = "number_native_need_load_key"

    private const val FIRST_TIME_ORC_EXTRACT_LANGUAGE = "first_time_orc_extract_language"

    private const val FIRST_TIME_SCAN_SINGLE = "first_time_scan_single"

    private const val FIRST_TIME_SCAN_MULTI = "first_time_scan_multi"

    private const val FIRST_TIME_SCAN_TO_TEXT = "first_time_scan_to_text"

    private const val FIRST_TIME_IMPORT_PHOTO = "first_time_import_photo"

    private const val LIST_TUTORIAL_ID = "list_tutorial_id"

    private const val TIME_VIEW_FILE = "time_open_view"

    private const val FILE_CONVERT_NEW = "file_convert_new"


    // mở sang public để dùng như 1 biến ở class khác có thể gây ra memory leak
    private fun getSharedPreferences() =
        App.appContext().getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)

    val editor: SharedPreferences.Editor
        get() = getSharedPreferences().edit()

    var isRate: Boolean
        get() = getSharedPreferences().getBoolean(RATE_APP, false)
        set(value) {
            getSharedPreferences().setBoolean(RATE_APP, value)
        }

    fun rateLater() {
        val editor = getSharedPreferences().edit()
        editor.putBoolean(RATE_LATER, true)
        editor.putLong(SHOW_RATE_TIME, System.currentTimeMillis())
        editor.apply()
    }

    fun isCloseAppFirstTime(): Boolean {
        val rate = isRate
        return getSharedPreferences().getBoolean(CLOSE_APP_FIRST_TIME, true) && !rate
    }

    fun setCloseAppFirstTime() {
        val editor = getSharedPreferences().edit()
        editor.putBoolean(CLOSE_APP_FIRST_TIME, false)
        editor.apply()
    }

    var isVip: Boolean
        get() = getSharedPreferences().getBoolean(VIP, false)
        set(value) {
            getSharedPreferences().setBoolean(VIP, value)
        }

    var isSelectedRule: Int
        get() = getSharedPreferences().getInt(SELECT_RULE, RULE_WALKTHROUGH)
        set(value) {
            getSharedPreferences().setInt(SELECT_RULE, value)
        }

    var typeGrid: Int
        get() = getSharedPreferences().getInt(TYPE_GRID, TYPE_GRID_1)
        set(value) {
            getSharedPreferences().setInt(TYPE_GRID, value)
        }

    var isFirstPrint: Boolean
        get() = getSharedPreferences().getBoolean(FIRST_PRINT, false)
        set(value) {
            getSharedPreferences().setBoolean(FIRST_PRINT, value)
        }

    var interNoneRewardLoaded: Long
        get() = getSharedPreferences().getLong(LOADED_INTER_NONE_REWARD, 0)
        set(value) {
            getSharedPreferences().edit().putLong(LOADED_INTER_NONE_REWARD, value).apply()
        }

    var interNoneRewardShowLastTime: Long
        get() = getSharedPreferences().getLong(SHOW_LAST_TIME_INTER_NONE_REWARD, 0)
        set(value) {
            getSharedPreferences().edit().putLong(SHOW_LAST_TIME_INTER_NONE_REWARD, value).apply()
        }

    var interRewardLoaded: Long
        get() = getSharedPreferences().getLong(LOADED_INTER_REWARD, 0)
        set(value) {
            getSharedPreferences().edit().putLong(LOADED_INTER_REWARD, value).apply()
        }

    var interRewardShowLastTime: Long
        get() = getSharedPreferences().getLong(SHOW_LAST_TIME_INTER_REWARD, 0)
        set(value) {
            getSharedPreferences().edit().putLong(SHOW_LAST_TIME_INTER_REWARD, value).apply()
        }

    var openAdShowLastTime: Long
        get() = getSharedPreferences().getLong(SHOW_LAST_TIME_OPEN_AD, 0)
        set(value) {
            getSharedPreferences().edit().putLong(SHOW_LAST_TIME_OPEN_AD, value).apply()
        }


    var interstitialDisplay: Long
        get() = getSharedPreferences().getLong(INTERSTITIAL_DISPLAY, 0)
        set(value) {
            getSharedPreferences().edit().putLong(INTERSTITIAL_DISPLAY, value).apply()
        }

    var appOpenDisplay: Long
        get() = getSharedPreferences().getLong(APP_OPEN_AD_DISPLAY, 0)
        set(value) {
            getSharedPreferences().edit().putLong(APP_OPEN_AD_DISPLAY, value).apply()
        }



    var widthCam: Int
        get() = getSharedPreferences().getInt(WIDTH_CAM, 0)
        set(value) {
            getSharedPreferences().setInt(WIDTH_CAM, value)
        }
    var heightCam: Int
        get() = getSharedPreferences().getInt(HEIGHT_CAM, 0)
        set(value) {
            getSharedPreferences().setInt(HEIGHT_CAM, value)
        }

    var isStartWithCamera: Boolean
        get() = getSharedPreferences().getBoolean(START_WITH_CAMERA, false)
        set(value) {
            getSharedPreferences().setBoolean(START_WITH_CAMERA, value)
        }

    var isAutoSave: Boolean
        get() = getSharedPreferences().getBoolean(AUTO_SAVE_SCAN, false)
        set(value) {
            getSharedPreferences().setBoolean(AUTO_SAVE_SCAN, value)
        }


    fun getLanguage() =
        getSharedPreferences().getString(LANGUAGE_SETTING, getPhoneLocale().language)

    fun setLanguage(language: String) {
        val editor = getSharedPreferences().edit()
        editor.putString(LANGUAGE_SETTING, language)
        editor.apply()
    }

    fun getPhoneLocale(): Locale {
        var locale = Locale("en")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (App.appContext().resources.configuration.locales.size() > 0) locale =
                App.appContext().resources.configuration.locales.get(0)
        } else {
            locale = App.appContext().resources.configuration.locale
        }
        return locale
    }


    var minTimeGap: Long
        get() = getSharedPreferences().getLong(MIN_TIME_GAP_KEY, 5000)
        set(value) {
            editor.putLong(MIN_TIME_GAP_KEY, value).apply()
        }

    var maxTimeGap: Long
        get() = getSharedPreferences().getLong(MAX_TIME_GAP_KEY, 30000)
        set(value) {
            editor.putLong(MAX_TIME_GAP_KEY, value).apply()
        }

    var monetization: Boolean
        get() = getSharedPreferences().getBoolean(MONETIZATION_KEY, false)
        set(value) {
            editor.putBoolean(MONETIZATION_KEY, value).apply()
        }

     var ignoreGapThresold: Int
        get() = getSharedPreferences().getInt(IGNORE_GAP_TIME_RELOAD_NUMBER, 2)
        set(value) {
            editor.putInt(IGNORE_GAP_TIME_RELOAD_NUMBER, value).apply()
        }
    var numberNativeNeedLoad: Int
        get() = getSharedPreferences().getInt(NUMBER_NATIVE_NEED_LOAD_KEY, 2)
        set(value) {
            editor.putInt(NUMBER_NATIVE_NEED_LOAD_KEY, value).apply()
        }

    var isFirstTimeOrc: Boolean
        get() = getSharedPreferences().getBoolean(FIRST_TIME_ORC_EXTRACT_LANGUAGE, true)
        set(value) {
            getSharedPreferences().setBoolean(FIRST_TIME_ORC_EXTRACT_LANGUAGE, value)
        }

    var isFirstTimeImportPhoto: Boolean
        get() = getSharedPreferences().getBoolean(FIRST_TIME_IMPORT_PHOTO, true)
        set(value) {
            getSharedPreferences().setBoolean(FIRST_TIME_IMPORT_PHOTO, value)
        }


    fun getViewFileCount(): Long {
        return getSharedPreferences().getLong(VIEW_FILE_COUNT, 0)
    }

    fun setViewFileCount() {
        val count = getSharedPreferences().getLong(VIEW_FILE_COUNT, 0)
        val editor = getSharedPreferences().edit()
        editor.putLong(VIEW_FILE_COUNT, count + 1L)
        editor.apply()
    }

    fun resetViewFileCount() {
        getSharedPreferences().setLong(VIEW_FILE_COUNT, 0)
    }

    var timeViewOpen: Long
        get() = getSharedPreferences().getLong(TIME_VIEW_FILE, 0L)
        set(value) {
            getSharedPreferences().setLong(TIME_VIEW_FILE, value)
        }

    var fileNewConvert: Boolean
        get() = getSharedPreferences().getBoolean(FILE_CONVERT_NEW, false)
        set(value) {
            getSharedPreferences().setBoolean(FILE_CONVERT_NEW, value)
        }
}
package com.dss.xeapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sorter(
    var usingTime: Boolean = true,
    var usingName: Boolean = false,
    var timeFilter: TimeFilter = TimeFilter.NEW_TO_OLDEST,
    var nameFilter: NameFilter = NameFilter.A_Z
) : Parcelable {

    enum class TimeFilter {
        OLDEST_TO_NEW,
        NEW_TO_OLDEST
    }

    enum class NameFilter {
        A_Z,
        Z_A
    }
}
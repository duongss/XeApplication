package com.dss.xeapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarFee(
    val listedPrice: Long = 0L,               // Giá niêm yết
    val registration: Long = 0L,           // Phí đăng ký
    val licensePlate: Long = 0L,           // Phí biển số
    val roadMaintenance: Long = 0L,        // Phí bảo trì đường bộ 1 năm
    val civilLiabilityInsurance: Long = 0L,   // Bảo hiểm trách nhiệm dân sự
    val registrationCertificate: Long = 0L // Phí chứng nhận đăng ký
) : Parcelable
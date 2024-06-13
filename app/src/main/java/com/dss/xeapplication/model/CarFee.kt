package com.dss.xeapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarFee(
    val locationFee: String = "",
    val registration: Long = 0L,           // Phí đăng ký
    val licensePlate: Long = 0L,           // Phí biển số
    val roadMaintenance: Long = 0L,        // Phí bảo trì đường bộ 1 năm
    val civilLiabilityInsurance: Long = 0L,   // Bảo hiểm trách nhiệm dân sự
    val registrationCertificate: Long = 0L, // Phí chứng nhận đăng ký
    val unitPrice: String = "Vnd"
) : Parcelable

object LocationFee {
    val HANOI = CarFee("Hà nội", 81600000, 20000000, 1560000, 873400, 340000)
    val HCM = CarFee("TP Hồ Chí Minh", 68000000, 20000000, 1560000, 873400, 340000)
    val HAI_PHONG = CarFee("Hải Phòng", 81600000, 10000000, 1560000, 873400, 340000)
    val THANH_HOA = CarFee("Thanh Hóa", 68000000, 10000000, 1560000, 873400, 340000)
    val HUE = CarFee("Lào Cai", 68000000, 10000000, 1560000, 873400, 340000)
    val DA_NANG = CarFee("Đà Nẵng", 68000000, 10000000, 1560000, 873400, 340000)
    val DIEN_BIEN = CarFee("Điện Biên", 68000000, 10000000, 1560000, 873400, 340000)
    val OTHER = CarFee("Tỉnh thành khác", 68000000, 10000000, 1560000, 873400, 340000)

    val listLocationFee = listOf(HANOI, HCM, HAI_PHONG, THANH_HOA, HUE, DA_NANG, DIEN_BIEN, OTHER)
}
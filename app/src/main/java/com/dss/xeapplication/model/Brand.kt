package com.dss.xeapplication.model


data class Brand(
    var name: NameBrand,
    var image: String,
    var listCar: ArrayList<Car> = arrayListOf(),
    var isSelected : Boolean = false
) {


    enum class NameBrand {
        All, Mitsubishi, Toyota, Honda, Ford, Huyndai, Kia, Vinfast, Mazda,BMW, Audi
    }

}

object BrandProvider {
    val ALL = Brand(
        Brand.NameBrand.All,
        ""
    )

    val TOYOTA = Brand(
        Brand.NameBrand.Toyota,
        "https://firebasestorage.googleapis.com/v0/b/xeapp-2a308.appspot.com/o/brand%2Ftoyota.png?alt=media&token=56c62477-6253-4fc3-8fbe-6925688d0375"
    )
    val HONDA = Brand(
        Brand.NameBrand.Honda,
        "https://firebasestorage.googleapis.com/v0/b/xeapp-2a308.appspot.com/o/brand%2FHonda.png?alt=media&token=3f770279-e606-4ac0-b755-c7a5633dfbb8"
    )
    val MITSUBISHI = Brand(
        Brand.NameBrand.Mitsubishi,
        "https://firebasestorage.googleapis.com/v0/b/xeapp-2a308.appspot.com/o/brand%2FMitsubishi.jpg?alt=media&token=b7fdfb1b-fc7d-4259-ba22-bd73c23a811d"
    )
    val HUYNDAI = Brand(
        Brand.NameBrand.Huyndai,
        "https://firebasestorage.googleapis.com/v0/b/xeapp-2a308.appspot.com/o/brand%2FHuyndai.jpg?alt=media&token=46d0bfac-7e38-41a1-bb51-e0fbd5375ac6"
    )
    val KIA = Brand(
        Brand.NameBrand.Kia,
        "https://firebasestorage.googleapis.com/v0/b/xeapp-2a308.appspot.com/o/brand%2Fkia.png?alt=media&token=35512a09-d204-4369-8c0a-c0eb05cb8bd9"
    )
    val VINFAST = Brand(
        Brand.NameBrand.Vinfast,
        "https://firebasestorage.googleapis.com/v0/b/xeapp-2a308.appspot.com/o/brand%2Fvinfast.png?alt=media&token=d3648af8-f645-4dae-af6d-19c69d6c2546"
    )
    val MAZDA = Brand(
        Brand.NameBrand.Mazda,
        "https://firebasestorage.googleapis.com/v0/b/xeapp-2a308.appspot.com/o/brand%2FMazda.png?alt=media&token=32836ab3-7718-4257-9f45-5ebbb2da2a49"
    )
    val FORD = Brand(
        Brand.NameBrand.Ford,
        "https://firebasestorage.googleapis.com/v0/b/xeapp-2a308.appspot.com/o/brand%2Fford.png?alt=media&token=4f658240-2df6-4876-a991-3cbb35841993"
    )

    val BMW = Brand(
        Brand.NameBrand.BMW,
        "https://firebasestorage.googleapis.com/v0/b/xeapp-2a308.appspot.com/o/brand%2Fbmw.png?alt=media&token=6360dea4-dd99-43f4-b7f8-9b8b1cdf1452"
    )

    val AUDI = Brand(
        Brand.NameBrand.Audi,
        "https://firebasestorage.googleapis.com/v0/b/xeapp-2a308.appspot.com/o/brand%2Faudi.jpg?alt=media&token=6cde2877-211a-4f09-941e-4f21b0aa115a"
    )

    val listBrand = arrayListOf(ALL, MITSUBISHI, TOYOTA, HONDA, HUYNDAI, KIA, VINFAST, MAZDA, FORD,BMW,AUDI)
}
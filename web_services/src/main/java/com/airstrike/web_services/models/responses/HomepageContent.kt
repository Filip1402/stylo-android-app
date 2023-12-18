import com.google.gson.annotations.SerializedName

data class HomepageContent(
    @SerializedName("images") val images: List<HeroImage>
)

data class HeroImage(
    @SerializedName("hero-img") val heroImg: HeroImageDetails
)

data class HeroImageDetails(
    @SerializedName("url") val url: String
)
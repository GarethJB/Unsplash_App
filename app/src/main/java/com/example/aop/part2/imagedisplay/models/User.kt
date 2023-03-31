package com.example.aop.part2.imagedisplay.models


import com.google.gson.annotations.SerializedName

data class User(
    val bio: String?,
    val id: String?,
    @SerializedName("instagram_username")
    val instagramUsername: String?,
    val links: LinksX?,
    val location: String?,
    val name: String?,
    @SerializedName("portfolio_url")
    val portfolioUrl: String?,
    @SerializedName("total_collections")
    val totalCollections: Int?,
    @SerializedName("total_likes")
    val totalLikes: Int?,
    @SerializedName("total_photos")
    val totalPhotos: Int?,
    @SerializedName("twitter_username")
    val twitterUsername: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    val username: String?,
    @SerializedName("profile_image") var profileImage : ProfileImageData = ProfileImageData()
)


data class ProfileImageData (
    @SerializedName("small"  ) var small  : String = "",
    @SerializedName("medium" ) var medium : String = "",
    @SerializedName("large"  ) var large  : String = ""
)
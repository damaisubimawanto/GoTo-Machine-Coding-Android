package com.gopay.data.responses

import com.google.gson.annotations.SerializedName
import com.gopay.domain.models.RepositoryModel
import com.gopay.extensions.orZero

/**
 * Created by damai007 on 04/December/2023
 */
class RepositoryResponse {
    @SerializedName("author")
    var author: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("avatar")
    var avatar: String? = null

    @SerializedName("url")
    var url: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("language")
    var language: String? = null

    @SerializedName("languageColor")
    var languageColor: String? = null

    @SerializedName("stars")
    var stars: Int? = null

    @SerializedName("forks")
    var forks: Int? = null

    @SerializedName("currentPeriodStars")
    var currentPeriodStars: Int? = null

    fun convertToDataModel(): RepositoryModel {
        return RepositoryModel(
            name = this.name,
            author = this.author,
            description = this.description,
            stars = this.stars.orZero(),
            forks = this.forks.orZero(),
            language = this.language,
            avatar = this.avatar
        )
    }
}
package com.gopay.data.responses

import com.google.gson.annotations.SerializedName
import com.gopay.domain.models.RepositoryModel
import com.gopay.extensions.orZero

/**
 * Created by damai007 on 04/December/2023
 */
class RepositoryResponse {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("owner")
    var owner: OwnerResponse? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("language")
    var language: String? = null

    @SerializedName("forks_count")
    var forksCount: Int? = null

    @SerializedName("watchers")
    var watchers: Int? = null

    fun map(): RepositoryModel {
        return RepositoryModel(
            id = this.id.orZero(),
            name = this.name,
            description = this.description
        )
    }
}

class OwnerResponse {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("avatar_url")
    var avatarUrl: String? = null
}
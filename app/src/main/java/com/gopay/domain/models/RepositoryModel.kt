package com.gopay.domain.models

/**
 * Created by damai007 on 04/December/2023
 */
data class RepositoryModel(
    val name: String?,
    val description: String?,
    val stars: Int,
    val forks: Int,
    val language: String?
)
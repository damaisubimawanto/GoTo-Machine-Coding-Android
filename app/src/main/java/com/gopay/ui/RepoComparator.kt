package com.gopay.ui

import androidx.recyclerview.widget.DiffUtil
import com.gopay.domain.models.RepositoryModel

/**
 * Created by damai007 on 04/December/2023
 */
object RepoComparator : DiffUtil.ItemCallback<RepositoryModel>() {

    override fun areItemsTheSame(oldItem: RepositoryModel, newItem: RepositoryModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RepositoryModel, newItem: RepositoryModel): Boolean {
        return oldItem == newItem
    }
}
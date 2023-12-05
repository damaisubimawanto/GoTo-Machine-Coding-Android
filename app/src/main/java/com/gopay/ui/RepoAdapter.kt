package com.gopay.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gopay.databinding.ItemLayoutRepoBinding
import com.gopay.domain.models.RepositoryModel
import com.gopay.base.extensions.loadImage

/**
 * Created by damai007 on 04/December/2023
 */
class RepoAdapter : ListAdapter<RepositoryModel, RepoAdapter.ViewHolder>(
    RepoComparator
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            binding = ItemLayoutRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(data = currentList[position])
    }

    inner class ViewHolder(
        private val binding: ItemLayoutRepoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: RepositoryModel) {
            with(binding) {
                ivThumbnail.loadImage(url = data.avatar)
                tvAuthor.text = data.author
                tvRepositoryName.text = data.name
                tvRepositoryDescription.text = data.description
                tvStarCount.text = "${data.stars}"
                tvForkCount.text = "${data.forks}"

                val languageText = if (data.language.isNullOrBlank()) {
                    "-"
                } else {
                    data.language
                }
                tvLanguage.text = languageText
            }
        }
    }
}
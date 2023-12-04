package com.gopay.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gopay.R
import com.gopay.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: MainViewModel by inject()

    private lateinit var myAdapter: RepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myAdapter = RepoAdapter()
        binding.rvRepos.adapter = myAdapter

        observeLiveData()
        viewModel.getRepositories()
    }

    private fun observeLiveData() {
        viewModel.repositoryListLiveData.observe(this) {
            myAdapter.submitList(it)
        }

        viewModel.errorLiveData.observe(this) {

        }
    }
}

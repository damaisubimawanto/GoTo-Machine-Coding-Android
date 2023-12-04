package com.gopay.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import com.gopay.R
import com.gopay.customviews.FullScreenViewType
import com.gopay.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: MainViewModel by inject()

    private lateinit var myAdapter: RepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
        observeLiveData()
        viewModel.getRepositories()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sortByWatcher -> {
                viewModel.getRepositoriesByWatcherFilter()
                true
            }
            R.id.sortByFork -> {
                viewModel.getRepositoresByForkFilter()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupViews() {
        with(binding.rvRepos) {
            myAdapter = RepoAdapter()
            adapter = myAdapter
        }
    }

    private fun observeLiveData() {
        viewModel.repositoryListLiveData.observe(this) {
            myAdapter.submitList(it)
        }

        viewModel.loadingLiveData.observe(this) { isLoading ->
            binding.viewFullScreen.showOrHide(
                type = FullScreenViewType.LoadingView,
                isShowing = isLoading
            )
        }

        viewModel.errorLiveData.observe(this) {
            it.getContentIfNotHandled()?.let { isError ->
                binding.viewFullScreen.showOrHide(
                    type = FullScreenViewType.ErrorView,
                    isShowing = isError
                )
            }
        }
    }
}

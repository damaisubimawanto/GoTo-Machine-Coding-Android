package com.gopay.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.isVisible
import com.gopay.R
import com.gopay.base.customviews.FullScreenViewType
import com.gopay.databinding.ActivityMainBinding
import com.gopay.domain.models.SortMenuType
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: MainViewModel by inject()

    private lateinit var myAdapter: RepoAdapter
    private var mMenuInflater: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
        observeLiveData()
        viewModel.getRepositories()
    }

    override fun onDestroy() {
        mMenuInflater = null
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_option_menu, menu)
        mMenuInflater = menu
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
            R.id.clearSort -> {
                viewModel.getDefaultRepositories()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupViews() {
        setSupportActionBar(binding.toolbar)

        with(binding.rvRepos) {
            myAdapter = RepoAdapter()
            adapter = myAdapter
        }
    }

    private fun observeLiveData() {
        viewModel.repositoryListLiveData.observe(this) {
            if (viewModel.isReset) {
                viewModel.isReset = false
                myAdapter.submitList(null)
            }
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
                binding.rvRepos.isVisible = !isError
            }
        }

        viewModel.sortMenuLiveData.observe(this) { menuType ->
            when (menuType) {
                is SortMenuType.SortByWatcher -> {
                    mMenuInflater?.also { menu ->
                        menu.findItem(R.id.sortByWatcher).setVisible(false)
                        menu.findItem(R.id.sortByFork).setVisible(true)
                        menu.findItem(R.id.clearSort).setVisible(true)
                    }
                }
                is SortMenuType.SortByFork -> {
                    mMenuInflater?.also { menu ->
                        menu.findItem(R.id.sortByWatcher).setVisible(true)
                        menu.findItem(R.id.sortByFork).setVisible(false)
                        menu.findItem(R.id.clearSort).setVisible(true)
                    }
                }
                is SortMenuType.ClearSort -> {
                    mMenuInflater?.also { menu ->
                        menu.findItem(R.id.clearSort).setVisible(false)
                        menu.findItem(R.id.sortByWatcher).setVisible(true)
                        menu.findItem(R.id.sortByFork).setVisible(true)
                    }
                }
            }
        }
    }
}

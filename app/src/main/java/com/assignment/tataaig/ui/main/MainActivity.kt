package com.assignment.tataaig.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.TorrMonk.extension.setVisibility
import com.assignment.tataaig.R
import com.assignment.tataaig.adapter.MovieAdapter
import com.assignment.tataaig.adapter.MovieAdapterListener
import com.assignment.tataaig.databinding.ActivityMainBinding
import com.assignment.tataaig.model.PopularMovieResponseModel
import com.assignment.tataaig.ui.detail.DetailActivity
import com.assignment.tataaig.util.CustomPaginationRecyclerView

class MainActivity : AppCompatActivity(), MovieAdapterListener, MainActivityContract.View {

    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var viewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupToolBar("Popular Movies")
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)


        // Init Adapter
        binding.rvMovieList.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        binding.rvMovieList.layoutManager = layoutManager
        movieAdapter = MovieAdapter(context = this, adapterListener = this)
        binding.rvMovieList.adapter = movieAdapter

        binding.rvMovieList.addOnScrollListener(object :
            CustomPaginationRecyclerView(layoutManager) {
            override fun onLoadMore(x: Int, y: Int, view: View?) {
                viewModel.loadNextPage()
            }
        })

        viewModel.getPopularMoviesResultsLD().observe(this, Observer { data ->
            loadDataInAdapter(data)
        })

        viewModel.getProgressStatusLD().observe(this, Observer { inProgress ->
            if (inProgress) {
                showProgressBar()
            } else {
                hideProgressBar()
            }
        })

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sorting_option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sort_by_popularity -> {
                val data = movieAdapter.getCurrentData()
                data.sortBy { it.popularity }
                reLoadDataInAdapter(data)
            }
            R.id.sort_by_rating -> {
                val data = movieAdapter.getCurrentData()
                data.sortBy { it.vote_average }
                reLoadDataInAdapter(data)
            }
        }
        return true
    }

    private fun reLoadDataInAdapter(data: List<PopularMovieResponseModel.Result>) {
        movieAdapter.reloadPageData(data)
    }

    private fun loadDataInAdapter(results: List<PopularMovieResponseModel.Result>) {
        movieAdapter.loadPageData(results = results)
    }

    private fun setupToolBar(toolbarTitle: String) {
        setSupportActionBar(binding.toolbar.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = toolbarTitle
    }

    private fun showProgressBar() {
        binding.progressBar.setVisibility(true)
    }

    private fun hideProgressBar() {
        binding.progressBar.setVisibility(false)
    }


    override fun launchMovieDetailActivity(movieId: Int, movieTitle: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.MOVIE_ID, movieId)
        intent.putExtra(DetailActivity.MOVIE_TITLE, movieTitle)
        startActivity(intent)
    }

}

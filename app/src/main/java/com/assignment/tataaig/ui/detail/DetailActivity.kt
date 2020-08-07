package com.assignment.tataaig.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.TorrMonk.extension.setVisibility
import com.assignment.tataaig.constant.Constant
import com.assignment.tataaig.databinding.ActivityDetailBinding
import com.assignment.tataaig.model.MovieDetailResponseModel
import com.assignment.tataaig.util.GlideUtil
import com.assignment.tataaig.util.NetworkUtil
import com.assignment.tataaig.util.SweetDialogUtil

class DetailActivity : AppCompatActivity(), DetailActivityContract.View {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailActivityViewModel
    private var movieId: Int = 0
    private var movieTitle: String? = null

    companion object {
        const val MOVIE_ID = "movie_id"
        const val MOVIE_TITLE = "movie_title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        intent.extras?.apply {
            movieId = getInt(MOVIE_ID, 0)
            movieTitle = getString(MOVIE_TITLE)
        }
        movieTitle?.let { setupToolBar(it) }
        setContentView(binding.root)


        viewModel = ViewModelProvider(this).get(DetailActivityViewModel::class.java)

        viewModel.getMovieResponseModelLD().observe(this, Observer { data ->
            loadMovieDetails(data)
        })

        viewModel.getProgressStatusLD().observe(this, Observer { status ->
            toggleProgressBar(status)
        })

        viewModel.getMovieDetails(movieId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun loadMovieDetails(data: MovieDetailResponseModel) {
        binding.apply {
            GlideUtil.loadImage(
                Constant.IMAGE_BASE_URL + data.poster_path,
                ivMovieImage,
                this@DetailActivity
            )
            tvPopularity.text = data.popularity.toString()
            tvReleasedDate.text = data.release_date
            tvVoteAverage.text = data.vote_average.toString()
            tvTotalVote.text = data.vote_count.toString()
            tvMovieOverView.text = data.overview
        }
    }


    private fun toggleProgressBar(status: Boolean) {
        if (status) {
            binding.progressBar.setVisibility(true)
            binding.rlContentView.setVisibility(false)
        } else {
            binding.progressBar.setVisibility(false)
            binding.rlContentView.setVisibility(true)
        }
    }

    private fun setupToolBar(toolbarTitle: String) {
        setSupportActionBar(binding.toolbar.toolbar)
        supportActionBar?.title = toolbarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


}

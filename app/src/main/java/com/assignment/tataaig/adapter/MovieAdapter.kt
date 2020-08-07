package com.assignment.tataaig.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.tataaig.constant.Constant
import com.assignment.tataaig.databinding.RowItemMovieListBinding
import com.assignment.tataaig.model.PopularMovieResponseModel
import com.assignment.tataaig.util.GlideUtil


class MovieAdapter(
    private val context: Context,
    private var movieResults: MutableList<PopularMovieResponseModel.Result> = ArrayList(),
    private val adapterListener: MovieAdapterListener
) : RecyclerView.Adapter<MovieAdapter.MovieListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return MovieListViewHolder(
            RowItemMovieListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return movieResults.size
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val currentPageResult = movieResults.get(position)
        holder.bind(currentPageResult)
    }

    fun loadPageData(results: List<PopularMovieResponseModel.Result>) {
        this.movieResults.addAll(results)
        notifyItemRangeInserted(itemCount, results.size)
    }

    fun isEmpty(): Boolean {
        return movieResults.isEmpty()
    }

    fun getCurrentData(): MutableList<PopularMovieResponseModel.Result> {
        return movieResults
    }

    fun reloadPageData(data: List<PopularMovieResponseModel.Result>) {
        this.movieResults = data.toMutableList()
        notifyDataSetChanged()
    }

    inner class MovieListViewHolder(private val binding: RowItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val currentMovie = movieResults[absoluteAdapterPosition]
                with(currentMovie) {
                    adapterListener.launchMovieDetailActivity(id, title)
                }
            }
        }

        fun bind(currentPageResult: PopularMovieResponseModel.Result?) {

            binding.apply {
                tvMovieTitle.text = currentPageResult?.title
                tvRating.text = currentPageResult?.popularity.toString()
                GlideUtil.loadImage(
                    Constant.IMAGE_BASE_URL + currentPageResult?.poster_path,
                    ivMovieImage,
                    context
                )
            }

        }
    }
}

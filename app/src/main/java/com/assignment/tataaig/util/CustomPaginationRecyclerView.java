package com.assignment.tataaig.util;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class CustomPaginationRecyclerView extends RecyclerView.OnScrollListener {

    private LinearLayoutManager layoutManager;

    private int visibleThreshold = 5;
    private int currentPage = 0;
    private int previousTotalItemCount = 0;
    private boolean loading = true;
    private int startingPageIndex = 0;


    public CustomPaginationRecyclerView(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = (LinearLayoutManager) layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

        int totalItemCount = layoutManager.getItemCount();

        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }

        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
            currentPage++;
            onLoadMore(currentPage, totalItemCount, recyclerView);
            loading = true;
        }

    }


    abstract public void onLoadMore(int currentPage, int totalItemCount, View view);

}

package com.TorrMonk.extension

import android.opengl.Visibility
import android.view.View

fun View.setVisibility(status: Boolean) {
    if (status) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}


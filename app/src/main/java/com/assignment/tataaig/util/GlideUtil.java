package com.assignment.tataaig.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.assignment.tataaig.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.Random;

public class GlideUtil {

    public static void loadImage(String imageUrl, ImageView iv, Context context) {
        Glide.with(context)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(new RequestOptions().placeholder(getRandomDrawableColor()))
                .into(iv);
    }

    private static ColorDrawable[] vibrantLightColorList = {
            new ColorDrawable(Color.parseColor("#F3E5F5")),
            new ColorDrawable(Color.parseColor("#E3F2FD")),
            new ColorDrawable(Color.parseColor("#FCE4EC")),
            new ColorDrawable(Color.parseColor("#FFF3E0")),
            new ColorDrawable(Color.parseColor("#FBE9E7")),
            new ColorDrawable(Color.parseColor("#E0F7FA")),
            new ColorDrawable(Color.parseColor("#F1F8E9"))

    };

    private static ColorDrawable getRandomDrawableColor() {
        int idx = new Random().nextInt(vibrantLightColorList.length);
        return vibrantLightColorList[idx];
    }

}

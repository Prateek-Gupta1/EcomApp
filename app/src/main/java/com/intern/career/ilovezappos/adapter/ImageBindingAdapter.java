package com.intern.career.ilovezappos.adapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Home on 2/9/17.
 */

public class ImageBindingAdapter {

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView image, String url) {
        Picasso.with(image.getContext()).load(url).into(image);
    }
}

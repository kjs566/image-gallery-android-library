package com.kjs566.imagegallery;

import com.bumptech.glide.request.RequestOptions;

public class IGUtils {

    public static RequestOptions createDefaultRequestOptions() {
        return new RequestOptions()
                .placeholder(R.drawable.ig_placeholder)
                .error(R.drawable.ig_error)
                .fallback(R.drawable.ig_fallback);
    }
}

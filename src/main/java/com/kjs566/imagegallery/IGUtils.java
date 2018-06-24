package com.kjs566.imagegallery;

import com.bumptech.glide.request.RequestOptions;

public class IGUtils {

    public static IGRequestOptions createDefaultRequestOptions() {
        return new IGRequestOptions()
                .placeholder(R.drawable.ig_placeholder)
                .error(R.drawable.ig_error)
                .fallback(R.drawable.ig_fallback)
                .loadingIndicator(R.drawable.ig_loading_indicator);
    }
}

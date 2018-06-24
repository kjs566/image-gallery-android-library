package com.kjs566.imagegallery.adapters;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.kjs566.imagegallery.GlideLoadable;
import com.kjs566.imagegallery.IGRequestOptions;

public class IGStringUrisAdapter extends IGBaseAdapter{
    protected String[] mStringUris;

    public IGStringUrisAdapter(IGRequestOptions requestOptions) {
        super(requestOptions);
    }

    public void setStringUris(String[] mStringUris) {
        this.mStringUris = mStringUris;
        notifyDataSetChanged();
    }

    @Override
    public GlideLoadable createGlideLoadable(int itemPosition) {
        return GlideLoadable.fromString(mStringUris[itemPosition]);
    }

    @Override
    public int getItemCount() {
        return mStringUris == null ? 0 : mStringUris.length;
    }
}

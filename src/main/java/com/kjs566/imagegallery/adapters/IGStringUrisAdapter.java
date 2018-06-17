package com.kjs566.imagegallery.adapters;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;

public class IGStringUrisAdapter extends IGBaseAdapter{
    protected String[] mStringUris;

    public void setStringUris(String[] mStringUris) {
        this.mStringUris = mStringUris;
        notifyDataSetChanged();
    }

    @Override
    protected RequestBuilder<Drawable> loadImage(RequestManager manager, int itemPosition) {
        return manager.load(mStringUris[itemPosition]);
    }

    @Override
    public int getItemCount() {
        return mStringUris == null ? 0 : mStringUris.length;
    }
}

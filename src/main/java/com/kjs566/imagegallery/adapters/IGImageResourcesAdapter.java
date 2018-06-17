package com.kjs566.imagegallery.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;

public class IGImageResourcesAdapter extends IGBaseAdapter{
    @DrawableRes protected  int[] mResources;

    public void setResources(@DrawableRes int[] resources){
        this.mResources = resources;
        notifyDataSetChanged();
    }

    @Override
    protected RequestBuilder<Drawable> loadImage(RequestManager manager, int itemPosition) {
        return manager.load(mResources[itemPosition]);
    }

    @Override
    public int getItemCount() {
        return mResources == null ? 0 : mResources.length;
    }
}

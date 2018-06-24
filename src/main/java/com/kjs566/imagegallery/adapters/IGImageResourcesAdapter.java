package com.kjs566.imagegallery.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.kjs566.imagegallery.GlideLoadable;
import com.kjs566.imagegallery.IGRequestOptions;

public class IGImageResourcesAdapter extends IGBaseAdapter{
    @DrawableRes protected  int[] mResources;

    public IGImageResourcesAdapter(IGRequestOptions requestOptions) {
        super(requestOptions);
    }

    public void setResources(@DrawableRes int[] resources){
        this.mResources = resources;
        notifyDataSetChanged();
    }

    @Override
    public GlideLoadable createGlideLoadable(int itemPosition) {
        return GlideLoadable.fromResource(mResources[itemPosition]);
    }

    @Override
    public int getItemCount() {
        return mResources == null ? 0 : mResources.length;
    }
}

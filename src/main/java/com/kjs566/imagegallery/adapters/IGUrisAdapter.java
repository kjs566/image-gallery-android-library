package com.kjs566.imagegallery.adapters;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.kjs566.imagegallery.ui.IGDetailItemViewHolder;

import java.util.List;

public class IGUrisAdapter extends IGBaseAdapter {
    protected Uri[] mUrisArray;

    public IGUrisAdapter(RequestOptions requestOptions) {
        super(requestOptions);
    }

    public void setUris(List<Uri> uris){
        setUris(uris.toArray(mUrisArray));
    }

    public void setUris(Uri[] uris){
        this.mUrisArray = uris;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mUrisArray == null ? 0 : mUrisArray.length;
    }

    @Override
    protected RequestBuilder<Drawable> loadImage(RequestManager manager, int itemPosition) {
        return manager.load(mUrisArray[itemPosition]);
    }
}

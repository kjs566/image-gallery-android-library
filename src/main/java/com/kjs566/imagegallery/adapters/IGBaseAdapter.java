package com.kjs566.imagegallery.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.kjs566.imagegallery.GlideLoadable;
import com.kjs566.imagegallery.IGRequestOptions;
import com.kjs566.imagegallery.R;
import com.kjs566.imagegallery.ui.IGDetailItemViewHolder;

public abstract class IGBaseAdapter extends RecyclerView.Adapter<IGDetailItemViewHolder>{
    private final IGRequestOptions mRequestOptions;

    public IGBaseAdapter(IGRequestOptions requestOptions){
        this.mRequestOptions = requestOptions;
    }

    @NonNull
    @Override
    public IGDetailItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IGDetailItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ig_detail_item, parent, false), mRequestOptions);
    }

    @Override
    public void onBindViewHolder(@NonNull final IGDetailItemViewHolder holder, int position) {
        holder.showLoadingIndicator();
        createGlideLoadable(position).loadInto(Glide.with(holder.itemView)).apply(mRequestOptions).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.hideLoadingIndicator();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.hideLoadingIndicator();
                return false;
            }
        }).into(holder.getImageView());
    }



    public abstract GlideLoadable createGlideLoadable(int itemPosition);
}

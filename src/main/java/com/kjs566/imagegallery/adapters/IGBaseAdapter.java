package com.kjs566.imagegallery.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.kjs566.imagegallery.GlideLoadable;
import com.kjs566.imagegallery.R;
import com.kjs566.imagegallery.ui.IGDetailItemViewHolder;

public abstract class IGBaseAdapter extends RecyclerView.Adapter<IGDetailItemViewHolder>{
    private final RequestOptions mRequestOptions;

    public IGBaseAdapter(RequestOptions requestOptions){
        this.mRequestOptions = requestOptions;
    }

    @NonNull
    @Override
    public IGDetailItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IGDetailItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ig_detail_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IGDetailItemViewHolder holder, int position) {
        createGlideLoadable(position).loadInto(Glide.with(holder.itemView)).apply(mRequestOptions).into(holder.getImageView());
    }



    public abstract GlideLoadable createGlideLoadable(int itemPosition);
}

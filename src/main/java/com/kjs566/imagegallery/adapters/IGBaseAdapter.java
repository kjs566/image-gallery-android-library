package com.kjs566.imagegallery.adapters;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.kjs566.imagegallery.GlideApp;
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
        loadImage(GlideApp.with(holder.itemView), position).apply(mRequestOptions).into(holder.getImageView());
    }

    protected abstract RequestBuilder<Drawable> loadImage(RequestManager manager, int itemPosition);
}

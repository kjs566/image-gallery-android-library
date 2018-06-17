package com.kjs566.imagegallery.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.kjs566.imagegallery.R;

public class IGDetailItemViewHolder extends RecyclerView.ViewHolder{
    private ImageView mImageView;

    public IGDetailItemViewHolder(View itemView) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.ig_iv_image);
    }

    public ImageView getImageView() {
        return mImageView;
    }
}

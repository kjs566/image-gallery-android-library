package com.kjs566.imagegallery.ui;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.kjs566.imagegallery.IGRequestOptions;
import com.kjs566.imagegallery.R;

public class IGDetailItemViewHolder extends RecyclerView.ViewHolder{
    private ImageView mImageView;
    private ImageView mLoadingIndicator;

    public IGDetailItemViewHolder(View itemView, IGRequestOptions requestOptions) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.ig_iv_image);
        mLoadingIndicator = itemView.findViewById(R.id.ig_iv_loading_indicator);

        int loadingRes = requestOptions.getPlaceholderLoadingId();
        if(loadingRes != -1) {
            mLoadingIndicator.setImageResource(loadingRes);
        }else{
            Drawable loadingDrawable = requestOptions.getPlaceholderLoadingDrawable();
            if(loadingDrawable != null){
                mLoadingIndicator.setImageDrawable(loadingDrawable);
            }
        }
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public void showLoadingIndicator(){
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    public void hideLoadingIndicator(){
        mLoadingIndicator.setVisibility(View.INVISIBLE);
    }
}

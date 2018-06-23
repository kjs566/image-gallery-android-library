package com.kjs566.imagegallery;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.request.RequestOptions;

public class IGRequestOptions extends RequestOptions{
    public static IGRequestOptions fromOptions(RequestOptions options){
        return new IGRequestOptions().apply(options);
    }

    private @DrawableRes int mLoadingRes = -1;
    private Drawable mLoadingDrawable = null;

    @NonNull
    @Override
    public IGRequestOptions placeholder(int resourceId) {
        return (IGRequestOptions) super.placeholder(resourceId);
    }

    @NonNull
    @Override
    public IGRequestOptions placeholder(@Nullable Drawable drawable) {
        return (IGRequestOptions) super.placeholder(drawable);
    }

    @NonNull
    @Override
    public IGRequestOptions error(int resourceId) {
        return (IGRequestOptions) super.error(resourceId);
    }

    @NonNull
    @Override
    public IGRequestOptions error(@Nullable Drawable drawable) {
        return (IGRequestOptions) super.error(drawable);
    }

    @NonNull
    @Override
    public IGRequestOptions fallback(int resourceId) {
        return (IGRequestOptions) super.fallback(resourceId);
    }

    @NonNull
    @Override
    public IGRequestOptions apply(@NonNull RequestOptions other) {
        return (IGRequestOptions) super.apply(other);
    }

    @NonNull
    @Override
    public IGRequestOptions fallback(@Nullable Drawable drawable) {
        return (IGRequestOptions) super.fallback(drawable);
    }

    public IGRequestOptions loadingIndicator(@DrawableRes int resourceId){
        this.mLoadingRes = resourceId;
        if(isAutoCloneEnabled()){
            return clone().loadingIndicator(resourceId);
        }
        this.mLoadingRes = resourceId;
        return this;
    }

    public IGRequestOptions loadingIndicator(Drawable drawable){
        this.mLoadingDrawable = drawable;
        if(isAutoCloneEnabled()){
            return clone().loadingIndicator(drawable);
        }

        this.mLoadingDrawable = drawable;
        return this;
    }

    @Override
    public IGRequestOptions clone() {
        return (IGRequestOptions) super.clone();
    }

    public int getPlaceholderLoadingInt() {
        return mLoadingRes;
    }

    public Drawable getPlaceholderLoadingDrawable() {
        return mLoadingDrawable;
    }
}

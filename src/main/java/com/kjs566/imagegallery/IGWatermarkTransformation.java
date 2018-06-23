package com.kjs566.imagegallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.lang.ref.WeakReference;
import java.security.MessageDigest;

public class IGWatermarkTransformation extends BitmapTransformation{
    private static final byte[] TRANSFORMATION_ID = "watermark_transformation".getBytes();

    private final WeakReference<Context> mContext;
    private @DrawableRes int mWatermarkRes;

    public IGWatermarkTransformation(Context context, @DrawableRes int watermarkRes){
        this.mWatermarkRes = watermarkRes;
        mContext = new WeakReference<>(context);
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        if(mWatermarkRes == -1){
            return toTransform;
        }
        Context context = mContext.get();
        if(context == null){
            return toTransform;
        }

        int w = toTransform.getWidth();
        int h = toTransform.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, toTransform.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(toTransform, 0, 0, null);


        Bitmap watermark = BitmapFactory.decodeResource(context.getResources(), mWatermarkRes);
        canvas.drawBitmap(watermark, 0, 0, null);

        return result;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(TRANSFORMATION_ID);
    }
}

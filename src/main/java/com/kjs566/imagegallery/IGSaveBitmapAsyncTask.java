package com.kjs566.imagegallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.bumptech.glide.RequestBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class IGSaveBitmapAsyncTask extends AsyncTask<Bitmap, Void, Uri>{
    public static final String TAG = IGSaveBitmapAsyncTask.class.getSimpleName();

    private WeakReference<Context> mContextReference;
    private WeakReference<OnImageSavedListener> mListener;

    public IGSaveBitmapAsyncTask(Context context, OnImageSavedListener listener){
        this.mContextReference = new WeakReference<>(context);
        this.mListener = new WeakReference<>(listener);
    }

    @Override
    protected Uri doInBackground(Bitmap... bitmaps) {
        Context context = mContextReference == null ? null : mContextReference.get();
        if(context == null)
            return null;
        if(bitmaps.length == 1){
            Bitmap image = bitmaps[0];
            File imagesFolder = new File(context.getCacheDir(), "images");
            Uri uri = null;
            try {
                imagesFolder.mkdirs();
                File file = new File(imagesFolder, "shared_image.png");

                FileOutputStream stream = new FileOutputStream(file);
                image.compress(Bitmap.CompressFormat.PNG, 90, stream);
                stream.flush();
                stream.close();
                uri = FileProvider.getUriForFile(context, "com.kjs566.fileprovider", file);

            } catch (IOException e) {
                Log.d(TAG, "IOException while trying to write file for sharing: " + e.getMessage());
            }
            return uri;
        }else{
            Log.w(TAG, "Wrong number of parameters");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Uri uri) {
        if(mListener == null || mListener.get() == null){
            return;
        }
        if(uri != null){
            mListener.get().onImageSaved(uri);
        }else{
            mListener.get().onImageSavingFailed();
        }
    }

    public void cleanUp(){
        this.mListener = null;
        this.mContextReference = null;
    }

    public interface OnImageSavedListener{
        void onImageSaved(Uri uri);
        void onImageSavingFailed();
    }
}

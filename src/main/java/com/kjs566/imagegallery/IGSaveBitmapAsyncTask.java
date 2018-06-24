package com.kjs566.imagegallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
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
    private final File mFileToSave;

    public IGSaveBitmapAsyncTask(Context context, File fileToSave, @Nullable OnImageSavedListener listener){
        this.mContextReference = new WeakReference<>(context);
        this.mListener = new WeakReference<>(listener);
        this.mFileToSave = fileToSave;
    }

    @Override
    protected Uri doInBackground(Bitmap... bitmaps) {
        Context context = mContextReference == null ? null : mContextReference.get();
        if(context == null)
            return null;
        if(bitmaps.length == 1){
            Bitmap image = bitmaps[0];
            Uri uri = null;
            try {
                FileOutputStream stream = new FileOutputStream(mFileToSave);
                image.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                stream.flush();
                stream.close();
                uri = FileProvider.getUriForFile(context, "com.kjs566.fileprovider", mFileToSave);

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

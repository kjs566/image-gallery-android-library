package com.kjs566.imagegallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.io.File;
import java.lang.ref.WeakReference;

public class IGImageSharing {
    public static final String DEFAULT_SHARING_FILE_NAME = "shared_image.jpeg";

    private WeakReference<Context> mContext;
    private IGSaveBitmapAsyncTask mSavingTask;

    public static IGImageSharing with(Context context){
        return new IGImageSharing(context);
    }

    public IGImageSharing(Context context){
        this.mContext = new WeakReference<>(context);
    }

    public static File getFileForSharableImage(Context context){
        return getFileForSharableImage(context, null);
    }

    public static File getFileForSharableImage(Context context, @Nullable String fileName){
        if(fileName == null){
            fileName = DEFAULT_SHARING_FILE_NAME;
        }

        File imagesFolder = new File(context.getExternalCacheDir(), "images");
        imagesFolder.mkdirs();
        return new File(imagesFolder, fileName);
    }

    /**
     * Saves the image as JPEG from async task and runs sharing
     * @param image Bitmap to save.
     */
    public void saveImage(Bitmap image, @Nullable IGSaveBitmapAsyncTask.OnImageSavedListener listener) {
        if(image == null)
            return;

        cleanupSavingTask();

        final Context context = mContext.get();
        if(context == null){
            return;
        }

        mSavingTask = new IGSaveBitmapAsyncTask(context, getFileForSharableImage(context), listener);
        mSavingTask.execute(image);

    }

    protected void cleanupSavingTask(){
        if(mSavingTask != null){
            mSavingTask.cleanUp();
            mSavingTask = null;
        }
    }

    public void cleanup(){
        cleanupSavingTask();
    }

    /**
     * Shares the JPEG image from Uri.
     * @param uri Uri of image to share.
     */
    public void shareImageUri(Uri uri){
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/jpeg");
        Context context = mContext.get();
        if(context != null) {
            context.startActivity(intent);
        }
    }
}

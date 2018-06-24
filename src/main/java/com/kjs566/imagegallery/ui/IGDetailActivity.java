package com.kjs566.imagegallery.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.kjs566.imagegallery.IGConstants;
import com.kjs566.imagegallery.IGImageSharing;
import com.kjs566.imagegallery.IGRequestOptions;
import com.kjs566.imagegallery.IGSaveBitmapAsyncTask;
import com.kjs566.imagegallery.IGUtils;
import com.kjs566.imagegallery.IGWatermarkTransformation;
import com.kjs566.imagegallery.R;
import com.kjs566.imagegallery.adapters.IGBaseAdapter;
import com.kjs566.imagegallery.adapters.IGImageResourcesAdapter;
import com.kjs566.imagegallery.adapters.IGStringUrisAdapter;

public class IGDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = IGDetailActivity.class.getSimpleName();

    protected IGBaseAdapter mAdapter;
    private IGDetailView mGalleryView;
    private IGImageSharing mImageSharing;
    private @DrawableRes int mWatermarkRes;

    private IGWatermarkTransformation mWatermarkTransformation;
    private int mLoadingIndicatorResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ig_activity_detail);

        mGalleryView = findViewById(R.id.ig_details);
        findViewById(R.id.ig_btn_back).setOnClickListener(this);
        findViewById(R.id.ig_btn_share).setOnClickListener(this);

        IGRequestOptions requestOptions = retrieveRequestOptions(getIntent());

        final int[] resourcesArray = getIntent().getIntArrayExtra(IGConstants.IMAGE_RESOURCES_ARRAY_EXTRA_KEY);
        if(resourcesArray != null){
            IGImageResourcesAdapter a = new IGImageResourcesAdapter(requestOptions);
            a.setResources(resourcesArray);
            mAdapter = a;
        }else {
            final String[] urisArray = getIntent().getStringArrayExtra(IGConstants.IMAGE_STRING_URIS_ARRAY_EXTRA_KEY);
            if (urisArray != null){
                IGStringUrisAdapter a = new IGStringUrisAdapter(requestOptions);
                a.setStringUris(urisArray);
                mAdapter = a;
            }
        }

        mWatermarkRes = getIntent().getIntExtra(IGConstants.SHARING_WATERMARK_RESOURCE_EXTRA_KEY, -1);
        mWatermarkTransformation = new IGWatermarkTransformation(this, mWatermarkRes);


        if(mAdapter == null){
            Log.w(TAG, "No images set");
        }else{
            mGalleryView.setImagesAdapter(mAdapter);
        }
    }

    protected IGRequestOptions retrieveRequestOptions(Intent intent){
        @DrawableRes int placeholder = intent.getIntExtra(IGConstants.PLACEHOLDER_RESOURCE_EXTRA_KEY, R.drawable.ig_placeholder);
        @DrawableRes int error = intent.getIntExtra(IGConstants.ERROR_PLACEHOLDER_RESOURCE_EXTRA_KEY, R.drawable.ig_error);
        @DrawableRes int fallback = intent.getIntExtra(IGConstants.FALLBACK_RESOURCE_EXTRA_KEY, R.drawable.ig_fallback);
        @DrawableRes int loadingIndicator = intent.getIntExtra(IGConstants.LOADING_INDICATOR_RESOURCE_EXTRA_KEY, R.drawable.ig_loading_indicator);

        return IGUtils.createDefaultRequestOptions()
                .placeholder(placeholder)
                .error(error)
                .fallback(fallback)
                .loadingIndicator(loadingIndicator);
    }

    protected void shareCurrentImage(){
        int position = mGalleryView.getCurrentItemPosition();
        RequestOptions options = new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                    .transform(mWatermarkTransformation);

        mGalleryView.getImagesAdapter().createGlideLoadable(position).loadIntoAsBitmap(Glide.with(this).asBitmap().apply(options)).into(new SimpleTarget<Bitmap>(){
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                if(mImageSharing == null){
                    mImageSharing = IGImageSharing.with(IGDetailActivity.this);
                }
                mImageSharing.saveImage(resource, new IGSaveBitmapAsyncTask.OnImageSavedListener() {
                    @Override
                    public void onImageSaved(Uri uri) {
                        mImageSharing.shareImageUri(uri);
                    }

                    @Override
                    public void onImageSavingFailed() {
                        Toast.makeText(IGDetailActivity.this, "Couldn't save image for sharing, please check you storage space and try again.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mImageSharing != null) {
            mImageSharing.cleanup();
        }
    }



    @Override
    public void onClick(View view) {
        final int id = view.getId();
        if(id == R.id.ig_btn_back){
            onBackPressed();
        }else if(id == R.id.ig_btn_share){
            shareCurrentImage();
        }
    }

    public static class IntentBuilder{
        private final Bundle mExtras;

        public IntentBuilder(){
            mExtras = new Bundle();
        }

        public IntentBuilder withImageUris(String... uris){
            mExtras.putStringArray(IGConstants.IMAGE_STRING_URIS_ARRAY_EXTRA_KEY, uris);
            return this;
        }

        public IntentBuilder withImageResources(@DrawableRes int... imageResources){
            mExtras.putIntArray(IGConstants.IMAGE_RESOURCES_ARRAY_EXTRA_KEY, imageResources);
            return this;
        }

        public IntentBuilder withSharingWatermark(@DrawableRes int watermarkResource){
            mExtras.putInt(IGConstants.SHARING_WATERMARK_RESOURCE_EXTRA_KEY, watermarkResource);
            return this;
        }

        public IntentBuilder withPlaceholder(@DrawableRes int placeholderResource){
            mExtras.putInt(IGConstants.PLACEHOLDER_RESOURCE_EXTRA_KEY, placeholderResource);
            return this;
        }

        public IntentBuilder withErrorPlaceholder(@DrawableRes int errorPlaceholderResource){
            mExtras.putInt(IGConstants.ERROR_PLACEHOLDER_RESOURCE_EXTRA_KEY, errorPlaceholderResource);
            return this;
        }

        public IntentBuilder withLoadingIndicator(@DrawableRes int loadingIndicatorResource){
            mExtras.putInt(IGConstants.LOADING_INDICATOR_RESOURCE_EXTRA_KEY, loadingIndicatorResource);
            return this;
        }

        public Intent build(Context context){
            Intent intent = new Intent(context, IGDetailActivity.class);
            intent.putExtras(mExtras);
            return intent;
        }

    }
}

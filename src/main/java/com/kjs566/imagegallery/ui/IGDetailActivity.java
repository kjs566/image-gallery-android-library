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

import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.kjs566.imagegallery.GlideApp;
import com.kjs566.imagegallery.IGConstants;
import com.kjs566.imagegallery.IGImageSharing;
import com.kjs566.imagegallery.IGSaveBitmapAsyncTask;
import com.kjs566.imagegallery.IGUtils;
import com.kjs566.imagegallery.R;
import com.kjs566.imagegallery.adapters.IGBaseAdapter;
import com.kjs566.imagegallery.adapters.IGImageResourcesAdapter;
import com.kjs566.imagegallery.adapters.IGStringUrisAdapter;

public class IGDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = IGDetailActivity.class.getSimpleName();

    protected IGBaseAdapter mAdapter;
    private IGDetailView mGalleryView;
    private IGImageSharing mImageSharing;

    private static Intent createBaseIntent(Context context, @Nullable Integer placeholderResource, @Nullable Integer errorResource){
        Intent intent = new Intent(context, IGDetailActivity.class);
        if(placeholderResource != null){
            intent.putExtra(IGConstants.PLACEHOLDER_RESOURCE_EXTRA_KEY, placeholderResource);
        }
        if(errorResource != null){
            intent.putExtra(IGConstants.ERROR_RESOURCE_EXTRA_KEY, errorResource);
        }

        return intent;
    }

    public static Intent createIntent(Context context, Integer placeholderResource, Integer errorResource, String[] imageStringUris){
        Intent intent = createBaseIntent(context, placeholderResource, errorResource);
        intent.putExtra(IGConstants.IMAGE_STRING_URIS_ARRAY_EXTRA_KEY, imageStringUris);
        return intent;
    }

    public static Intent createIntent(Context context, Integer placeholderResource, Integer errorResource, @DrawableRes int[] imageResources) {
        Intent intent = createBaseIntent(context, placeholderResource, errorResource);
        intent.putExtra(IGConstants.IMAGE_RESOURCES_ARRAY_EXTRA_KEY, imageResources);
        return intent;
    }

    public static void startActivity(Context context, @DrawableRes int placeholderResource, int errorResource, @DrawableRes int[] imageResources){
        Intent intent = createIntent(context, placeholderResource, errorResource, imageResources);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, @DrawableRes int... imageResources){
        Intent intent = createIntent(context, null, null, imageResources);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, @DrawableRes int placeholderResource, int errorResource, String[] imageStringUris){
        Intent intent = createIntent(context, placeholderResource, errorResource, imageStringUris);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String... imageStringUris){
        Intent intent = createIntent(context, null, null, imageStringUris);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ig_activity_detail);

        mGalleryView = findViewById(R.id.ig_details);
        findViewById(R.id.ig_btn_back).setOnClickListener(this);
        findViewById(R.id.ig_btn_share).setOnClickListener(this);

        RequestOptions requestOptions = retrieveRequestOptions(getIntent());

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

        if(mAdapter == null){
            Log.w(TAG, "No images set");
        }else{
            mGalleryView.setImagesAdapter(mAdapter);
        }
    }

    protected RequestOptions retrieveRequestOptions(Intent intent){
        @DrawableRes int placeholder = intent.getIntExtra(IGConstants.PLACEHOLDER_RESOURCE_EXTRA_KEY, R.drawable.ig_placeholder);
        @DrawableRes int error = intent.getIntExtra(IGConstants.ERROR_RESOURCE_EXTRA_KEY, R.drawable.ig_error);
        @DrawableRes int fallback = intent.getIntExtra(IGConstants.FALLBACK_RESOURCE_EXTRA_KEY, R.drawable.ig_fallback);

        return IGUtils.createDefaultRequestOptions()
                .placeholder(placeholder)
                .error(error)
                .fallback(fallback);
    }

    protected void shareCurrentImage(){
        int position = mGalleryView.getCurrentItemPosition();
        RequestOptions options = new RequestOptions()
                                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                    .dontTransform();

        mGalleryView.getImagesAdapter().createGlideLoadable(position).loadIntoAsBitmap(GlideApp.with(this).asBitmap().apply(options)).into(new SimpleTarget<Bitmap>(){
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                if(mImageSharing == null){
                    mImageSharing = IGImageSharing.with(IGDetailActivity.this);
                }
                mImageSharing.saveAndShareImage(resource);
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
}

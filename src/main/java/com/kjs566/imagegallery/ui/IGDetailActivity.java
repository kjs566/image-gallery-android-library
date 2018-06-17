package com.kjs566.imagegallery.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kjs566.imagegallery.R;
import com.kjs566.imagegallery.adapters.IGBaseAdapter;
import com.kjs566.imagegallery.adapters.IGImageResourcesAdapter;
import com.kjs566.imagegallery.adapters.IGStringUrisAdapter;

public class IGDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String IMAGE_RESOURCES_ARRAY_EXTRA_KEY = "imageResources";
    public static final String IMAGE_STRING_URIS_ARRAY_EXTRA_KEY = "imageUris";

    private static final String TAG = IGDetailActivity.class.getSimpleName();

    protected IGBaseAdapter mAdapter;
    private IGDetailView mGalleryView;

    public static Intent createIntent(Context context, String... imageStringUris){
        Intent intent = new Intent(context, IGDetailActivity.class);
        intent.putExtra(IMAGE_STRING_URIS_ARRAY_EXTRA_KEY, imageStringUris);
        return intent;
    }

    public static Intent createIntent(Context context, @DrawableRes int... imageResources){
        Intent intent = new Intent(context, IGDetailActivity.class);
        intent.putExtra(IMAGE_RESOURCES_ARRAY_EXTRA_KEY, imageResources);
        return intent;
    }

    public static void startActivity(Context context, @DrawableRes int... imageResources){
        Intent intent = createIntent(context, imageResources);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String... imageStringUris){
        Intent intent = createIntent(context, imageStringUris);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ig_activity_detail);

        mGalleryView = findViewById(R.id.ig_details);
        findViewById(R.id.ig_btn_back).setOnClickListener(this);
        findViewById(R.id.ig_btn_share).setOnClickListener(this);

        final int[] resourcesArray = getIntent().getIntArrayExtra(IMAGE_RESOURCES_ARRAY_EXTRA_KEY);
        if(resourcesArray != null){
            IGImageResourcesAdapter a = new IGImageResourcesAdapter();
            a.setResources(resourcesArray);
            mAdapter = a;
        }else {
            final String[] urisArray = getIntent().getStringArrayExtra(IMAGE_STRING_URIS_ARRAY_EXTRA_KEY);
            if (urisArray != null){
                IGStringUrisAdapter a = new IGStringUrisAdapter();
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

    protected void shareCurrentImage(){
        //TODO
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

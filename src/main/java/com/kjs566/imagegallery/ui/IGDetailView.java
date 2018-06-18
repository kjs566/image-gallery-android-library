package com.kjs566.imagegallery.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.bumptech.glide.request.RequestOptions;
import com.kjs566.imagegallery.R;
import com.kjs566.imagegallery.adapters.IGBaseAdapter;
import com.kjs566.imagegallery.adapters.IGImageResourcesAdapter;
import com.kjs566.imagegallery.adapters.IGStringUrisAdapter;
import com.kjs566.imagegallery.adapters.IGUrisAdapter;

public class IGDetailView extends RecyclerView{
    private IGBaseAdapter mAdapter;
    private RequestOptions mRequestOptions;

    public IGDetailView(Context context) {
        super(context);
        init(null, -1);
    }

    public IGDetailView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, -1);
    }

    public IGDetailView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, -1);
    }

    protected void init(@Nullable AttributeSet attrs, int defStyle){
        this.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        if(attrs != null){
            final TypedArray a = getContext().obtainStyledAttributes(
                    attrs, R.styleable.IGDetailView, defStyle, 0);

            this.mRequestOptions = new RequestOptions()
                    .placeholder(a.getResourceId(R.styleable.IGDetailView_placeholder, R.drawable.ig_placeholder))
                    .error(a.getResourceId(R.styleable.IGDetailView_error, R.drawable.ig_error))
                    .fallback(a.getResourceId(R.styleable.IGDetailView_fallback, R.drawable.ig_fallback));

            if(a.hasValue(R.styleable.IGDetailView_urisArray)){
                IGStringUrisAdapter adapter = new IGStringUrisAdapter(mRequestOptions);

                int arrayRes = a.getResourceId(R.styleable.IGDetailView_urisArray, -1);
                adapter.setStringUris(getResources().getStringArray(arrayRes));
                setImagesAdapter(adapter);
            }
            if(a.hasValue(R.styleable.IGDetailView_resArray)){
                IGImageResourcesAdapter adapter = new IGImageResourcesAdapter(mRequestOptions);

                int arrayRes = a.getResourceId(R.styleable.IGDetailView_resArray, -1);
                adapter.setResources(getResources().getIntArray(arrayRes));
                setImagesAdapter(adapter);

            }
            a.recycle();
        }

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(this);
    }

    public void setImagesAdapter(IGBaseAdapter adapter){
        mAdapter = adapter;
        this.setAdapter(mAdapter);
    }
}

package com.kjs566.imagegallery.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.kjs566.imagegallery.IGRequestOptions;
import com.kjs566.imagegallery.IGUtils;
import com.kjs566.imagegallery.R;
import com.kjs566.imagegallery.adapters.IGBaseAdapter;
import com.kjs566.imagegallery.adapters.IGImageResourcesAdapter;
import com.kjs566.imagegallery.adapters.IGStringUrisAdapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IGDetailView extends RecyclerView{
    private IGBaseAdapter mAdapter;
    private IGRequestOptions mRequestOptions;
    private LinearLayoutManager mLayoutManager;
    private ListPreloader.PreloadModelProvider mPreloadModelProvider = null;

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
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        //mLayoutManager.setInitialPrefetchItemCount(3);
        //mLayoutManager.setItemPrefetchEnabled(true);

        if(attrs != null){
            final TypedArray a = getContext().obtainStyledAttributes(
                    attrs, R.styleable.IGDetailView, defStyle, 0);

            this.mRequestOptions = IGUtils.createDefaultRequestOptions()
                    .placeholder(a.getResourceId(R.styleable.IGDetailView_placeholder, R.drawable.ig_placeholder))
                    .error(a.getResourceId(R.styleable.IGDetailView_error, R.drawable.ig_error))
                    .fallback(a.getResourceId(R.styleable.IGDetailView_fallback, R.drawable.ig_fallback))
                    .loadingIndicator(a.getResourceId(R.styleable.IGDetailView_loading, R.drawable.ig_loading_indicator));

            if(a.hasValue(R.styleable.IGDetailView_urisArray)){
                IGStringUrisAdapter adapter = new IGStringUrisAdapter(mRequestOptions);

                int arrayRes = a.getResourceId(R.styleable.IGDetailView_urisArray, -1);
                String[] uris = getResources().getStringArray(arrayRes);
                adapter.setStringUris(uris);
                //mPreloadModelProvider = new IGPreloadModelProvider<>(uris);

                setImagesAdapter(adapter);
            }
            if(a.hasValue(R.styleable.IGDetailView_resArray)){
                IGImageResourcesAdapter adapter = new IGImageResourcesAdapter(mRequestOptions);

                int arrayRes = a.getResourceId(R.styleable.IGDetailView_resArray, -1);
                int[] resources = getResources().getIntArray(arrayRes);
                adapter.setResources(resources);

                setImagesAdapter(adapter);
            }

            a.recycle();
        }

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(this);

        if(mPreloadModelProvider != null){
            //RecyclerViewPreloader<?> preloader = new RecyclerViewPreloader<>(Glide.with(this), mPreloadModelProvider, new ViewPreloadSizeProvider(this), 10);
            //addOnScrollListener(preloader);
        }

        this.setLayoutManager(mLayoutManager);
    }

    public void setImagesAdapter(IGBaseAdapter adapter){
        mAdapter = adapter;
        this.setAdapter(mAdapter);
    }

    public IGBaseAdapter getImagesAdapter(){
        return mAdapter;
    }

    public int getCurrentItemPosition(){
        final LayoutManager layoutManager = getLayoutManager();
        if(layoutManager != null && layoutManager instanceof LinearLayoutManager){
            return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }
        throw new UnsupportedOperationException("Only LinearLayoutManager supports this operation");
    }

    public class IGPreloadModelProvider<T> implements ListPreloader.PreloadModelProvider<T>{
        private final T[] mItems;

        public IGPreloadModelProvider(T[] items){
            this.mItems = items;
        }

        @NonNull
        @Override
        public List<T> getPreloadItems(int position) {
            if(mItems.length <= position){
                return Collections.emptyList();
            }
            T item = mItems[position];
            return Collections.singletonList(item);
        }

        @Nullable
        @Override
        public RequestBuilder<?> getPreloadRequestBuilder(@NonNull T item) {
            return Glide.with(IGDetailView.this).load(item).apply(mRequestOptions);
        }
    }
}

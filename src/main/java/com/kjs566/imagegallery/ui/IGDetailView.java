package com.kjs566.imagegallery.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.kjs566.imagegallery.R;
import com.kjs566.imagegallery.adapters.IGBaseAdapter;

public class IGDetailView extends RecyclerView{
    private IGBaseAdapter mAdapter;

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

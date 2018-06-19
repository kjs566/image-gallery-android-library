package com.kjs566.imagegallery;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.gif.GifDrawable;

import java.io.File;

public class GlideLoadable {
    private static final int LOADABLE_TYPE_BITMAP = 0;
    private static final int LOADABLE_TYPE_DRAWABLE = 1;
    private static final int LOADABLE_TYPE_RESOURCE = 2;
    private static final int LOADABLE_TYPE_OBJECT = 3;
    private static final int LOADABLE_TYPE_URI = 4;
    private static final int LOADABLE_TYPE_STRING = 5;

    private int type;

    private Bitmap bitmap;
    private Drawable drawable;

    private @DrawableRes int resource;
    private Object object;
    private Uri uri;
    private String string;

    private GlideLoadable(int type){
        this.type = type;
    }

    public static GlideLoadable fromBitmap(Bitmap bitmap){
        GlideLoadable loadable = new GlideLoadable(LOADABLE_TYPE_BITMAP);
        loadable.bitmap = bitmap;
        return loadable;
    }

    public static GlideLoadable fromDrawable(Drawable drawable){
        GlideLoadable loadable = new GlideLoadable(LOADABLE_TYPE_DRAWABLE);
        loadable.drawable = drawable;
        return loadable;
    }

    public static GlideLoadable fromResource(int resource){
        GlideLoadable loadable = new GlideLoadable(LOADABLE_TYPE_RESOURCE);
        loadable.resource = resource;
        return loadable;
    }

    public static GlideLoadable fromString(String string){
        GlideLoadable loadable = new GlideLoadable(LOADABLE_TYPE_STRING);
        loadable.string = string;
        return loadable;
    }

    public static GlideLoadable fromUri(Uri uri){
        GlideLoadable loadable = new GlideLoadable(LOADABLE_TYPE_URI);
        loadable.uri = uri;
        return loadable;
    }

    public static GlideLoadable fromObject(Object object){
        GlideLoadable loadable = new GlideLoadable(LOADABLE_TYPE_OBJECT);
        loadable.object = object;
        return loadable;
    }

    public RequestBuilder<Drawable> loadInto(RequestManager manager){
        return loadInto(manager.asDrawable());
    }

    public RequestBuilder<Drawable> loadInto(RequestBuilder<Drawable> builder){
        switch (type){
            case LOADABLE_TYPE_BITMAP:
                return builder.load(bitmap);
            case LOADABLE_TYPE_DRAWABLE:
                return builder.load(drawable);
            case LOADABLE_TYPE_RESOURCE:
                return builder.load(resource);
            case LOADABLE_TYPE_STRING:
                return builder.load(string);
            case LOADABLE_TYPE_URI:
                return builder.load(uri);
        }

        return builder;
    }

    public RequestBuilder<Bitmap> loadIntoAsBitmap(RequestBuilder<Bitmap> builder){
        switch (type){
            case LOADABLE_TYPE_BITMAP:
                return builder.load(bitmap);
            case LOADABLE_TYPE_DRAWABLE:
                return builder.load(drawable);
            case LOADABLE_TYPE_RESOURCE:
                return builder.load(resource);
            case LOADABLE_TYPE_STRING:
                return builder.load(string);
            case LOADABLE_TYPE_URI:
                return builder.load(uri);
        }

        return builder;
    }

    public RequestBuilder<File> loadIntoAsFile(RequestBuilder<File> builder){
        switch (type){
            case LOADABLE_TYPE_BITMAP:
                return builder.load(bitmap);
            case LOADABLE_TYPE_DRAWABLE:
                return builder.load(drawable);
            case LOADABLE_TYPE_RESOURCE:
                return builder.load(resource);
            case LOADABLE_TYPE_STRING:
                return builder.load(string);
            case LOADABLE_TYPE_URI:
                return builder.load(uri);
        }

        return builder;
    }

    public RequestBuilder<GifDrawable> loadIntoAsGif(RequestBuilder<GifDrawable> builder){
        switch (type){
            case LOADABLE_TYPE_BITMAP:
                return builder.load(bitmap);
            case LOADABLE_TYPE_DRAWABLE:
                return builder.load(drawable);
            case LOADABLE_TYPE_RESOURCE:
                return builder.load(resource);
            case LOADABLE_TYPE_STRING:
                return builder.load(string);
            case LOADABLE_TYPE_URI:
                return builder.load(uri);
        }

        return builder;
    }


}

package com.kjs566.imagegallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IGImagePicker {
    /**
     * Creates image picker with choice of camera or gallery.
     * @param context Context
     * @return Created intent
     */
    public static Intent createImagePicker(Context context) {
        Intent chooserIntent = null;

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        cameraIntent.putExtra("return-data", true);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(IGImageSharing.getFileForSharableImage(context)));

        List<Intent> intentsList = createPickerIntentsList(context, galleryIntent, cameraIntent);

        if (intentsList.size() > 0) {
            chooserIntent = Intent.createChooser(intentsList.remove(intentsList.size() - 1), context.getString(R.string.pick_or_take_image));
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentsList.toArray(new Parcelable[]{}));
        }

        return chooserIntent;
    }

    /**
     * Creates list if intents with applications capable of responding to the input vararg of intents.
     * @param context Context
     * @param intents Intents we want to combine into one picker
     * @return List of available intents
     */
    private static List<Intent> createPickerIntentsList(Context context, Intent... intents) {
        List<Intent> list = new ArrayList<>(intents.length);

        for(Intent intent : intents) {
            List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);
            for (ResolveInfo resolveInfo : resInfo) {
                String packageName = resolveInfo.activityInfo.packageName;

                Intent intentTarget = new Intent(intent);
                intentTarget.setPackage(packageName);

                list.add(intentTarget);
            }
        }
        return list;
    }

    /**
     * Gets the URI of the image returned from onActivityResult
     * @param context Context
     * @param resultCode Result code
     * @param imageIntent Intent of result
     * @return Uri of image or null
     */
    public static Uri getImageUriFromResult(Context context, int resultCode, Intent imageIntent) {
        Uri imageUri = null;
        File imageFile = IGImageSharing.getFileForSharableImage(context);
        if (resultCode == Activity.RESULT_OK) {
            boolean fromCamera = (imageIntent == null || imageIntent.getData() == null || imageIntent.getData().toString().contains(imageFile.toString()));
            if (fromCamera) {
                imageUri = FileProvider.getUriForFile(context, "com.kjs566.fileprovider", imageFile);
            } else {
                imageUri = imageIntent.getData();
            }
        }
        return imageUri;
    }

}

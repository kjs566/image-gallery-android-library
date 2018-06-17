//TODO JCENTER STATUS

# Image Gallery Library for Android

## Highly customizable but easy to use image gallery library for Android.

### Description
Image showing gallery for Android.

Version 0.1.0 contains components for viewing image detail - screen with one image in the middle with swiping navigation between images.

It uses Recycler View for "paging" and Glide library for loading images.

**Images could be loaded from -**

1. String URI
2. Drawable resources
3. Any other method supported in Glide - Bitmap, byte[], File - not out of a box, needs to implement own adapter extending IGBaseAdapter

## Screenshots

//TODO

## Demo Project
Demo project with usage example can be found at:
> https://github.com/kjs566/image-gallery-android-demo

## How to use
### Instalation
**As gradle dependency**
In progress.

**Cloning sources from this repository**
1. Cloning into your projects root file.
> git clone -b "v0.1.0" --single-branch --depth 1 https://github.com/kjs566/image-gallery-android-library.git imagegallery
2. Adding it as a dependency for your project in app/build.gradle -> dependencies
> implementation project(path: ':imagegallery')


### Usage
You can use either whole implemented Activity or implemented View - based on your needs for customization.

**Activity - easiest way with helper static methods** 
> IGDetailActivity.startActivity(context, @DrawableRes int... imagesResources)
or
> IGDetailActivity.startActivity(context, String... imagesUrls)

**View - can be placed inside your own activity/fragment/view**
1) Add IGDetailView - 
* inside your XML 
> \<IGDetailView 
>  android:layout_width="..."
>  android:layout_height="..."
>  ...\>
> \</IGDetailView\>
* or within code - 
> new IGDetailView(...)

2) Create prepared (or your own) Adapter - extending IGBaseAdapter
* For images retrieved from Internet URLs
> IGStringUrisAdapter adapter = new IGStringUrisAdapter();
> adapter.setStringUris(imagesURIs);

* For images from internal resources
> IGImageResourcesAdapter adapter = new IGImageResourcesAdapter();
> adapter.setResources(imagesResources);

* For own loading using Glide
** extend IGBaseAdapter
** use manager and item position to manage the Glide loading

* For own loading without Glide
** extend IGBaseAdapter
** override onBindViewHolder
** create your own implementation of images loading


3) Set the adapter to the view
> igDetailView.setImagesAdapter(adapter);

Simplier variant with direct items setting from code and/or XML is in development.

## Customizations
List of customizable things - just override the resource (create string, drawable, color, ... in the XML resources) inside your own application.

Resource | Description 
-------- | -----------
@drawable/ig_back_button | Back button icon
@drawable/ig_share_button | Share button icon
@color/ig_button_background | Background of back and share buttons
@color/ig_detail_image_background | Background of the image in IGDetailView
@string/back | Content description for back button
@string/share | Content description for share button
@dimen/ig_btn_margin_side | Margin of share and back button from sides
@dimen/ig_btn_margin_top | Margin of share and back button from the top



## Dependencies

Library | Url | Tested with version
------- | --- | -------
 Glide | https://github.com/bumptech/glide | 4.7.1

## Supported Android versions
API 16+ (Android 4.1+)

## TODO LIST

## Licence

The project is licensed under the GNU General Public License v3.0.

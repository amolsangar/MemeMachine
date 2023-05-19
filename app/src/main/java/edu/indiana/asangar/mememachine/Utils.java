package edu.indiana.asangar.mememachine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;

import androidx.core.content.FileProvider;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* Utils.java
 *
 * Java class for common functionalities used everywhere
 *
 * Created by: Amol Sangar
 * Created on: 2/26/23
 * Last Modified by: Amol Sangar
 * Last Modified on: 2/27/23
 * Assignment/Project: A590 Android Development Final Project - Meme Machine
 * Part of: Meme Machine, referred by all fragments
 **/


public class Utils {
    /** Method to share image with other applications
     * @param context
     * @param bitmap
     */
    public static void shareImage(Context context, Bitmap bitmap){
        // save bitmap to cache directory
        try {
            File cachePath = new File(context.getCacheDir(), "imageview");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        File imagePath = new File(context.getCacheDir(), "imageview");
        File newFile = new File(imagePath, "image.png");
        Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", newFile);

        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, context.getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            shareIntent.setType("image/png");
            context.startActivity(Intent.createChooser(shareIntent, "Choose an app"));
        }
    }

    /** Method to save image to internal storage
     * @param context
     * @param url
     * @param filename
     */
    public static void saveImage(Context context, String url, String filename) {
        Picasso.get().load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                try {
                    File mydir = new File(Environment.getExternalStorageDirectory() + "/MemeMachine");
                    if (!mydir.exists()) {
                        mydir.mkdirs();
                    }

                    String fileUri = mydir.getAbsolutePath() + File.separator + filename + System.currentTimeMillis() + ".jpg";
                    FileOutputStream outputStream = new FileOutputStream(fileUri);

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }

    // Burak - https://stackoverflow.com/questions/36238481/android-usagestatsmanager-not-returning-correct-daily-results
    /** Converts milliseconds to human readable time format
     * @param usedTime
     * @return string with hour+min+sec
     */
    public static String convertLongToTimeChar(long usedTime) {
        String hour="", min="", sec="";

        int h=(int)(usedTime/1000/60/60);
        if (h!=0)
            hour = h+"h ";

        int m=(int)((usedTime/1000/60) % 60);
        if (m!=0)
            min = m+"m ";

        int s=(int)((usedTime/1000) % 60);
        if (s==0 && (h!=0 || m!=0))
            sec="";
        else
            sec = s+"s";

        return hour+min+sec;
    }
}

package com.example.reactive_android.model;

import android.graphics.Bitmap;

import io.reactivex.rxjava3.annotations.NonNull;

public class PictureImage {

  Bitmap bitmap;


    public PictureImage(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}

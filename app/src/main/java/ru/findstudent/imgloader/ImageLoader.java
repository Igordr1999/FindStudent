package ru.findstudent.imgloader;

import android.widget.ImageView;

public interface ImageLoader {
    void loadImage(String url, ImageView imageView);
    void setRequiredSize(int width, int height);
}

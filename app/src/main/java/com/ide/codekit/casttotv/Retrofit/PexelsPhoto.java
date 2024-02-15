package com.ide.codekit.casttotv.Retrofit;

import com.google.gson.annotations.SerializedName;

public class PexelsPhoto {
    @SerializedName("id")
    private long id;

    @SerializedName("src")
    private PexelsPhotoSrc src;

    // Getters and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PexelsPhotoSrc getSrc() {
        return src;
    }

    public void setSrc(PexelsPhotoSrc src) {
        this.src = src;
    }
}
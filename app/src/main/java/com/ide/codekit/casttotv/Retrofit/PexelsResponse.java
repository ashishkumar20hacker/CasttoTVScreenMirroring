package com.ide.codekit.casttotv.Retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PexelsResponse {
    @SerializedName("photos")
    private List<PexelsPhoto> photos;

    public List<PexelsPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PexelsPhoto> photos) {
        this.photos = photos;
    }
}

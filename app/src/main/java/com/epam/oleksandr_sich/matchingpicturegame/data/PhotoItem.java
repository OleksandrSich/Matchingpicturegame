package com.epam.oleksandr_sich.matchingpicturegame.data;

import java.util.Objects;

public class PhotoItem implements Cloneable{
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhotoItem)) return false;
        PhotoItem photoItem = (PhotoItem) o;
        return Objects.equals(getId(), photoItem.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }

    public PhotoItem(String id, String url) {
        this.id = id;
        this.url = url;
    }

    private String id;

    private String url;

    public ImageState getState() {
        return state;
    }

    public void updateState(ImageState state) {
        this.previousState = this.state;
        this.state = state;

    }

    private PhotoItem(PhotoItem source) {
        this.id = source.id;
        this.url = source.url;
    }
    private ImageState state = ImageState.DEFAULT;

    public ImageState getPreviousState() {
        return previousState;
    }

    private ImageState previousState = ImageState.DEFAULT;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PhotoItem createDuplicate() {
        return new PhotoItem(this);
    }
}

package com.epam.oleksandr_sich.matchingpicturegame.data;

import java.util.Objects;

public class PhotoItem implements Cloneable {
    private String id;
    private String url;
    private ImageState state = ImageState.DEFAULT;
    private ImageState previousState = ImageState.DEFAULT;

    public void setState(ImageState state) {
        this.state = state;
    }

    public PhotoItem(String id, String url, ImageState state) {
        this.id = id;
        this.url = url;
        this.state = state;
    }

    public PhotoItem(String id, String url) {
        this.id = id;
        this.url = url;
    }


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

    public ImageState getPreviousState() {
        return previousState;
    }

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

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhotoItem)) return false;
        PhotoItem photoItem = (PhotoItem) o;
        return Objects.equals(getId(), photoItem.getId());
    }
}

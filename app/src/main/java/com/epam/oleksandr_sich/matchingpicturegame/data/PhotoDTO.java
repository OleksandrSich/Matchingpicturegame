package com.epam.oleksandr_sich.matchingpicturegame.data;

import java.util.Objects;

public class PhotoDTO {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhotoDTO)) return false;
        PhotoDTO photoDTO = (PhotoDTO) o;
        return Objects.equals(getId(), photoDTO.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }

    public PhotoDTO(String id, String url) {
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
}

package com.epam.oleksandr_sich.matchingpicturegame.data;

public class PhotoDTO {
    public PhotoDTO(String id, String url) {
        this.id = id;
        this.url = url;
    }

    private String id;

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

    private String url;
}

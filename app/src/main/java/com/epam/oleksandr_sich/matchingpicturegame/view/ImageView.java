package com.epam.oleksandr_sich.matchingpicturegame.view;

import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoItem;

import java.util.List;

public interface ImageView {
    void showImages(List<PhotoItem> photos);

    void showLoading(boolean state);
}

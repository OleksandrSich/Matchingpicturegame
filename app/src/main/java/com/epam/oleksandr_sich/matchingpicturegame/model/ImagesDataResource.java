package com.epam.oleksandr_sich.matchingpicturegame.model;

import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoListResponse;
import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoResponse;

import io.reactivex.Observable;

interface ImagesDataResource {

    Observable<PhotoListResponse> getPhotoList(int page);

    Observable<PhotoResponse> getPhoto(String id);
}

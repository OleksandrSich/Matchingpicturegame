package com.epam.oleksandr_sich.matchingpicturegame;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoItem;
import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoResponse;
import com.epam.oleksandr_sich.matchingpicturegame.model.ImageRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class ImagePresenterImpl implements ImagePresenter {
    private final int START_PAGE = 1;
    private final int PER_PAGE = 8;
    private Context context;
    private List<PhotoItem> photos = new ArrayList<>();
    private ImageRepository imageRepository;
    private ImageView view;
    private int currentPage = START_PAGE;
    private Disposable loadPhotosSubscription;

    public ImagePresenterImpl(Context context, ImageView view) {
        this.context = context;
        this.view = view;
        imageRepository = new ImageRepository();
    }

    @Override
    public void loadPhotos() {
        currentPage++;
        photos.clear();
        view.showLoading(true);
        loadPhotosRequest(currentPage);
    }

    private void loadPhotosRequest(int page) {
        loadPhotosSubscription = imageRepository.getPhotoList(page, PER_PAGE)
                .map(response -> response.getPhotos().getPhoto())
                .flatMap(Observable::fromIterable)
                .subscribe(photo -> imageRepository.getPhoto(photo.getId())
                        .doOnError(throwable -> view.showLoading(false))
                        .subscribe(this::loadPhoto));
    }


    private void shuffleList() {
        long seed = System.nanoTime();
        Collections.shuffle(photos, new Random(seed));
    }

    private void addItemToList(PhotoResponse photoResponse) {
        String tempId = String.valueOf(photos.size() + 1);
        photos.add(new PhotoItem(tempId, getSizedPhoto(photoResponse)));
        photos.add(new PhotoItem(tempId, getSizedPhoto(photoResponse)));
    }

    private boolean isListFull() {
        return photos.size() == PER_PAGE * 2;
    }

    @Override
    public void onStop() {
        loadPhotosSubscription.dispose();
    }

    @Override
    public void onStart() {

    }

    private String getSizedPhoto(PhotoResponse photoResponse) {
        return photoResponse.getSizes().getSize().get(5).getSource();
    }

    private void loadPhoto(PhotoResponse photoResponse) {
        addItemToList(photoResponse);
        loadImage(photoResponse);
        if(isListFull()) {
            view.showLoading(false);
            shuffleList();
            view.showImages(photos);
        }
    }

    private void loadImage(PhotoResponse photoResponse) {
        RequestOptions requestOptions = new RequestOptions().priority(Priority.IMMEDIATE);
        Glide.with(context)
                .download(getSizedPhoto(photoResponse))
                .apply(requestOptions)
                .submit();
    }
}

package com.epam.oleksandr_sich.matchingpicturegame.presenter;

import android.app.Activity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoItem;
import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoResponse;
import com.epam.oleksandr_sich.matchingpicturegame.data.Size;
import com.epam.oleksandr_sich.matchingpicturegame.model.ImageRepository;
import com.epam.oleksandr_sich.matchingpicturegame.view.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class ImagePresenterImpl implements ImagePresenter {
    private final int START_PAGE = 1;
    private final int PER_PAGE = 8;
    public static int SPAN_COUNT = 4;

    private Activity context;
    private List<PhotoItem> photos = new ArrayList<>();
    private ImageRepository imageRepository;
    private ImageView view;
    private boolean isNeedToRetry = false;
    private Disposable loadPhotosSubscription;

    private int lastPage = 2;
    private int currentPage = START_PAGE;
    private int cardWeight;

    public ImagePresenterImpl(Activity context, ImageView view, ImageRepository imageRepository) {
        this.context = context;
        this.view = view;
        this.imageRepository = imageRepository;
    }

    @Override
    public void loadPhotos() {
        photos.clear();
        isNeedToRetry = false;
        view.showLoading(true);
        loadPhotosRequest(getNextPage());
    }

    private void loadPhotosRequest(int page) {
        loadPhotosSubscription = imageRepository.getPhotoList(page, PER_PAGE)
                .map(response -> {
                    lastPage = response.getPhotos().getPages();
                    return response.getPhotos().getPhoto();
                })
                .flatMap(Observable::fromIterable)
                .subscribe(photo -> imageRepository.getPhoto(photo.getId())
                        .doOnError(throwable -> {view.showLoading(false);
                        view.showErrorMsg();})
                        .subscribe(this::loadPhoto));
    }


    private void shuffleList() {
        long seed = System.nanoTime();
        Collections.shuffle(photos, new Random(seed));
    }

    private void addItemToList(PhotoResponse photoResponse) {
        PhotoItem photoItem = new PhotoItem(String.valueOf(photos.size() + 1), getSizedPhoto(photoResponse));
        photos.add(photoItem);
        photos.add(photoItem.createDuplicate());
    }

    private boolean isListFull() {
        return photos.size() == PER_PAGE * 2;
    }

    @Override
    public void onStop() {
        if (!loadPhotosSubscription.isDisposed()){
            loadPhotosSubscription.dispose();
            isNeedToRetry = true;
        }
    }

    @Override
    public void onStart() {
        if(isNeedToRetry) loadPhotos();
    }

    private String getSizedPhoto(PhotoResponse photoResponse) {
        List<Size> sizes = photoResponse.getSizes().getSize();
        Collections.sort(sizes, (size, t1) -> Integer.compare(size.getWidth(), t1.getWidth()));
        for (Size size : sizes) {
            if (size.getWidth() > getCardWeight()) {
                return size.getSource();
            }
        }
        return photoResponse.getSizes().getSize().get(0).getSource();
    }

    private void loadPhoto(PhotoResponse photoResponse) {
        addItemToList(photoResponse);
        loadImage(photoResponse);
        if (isListFull()) {
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

    public int getCardWeight() {
        if (cardWeight <= 0 ) {
            cardWeight = view.getDisplayWidth() / SPAN_COUNT;
        }
        return cardWeight;
    }

    private int getNextPage() {
        if (currentPage < lastPage) {
            currentPage++;
        } else currentPage = START_PAGE;
        return currentPage;
    }
}

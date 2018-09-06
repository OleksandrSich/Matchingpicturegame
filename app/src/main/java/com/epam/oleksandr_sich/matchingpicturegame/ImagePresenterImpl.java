package com.epam.oleksandr_sich.matchingpicturegame;

import android.content.Context;
import android.util.Log;

import com.epam.oleksandr_sich.matchingpicturegame.data.Photo;
import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoItem;
import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoListResponse;
import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoResponse;
import com.epam.oleksandr_sich.matchingpicturegame.model.ImageRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ImagePresenterImpl implements ImagePresenter {
    private Context context;
    private List<PhotoItem> photos = new ArrayList<>();
    private ImageRepository imageRepository;
    private ImageView view;

    public ImagePresenterImpl(Context context, ImageView view) {
        this.context = context;
        imageRepository = new ImageRepository();
        this.view = view;
    }

    @Override
    public void loadPhotos(int page) {
        imageRepository.getPhotoList(page)
                .subscribe(new Observer<PhotoListResponse>() {
                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onNext(PhotoListResponse photoListResponse) {
                                   for (Photo temp : photoListResponse.getPhotos().getPhoto()) {
                                       loadPhoto(temp.getId());
                                   }
                               }

                               @Override
                               public void onError(Throwable e) {

                               }

                               @Override
                               public void onComplete() {

                               }
                           }
                );
    }

    private void loadPhoto(String id) {
        imageRepository.getPhoto(id)
                .subscribe(new Observer<PhotoResponse>() {
                               @Override
                               public void onSubscribe(Disposable d) {
                               }

                               @Override
                               public void onNext(PhotoResponse photoResponse) {
                                   String tempId = String.valueOf(photos.size() + 1);
                                   photos.add(new PhotoItem(tempId,
                                           photoResponse.getSizes().getSize().get(5).getSource()));
                                   photos.add(new PhotoItem(tempId,
                                           photoResponse.getSizes().getSize().get(5).getSource()));
                                   long seed = System.nanoTime();
                                   Collections.shuffle(photos, new Random(seed));
                                   view.showImages(photos);

                               }

                               @Override
                               public void onError(Throwable t) {
                                   Log.d("PhotoResponse", "error");

                               }

                               @Override
                               public void onComplete() {

                               }
                           }
                );
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onStart() {

    }
}

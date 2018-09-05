package com.epam.oleksandr_sich.matchingpicturegame;

import android.content.Context;
import android.util.Log;

import com.epam.oleksandr_sich.matchingpicturegame.data.Photo;
import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoDTO;
import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoListResponse;
import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoResponse;
import com.epam.oleksandr_sich.matchingpicturegame.model.ImageRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ImagePresenterImpl implements ImagePresenter {
    private Context context;
    private List<PhotoDTO> photos = new ArrayList<>();
    private ImageRepository imageRepository;
    private ImageView view;

    public ImagePresenterImpl(Context context, ImageView view) {
        this.context = context;
        imageRepository = new ImageRepository();
        this.view = view;
    }

    @Override
    public void loadPhotos(int page) {
        imageRepository.getPhotoList(page).subscribe(new Observer<PhotoListResponse>() {
                                                          @Override
                                                          public void onSubscribe(Disposable d) {

                                                          }

                                                          @Override
                                                          public void onNext(PhotoListResponse photoListResponse) {
                                                              for (Photo temp: photoListResponse.getPhotos().getPhoto()) {
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

    public void loadPhoto(String id){
      imageRepository.getPhoto(id)
                .subscribe(new Observer<PhotoResponse>() {
                               @Override
                               public void onSubscribe(Disposable d) {
                               }

                               @Override
                               public void onNext(PhotoResponse photoResponse) {
                                   photos.add(new PhotoDTO("1", photoResponse.getSizes().getSize().get(5).getUrl()));
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

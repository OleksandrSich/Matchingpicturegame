package com.epam.oleksandr_sich.matchingpicturegame.model;

import android.util.Log;

import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoListResponse;
import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoResponse;
import com.epam.oleksandr_sich.matchingpicturegame.utils.BaseSchedulerProvider;
import com.epam.oleksandr_sich.matchingpicturegame.network.NetworkClient;
import com.epam.oleksandr_sich.matchingpicturegame.network.NetworkInterface;
import com.epam.oleksandr_sich.matchingpicturegame.utils.SchedulerProvider;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ImageRepository implements ImagesDataResource {

    private static final String SEARCH_METHOD = "flickr.photos.search";
    private static final String GET_PHOTO_METHOD = "flickr.photos.getSizes";
    private static final String TAGS = "kitten";
    private static final String API_KEY = "5423dbab63f23a62ca4a986e7cbb35e2";
    private static final String PER_PAGE = "8";
    private static final String FORMAT_JSON = "json";
    private static final int NO_JSON_CALL = 1;

    private BaseSchedulerProvider mSchedulerProvider =  SchedulerProvider.getInstance();

    @Override
    public Observable<PhotoListResponse> getPhotoList(final int page) {
       return NetworkClient.getRetrofit().create(NetworkInterface.class).listPhotos(SEARCH_METHOD, API_KEY, NO_JSON_CALL,
                TAGS, PER_PAGE, FORMAT_JSON, page)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui());


    }

    @Override
    public Observable<PhotoResponse> getPhoto(String id) {
        return NetworkClient.getRetrofit().create(NetworkInterface.class).getPhoto(GET_PHOTO_METHOD, API_KEY,
                id,  NO_JSON_CALL, FORMAT_JSON)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui());
    }

    public  void  getTetst(){
        NetworkClient.getRetrofit().create(NetworkInterface.class).getPhoto(GET_PHOTO_METHOD, API_KEY,
                "44436942192",  NO_JSON_CALL, FORMAT_JSON)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Observer<PhotoResponse>() {
                               @Override
                               public void onSubscribe(Disposable d) {
                               }

                               @Override
                               public void onNext(PhotoResponse photoResponse) {
                                   Log.d("PhotoListResponse", photoResponse.getStat());
                               }

                               @Override
                               public void onError(Throwable t) {
                                   Log.d("PhotoListResponse", "error");

                               }

                               @Override
                               public void onComplete() {

                               }
                           }
                );
    }
}
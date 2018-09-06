package com.epam.oleksandr_sich.matchingpicturegame.network;

import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoListResponse;
import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoResponse;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetworkInterface {


    @GET("/services/rest/")
    Observable<PhotoListResponse> listPhotos(@Query("method") String searchMethod,
                                             @Query("api_key") String apiKey,
                                             @Query("nojsoncallback") int noJsonCallback,
                                             @Query("tags") String tags,
                                             @Query("per_page") int perPage,
                                             @Query("format") String format,
                                             @Query("page") int page);

    @GET("/services/rest/")
    Observable<PhotoResponse> getPhoto(@Query("method") String searchMethod,
                                     @Query("api_key") String apiKey,
                                     @Query("photo_id") String photoId,
                                     @Query("nojsoncallback") int noJsonCallback,
                                     @Query("format") String format);
}

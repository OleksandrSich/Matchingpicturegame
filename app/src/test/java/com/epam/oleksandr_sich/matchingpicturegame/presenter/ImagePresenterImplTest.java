package com.epam.oleksandr_sich.matchingpicturegame.presenter;

import android.app.Activity;

import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoListResponse;
import com.epam.oleksandr_sich.matchingpicturegame.model.ImageRepository;
import com.epam.oleksandr_sich.matchingpicturegame.view.ImageView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.Observer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ImagePresenterImplTest {

    private ImagePresenterImpl presenter;
    @Mock
    private Activity context;
    @Mock
    private ImageView imageView;
    @Mock
    private ImageRepository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new ImagePresenterImpl(context, imageView, repository);
    }

    @Test
    public void loadPhotos() {
        int perPage = 8;
        int intPage = 2;
        when(repository.getPhotoList(intPage, perPage)).thenReturn(new Observable<PhotoListResponse>() {
            @Override
            protected void subscribeActual(Observer<? super PhotoListResponse> observer) {

            }
        });

        presenter.loadPhotos();

        verify(imageView).showLoading(true);
        verify(repository).getPhotoList(intPage, perPage);

    }

    @Test
    public void getCardWeight() {
        int width = 100;
        when(imageView.getDisplayWidth()).thenReturn(width);

        int cardWeight = presenter.getCardWeight();
        assertThat(cardWeight).isEqualTo(width/ImagePresenterImpl.SPAN_COUNT);

    }
}
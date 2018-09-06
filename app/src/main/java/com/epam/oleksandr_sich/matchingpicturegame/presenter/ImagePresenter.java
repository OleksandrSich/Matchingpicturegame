package com.epam.oleksandr_sich.matchingpicturegame.presenter;

import com.epam.oleksandr_sich.matchingpicturegame.view.ImageView;

interface ImagePresenter {

    void loadPhotos();

    void onStop();

    void onStart();
}

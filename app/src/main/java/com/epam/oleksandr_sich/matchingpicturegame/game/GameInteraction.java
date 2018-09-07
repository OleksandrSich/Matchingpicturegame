package com.epam.oleksandr_sich.matchingpicturegame.game;

import com.epam.oleksandr_sich.matchingpicturegame.data.ImageState;
import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoItem;

public interface GameInteraction {
    void gameFinished(int steps);

    PhotoItem getItem(int position);

    int getItemCount();

    void refreshItem(int position, ImageState done);
}

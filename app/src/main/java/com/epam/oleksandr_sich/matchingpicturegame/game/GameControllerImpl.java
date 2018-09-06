package com.epam.oleksandr_sich.matchingpicturegame.game;

import android.os.Handler;

import com.epam.oleksandr_sich.matchingpicturegame.ImagesAdapter;
import com.epam.oleksandr_sich.matchingpicturegame.data.ImageState;

public class GameControllerImpl implements GameController{
    public GameControllerImpl(ImagesAdapter adapter) {
        this.adapter = adapter;
    }

    private final Handler handler = new Handler();
    private ImagesAdapter adapter;
    private int selectedPosition = -1;
    private int selectedItems = 0;
    private int steps = 0;

    @Override
    public void clearGameData() {
        clearSelections();
        clearSteps();
    }

    private void clearSteps() {
        steps = 0;
    }

    private void clearSelections() {
        selectedPosition = -1;
        selectedItems = 0;
    }

    private boolean isAbleToSelect(int position) {
        return adapter.getItem(position).getState() == ImageState.DEFAULT &&
                selectedItems < 2;
    }

    private boolean isWon() {
        boolean res = true;
        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (adapter.getItem(i).getState() != ImageState.DONE) {
                res = false;
                break;
            }
        }
        return res;
    }

    @Override
    public void flipCard(int position) {
        if (!isAbleToSelect(position)) {
            return;
        }
        addStep();
        if (selectedPosition == -1) {
            chooseFirstCadr(position);
            selectedItems++;
        } else {
            if (isTheSameCard(position)) {
                updateItemState(selectedPosition, ImageState.DONE);
                updateItemState(position, ImageState.DONE);
                clearSelections();
                if (isWon()) finishGame();
            } else {
                updateItemState(selectedPosition, ImageState.CLOSED);
                updateItemState(position, ImageState.CLOSED);
                selectedItems++;
                closeNotEqualsCardWithDelay(position);

            }
        }

    }

    private void closeNotEqualsCardWithDelay(int position) {
        handler.postDelayed(() -> {
            updateItemState(selectedPosition, ImageState.DEFAULT);
            updateItemState(position, ImageState.DEFAULT);
            clearSelections();
        }, 1000);
    }

    private boolean isTheSameCard(int position) {
        return adapter.getItem(selectedPosition).equals(adapter.getItem(position));
    }

    private void chooseFirstCadr(int position) {
        selectedPosition = position;
        updateItemState(position, ImageState.SELECTED);
    }

    private void addStep() {
        steps++;
    }

    private void finishGame() {
    }

    private void updateItemState(int position, ImageState imageState) {
        adapter.getItem(position).updateState(imageState);
        adapter.notifyItemChanged(position);
    }
}

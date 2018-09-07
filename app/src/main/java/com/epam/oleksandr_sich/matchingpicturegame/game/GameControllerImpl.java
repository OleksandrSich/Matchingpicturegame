package com.epam.oleksandr_sich.matchingpicturegame.game;

import android.os.Handler;

import com.epam.oleksandr_sich.matchingpicturegame.data.ImageState;

public class GameControllerImpl implements GameController{

    private final Handler handler = new Handler();
    private GameInteraction gameInteraction;
    private int selectedPosition = -1;
    private int selectedItems = 0;
    private int steps = 0;

    public GameControllerImpl(GameInteraction gameInteraction) {
        this.gameInteraction = gameInteraction;
    }

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
        return gameInteraction.getItem(position).getState() == ImageState.DEFAULT &&
                selectedItems < 2;
    }

    private boolean isWon() {
        boolean res = true;
        for (int i = 0; i < gameInteraction.getItemCount(); i++) {
            if (gameInteraction.getItem(i).getState() != ImageState.DONE) {
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
            actionChooseFirstCard(position);
            selectedItems++;
        } else {
            if (isTheSameCard(position)) {
                actionDone(position);
            } else {
                actionCancel(position);
            }
        }
    }

    public void actionDone(int position) {
        markDone(position);
        clearSelections();
        if (isWon()) finishGame();
    }

    public void actionCancel(int position) {
        markCancel(position);
        selectedItems++;
        closeNotEqualsCardWithDelay(position);
    }

    private void markCancel(int position) {
        gameInteraction.refreshItem(selectedPosition, ImageState.CLOSED);
        gameInteraction.refreshItem(position, ImageState.CLOSED);
    }

    private void markDone(int position) {
        gameInteraction.refreshItem(selectedPosition, ImageState.DONE);
        gameInteraction.refreshItem(position, ImageState.DONE);
    }

    private void closeNotEqualsCardWithDelay(int position) {
        handler.postDelayed(() -> {
            gameInteraction.refreshItem(selectedPosition, ImageState.DEFAULT);
            gameInteraction.refreshItem(position, ImageState.DEFAULT);
            clearSelections();
        }, 1000);
    }

    public boolean isTheSameCard(int position) {
        return gameInteraction.getItem(selectedPosition).equals(gameInteraction.getItem(position));
    }

    public void actionChooseFirstCard(int position) {
        selectedPosition = position;
        gameInteraction.refreshItem(position, ImageState.SELECTED);
    }

    private void addStep() {
        steps++;
    }

    private void finishGame() {
        gameInteraction.gameFinished(steps/2);
    }


}

package com.epam.oleksandr_sich.matchingpicturegame.game;

import com.epam.oleksandr_sich.matchingpicturegame.data.ImageState;
import com.epam.oleksandr_sich.matchingpicturegame.data.PhotoItem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class GameInteractionTest {
    private GameControllerImpl subject;
    private PhotoItem firstCard;
    private PhotoItem secondCard;
    @Mock
    GameInteraction gameInteraction;
    private int fistPosition;
    private int secondPosition;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        subject = new GameControllerImpl(gameInteraction);
        firstCard = new PhotoItem("1", "2", ImageState.DEFAULT);
        secondCard = new PhotoItem("2", "3", ImageState.DEFAULT);
        fistPosition = 0;
        secondPosition = 1;
        when(gameInteraction.getItemCount()).thenReturn(2);
    }

    @After
    public void restart() throws Exception {
        subject.clearGameData();
    }

    @Test
    public void gameController_flipCard_call_getItem() throws Exception {
        when(gameInteraction.getItem(fistPosition)).thenReturn(firstCard);
        subject.flipCard(fistPosition);
        verify(gameInteraction).getItem(fistPosition);
    }

    @Test
    public void gameController_flipCard_call_refreshItem_SELECTED() throws Exception {
        when(gameInteraction.getItem(fistPosition)).thenReturn(firstCard);
        subject.flipCard(fistPosition);
        verify(gameInteraction).refreshItem(fistPosition, ImageState.SELECTED);
    }

    @Test
    public void gameController_flipCards_notEqual_call_refreshItem_CLOSED() throws Exception {
        when(gameInteraction.getItem(fistPosition)).thenReturn(firstCard);
        when(gameInteraction.getItem(secondPosition)).thenReturn(secondCard);
        subject.flipCard(fistPosition);
        subject.flipCard(secondPosition);
        verify(gameInteraction).refreshItem(fistPosition, ImageState.CLOSED);
        verify(gameInteraction).refreshItem(secondPosition, ImageState.CLOSED);
    }

    @Test
    public void gameController_flipCards_notEqual_neverCall_refreshItem_DONE() throws Exception {
        when(gameInteraction.getItem(fistPosition)).thenReturn(firstCard);
        when(gameInteraction.getItem(secondPosition)).thenReturn(secondCard);
        subject.flipCard(fistPosition);
        subject.flipCard(secondPosition);
        verify(gameInteraction, never()).refreshItem(fistPosition, ImageState.DONE);
        verify(gameInteraction, never()).refreshItem(secondPosition, ImageState.DONE);
    }

    @Test
    public void gameController_flipCards_equal_neverCall_refreshItem_CLOSED() throws Exception {
        when(gameInteraction.getItem(fistPosition)).thenReturn(firstCard);
        when(gameInteraction.getItem(secondPosition)).thenReturn(firstCard);
        subject.flipCard(fistPosition);
        subject.flipCard(secondPosition);
        verify(gameInteraction, never()).refreshItem(fistPosition, ImageState.CLOSED);
        verify(gameInteraction, never()).refreshItem(secondPosition, ImageState.CLOSED);
    }

    @Test
    public void gameController_flipCards_equal_call_refreshItem_DONE() throws Exception {
        when(gameInteraction.getItem(fistPosition)).thenReturn(firstCard);
        when(gameInteraction.getItem(secondPosition)).thenReturn(firstCard);
        subject.flipCard(fistPosition);
        subject.flipCard(secondPosition);
        verify(gameInteraction).refreshItem(fistPosition, ImageState.DONE);
        verify(gameInteraction).refreshItem(secondPosition, ImageState.DONE);
    }

    @Test
    public void gameController_flipCards_equal_withRestart_neverCall_refreshItem_DONE() throws Exception {
        when(gameInteraction.getItem(fistPosition)).thenReturn(firstCard);
        when(gameInteraction.getItem(secondPosition)).thenReturn(firstCard);
        subject.flipCard(fistPosition);
        subject.clearGameData();
        subject.flipCard(secondPosition);
        verify(gameInteraction, never()).refreshItem(fistPosition, ImageState.DONE);
        verify(gameInteraction, never()).refreshItem(secondPosition, ImageState.DONE);
    }

    @Test
    public void gameController_flipCards_equal_withRestart_neverCall_refreshItem_CLOSED() throws Exception {
        when(gameInteraction.getItem(fistPosition)).thenReturn(firstCard);
        when(gameInteraction.getItem(secondPosition)).thenReturn(secondCard);
        subject.flipCard(fistPosition);
        subject.clearGameData();
        subject.flipCard(secondPosition);
        verify(gameInteraction, never()).refreshItem(fistPosition, ImageState.CLOSED);
        verify(gameInteraction, never()).refreshItem(secondPosition, ImageState.CLOSED);
    }

    @Test
    public void gameController_finishGame_allDone() throws Exception {
        firstCard.setState(ImageState.DONE);
        secondCard.setState(ImageState.DONE);
        when(gameInteraction.getItem(fistPosition)).thenReturn(firstCard);
        when(gameInteraction.getItem(secondPosition)).thenReturn(secondCard);
        subject.checkGameIsOver();
        verify(gameInteraction).gameFinished(anyInt());
    }

    @Test
    public void gameController_neverCall_finishGame_someDone() throws Exception {
        firstCard.setState(ImageState.DEFAULT);
        secondCard.setState(ImageState.DONE);
        when(gameInteraction.getItem(fistPosition)).thenReturn(firstCard);
        when(gameInteraction.getItem(secondPosition)).thenReturn(secondCard);
        subject.checkGameIsOver();
        verify(gameInteraction, never()).gameFinished(anyInt());
    }
}

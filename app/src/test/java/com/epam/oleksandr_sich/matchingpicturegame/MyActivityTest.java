package com.epam.oleksandr_sich.matchingpicturegame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class MyActivityTest {

    @Test
    public void clickingButton_shouldRefreshGame() throws Exception {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
//
//        Button button = (Button) activity.findViewById(R.id.button);
//        TextView results = (TextView) activity.findViewById(R.id.results);
//
//        button.performClick();
//        assertThat(results.getText().toString()).isEqualTo("Robolectric Rocks!");
    }
}
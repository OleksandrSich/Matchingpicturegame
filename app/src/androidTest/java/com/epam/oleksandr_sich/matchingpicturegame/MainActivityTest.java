package com.epam.oleksandr_sich.matchingpicturegame;


import android.support.annotation.NonNull;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
//        ViewInteraction recyclerView = onView(
//                allOf(withId(R.id.images),
//                        childAtPosition(
//                                withClassName(is("android.widget.RelativeLayout")),
//                                1)));
//        recyclerView.perform(actionOnItemAtPosition(0, click())).;
//
//        ViewInteraction recyclerView2 = onView(
//                allOf(withId(R.id.images),
//                        childAtPosition(
//                                withClassName(is("android.widget.RelativeLayout")),
//                                1)));
//        recyclerView2.perform(actionOnItemAtPosition(1, click()));
//
//        onView(withId(R.id.images))
//                .perform(actionOnItemAtPosition(0, click()))
//                .perform(actionOnItemAtPosition(1 click()))
//                .check(matches(atPosition(1, withText("Test Text"))));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        ViewInteraction imageView = onView(
//                allOf(withId(R.id.itemImage), withContentDescription("Hello! I am random cat"),
//                        childAtPosition(
//                                childAtPosition(
//                                        IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
//                                        0),
//                                0),
//                        isDisplayed()));
//        imageView.check(matches(isDisplayed()));
//
//        ViewInteraction imageView2 = onView(
//                allOf(withId(R.id.itemImage), withContentDescription("Hello! I am random cat"),
//                        childAtPosition(
//                                childAtPosition(
//                                        IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
//                                        0),
//                                0),
//                        isDisplayed()));
//        imageView2.check(matches(isDisplayed()));
//
//        ViewInteraction recyclerView3 = onView(
//                allOf(withId(R.id.images),
//                        childAtPosition(
//                                withClassName(is("android.widget.RelativeLayout")),
//                                1)));
//        recyclerView3.perform(actionOnItemAtPosition(0, click()));

    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }
}

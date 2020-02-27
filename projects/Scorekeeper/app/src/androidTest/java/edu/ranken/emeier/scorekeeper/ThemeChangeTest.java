package edu.ranken.emeier.scorekeeper;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ThemeChangeTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void themeChangeTest() {
        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.title), withText("Night Mode"),
                        isDisplayed()));
        textView.check(matches(allOf(isDisplayed(), withText("Night Mode"))));

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Night Mode"),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction overflowMenuButton2 = onView(
                allOf(withContentDescription("More options"),
                        isDisplayed()));
        overflowMenuButton2.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.title), withText("Day Mode"),
                        isDisplayed()));
        textView2.check(matches(allOf(isDisplayed(), withText("Day Mode"))));
    }
}
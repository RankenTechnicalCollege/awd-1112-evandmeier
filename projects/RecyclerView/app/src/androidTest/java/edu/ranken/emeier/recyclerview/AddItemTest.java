package edu.ranken.emeier.recyclerview;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddItemTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addItemTest() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.word), withText("+ Word 20"),
                        isDisplayed()));
        textView2.check(matches(allOf(withText("+ Word 20"), isDisplayed())));
    }
}
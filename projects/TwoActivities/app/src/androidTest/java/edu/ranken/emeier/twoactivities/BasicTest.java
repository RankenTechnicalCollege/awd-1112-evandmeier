package edu.ranken.emeier.twoactivities;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class BasicTest {

    @Rule
    public ActivityTestRule<MainActivity> _mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void enterText() {
        onView(withId(R.id.editText_Main))
                .perform(typeText("Hello World"))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.button_main))
                .perform(click());

        onView(withId(R.id.text_message))
                .check(matches(allOf(isDisplayed(), withText("Hello World"))));
    }
}
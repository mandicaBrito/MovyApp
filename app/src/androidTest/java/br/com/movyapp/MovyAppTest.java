package br.com.movyapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.movyapp.view.home.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MovyAppTest {

    @Rule
    public ActivityTestRule<MainActivity> activity = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void loadMoviesTest() {
        onView(withId(R.id.rcv_movie_list))
                .perform(RecyclerViewActions.scrollToPosition(15));

        onView(withId(R.id.rcv_movie_list)).check(new RecyclerViewItemCount(40));

    }

    @Test
    public void movieDetailTest() {
        onView(withId(R.id.rcv_movie_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.txv_movie_detail_title)).check(matches(withText("Dunkirk")));
    }

    @Test
    public void searchMovieTest() {
        onView(withId(R.id.search)).perform(click());
        onView(withId(android.support.design.R.id.search_src_text)).perform(typeText("an"));
        onView(withId(R.id.rcv_movie_list)).check(new RecyclerViewItemCount(4));
    }

}
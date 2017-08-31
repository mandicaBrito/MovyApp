package br.com.movyapp;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RecyclerViewItemCount implements ViewAssertion {

    private final int expectedCount;

    public RecyclerViewItemCount(final int expectedCount) {
        this.expectedCount = expectedCount;
    }

    @Override
    public void check(final View view, final NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        assertThat(adapter.getItemCount(), is(expectedCount));
    }
}

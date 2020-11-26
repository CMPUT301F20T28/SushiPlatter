package com.example.a301pro;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static androidx.test.InstrumentationRegistry.getContext;


/**
 * Intent testing for Mybook fragment
 */
public class MybookFragIntentTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    /**
     * Set up the start point for activity test
     */
    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    /**
     * Closes the activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

    /**
     * Test for executing the app
     * @throws Exception fail message
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    /**
     * Test for switching to the Mybook fragment
     */
    @Test
    public void testBottomNavigation() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_mine));
        solo.waitForFragmentById(R.id.nav_mine,500);
    }

    /**
     * Test for opening add/edit book intent
     */
    @Test
    public void testAddBook() {
        View add_btn = rule.getActivity().findViewById(R.id.add_book_button);
        solo.clickOnView(add_btn);
        solo.assertCurrentActivity("Wrong Activity", AddEditIntent.class);
    }

    @Test
    public void testSearch() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_mine));
        solo.waitForFragmentById(R.id.nav_mine,500);
        solo.enterText((EditText) solo.getView(R.id.search_method), "cat");
        solo.waitForText("cat", 1, 1000);
        solo.clearEditText((EditText) solo.getView(R.id.search_method)); //Clear the EditText
        solo.enterText((EditText) solo.getView(R.id.search_method), "city");
        solo.waitForText("", 0, 1000);
    }

    @Test
    public void testFilter() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_mine));
        solo.waitForFragmentById(R.id.nav_mine,500);
        solo.clickOnView(solo.getView(R.id.filter));
        solo.waitForText("Available", 0, 1000);
        solo.waitForText("Borrowed", 0, 1000);
        solo.waitForText("Accepted", 0, 1000);
        solo.waitForText("Requested", 0, 1000);
    }
}

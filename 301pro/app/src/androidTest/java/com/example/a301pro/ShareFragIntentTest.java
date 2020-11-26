package com.example.a301pro;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


/**
 * Intent testing for Share fragment
 */
public class ShareFragIntentTest {
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
     * Test for switching to the Share fragment
     */
    @Test
    public void testBottomNavigation() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_share));
        solo.waitForFragmentById(R.id.nav_share,500);
    }

    /**
     * Test for searching book by keyword
     */
    @Test
    public void testSearch() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_share));
        solo.waitForFragmentById(R.id.nav_share,500);
        solo.enterText((EditText) solo.getView(R.id.search_method), "a");
        solo.waitForText("a", 1, 1000);
    }

    /**
     * Test for menu pop up
     */
    @Test
    public void testFilter() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_share));
        solo.waitForFragmentById(R.id.nav_mine,500);
        solo.clickOnView(solo.getView(R.id.filter));
        solo.waitForText("Computer", 0, 1000);
    }

    /**
     * Test for switching to book requesting page
     */
    @Test
    public void testRequestIntent() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_share));
        solo.waitForFragmentById(R.id.nav_share,500);
        solo.clickInList(1);
        solo.waitForFragmentById(R.id.sent_request,500);
    }

    /**
     * Test for back button in requesting page
     */
    @Test
    public void testBackButton() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_share));
        solo.waitForFragmentById(R.id.nav_share,500);
        solo.clickInList(1);
        solo.waitForFragmentById(R.id.sent_request,500);
        solo.clickOnView(solo.getView(R.id.back_sent_request));
        solo.waitForFragmentById(R.id.nav_share,500);
    }
}
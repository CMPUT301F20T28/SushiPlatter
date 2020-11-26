package com.example.a301pro;

import android.app.Activity;
import android.view.KeyEvent;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


/**
 * Intent testing for Borrow fragment
 */
public class BorrowedFragIntentTest {
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
        solo.clickOnView(solo.getView(R.id.nav_bo));
        solo.waitForFragmentById(R.id.nav_bo,500);
    }

    /**
     * Test for searching book by keyword
     */
    @Test
    public void testSearch() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_bo));
        solo.waitForFragmentById(R.id.nav_bo,500);
        solo.enterText((EditText) solo.getView(R.id.search_method_pending), "food");
        solo.waitForText("food", 1, 1000);
        solo.clearEditText((EditText) solo.getView(R.id.search_method_pending)); //Clear the EditText
        solo.enterText((EditText) solo.getView(R.id.search_method_pending), "city");
        solo.waitForText("", 0, 1000);
    }

    /**
     * Test for menu pop up
     */
    @Test
    public void testFilter() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_bo));
        solo.waitForFragmentById(R.id.nav_bo,500);
        solo.clickOnView(solo.getView(R.id.filter_pending));
        solo.waitForText("Computer", 0, 1000);
    }

    /**
     * Test for switching to book scanning page
     */
    @Test
    public void testScanIntent() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_bo));
        solo.waitForFragmentById(R.id.nav_bo,500);
        solo.clickInList(1);
        solo.assertCurrentActivity("Wrong Activity", scan_ISBN.class);
    }

    /**
     * Test for invoking system camera
     */
    @Test
    public void testScanButton() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_bo));
        solo.waitForFragmentById(R.id.nav_bo,500);
        solo.clickInList(1);
        solo.assertCurrentActivity("Wrong Activity", scan_ISBN.class);
        solo.clickOnView(solo.getView((R.id.scanBtn)));
        solo.sendKey(KeyEvent.KEYCODE_CAMERA);
    }
}

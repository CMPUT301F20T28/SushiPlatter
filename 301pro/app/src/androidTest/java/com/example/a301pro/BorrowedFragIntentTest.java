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
    public ActivityTestRule<Login> rule =
            new ActivityTestRule<>(Login.class, true, true);

    /**
     * Set up the start point for activity test
     */
    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    /**
     * Closes the activity after each test
     * @throws Exception fail message
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
     * Test for switching to the Borrow fragment
     */
    @Test
    public void testBottomNavigation() {
        login();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_bo));
        solo.waitForFragmentById(R.id.nav_bo,1000);
        solo.sleep(500);
    }

    /**
     * Test for searching book by keyword
     */
    @Test
    public void testSearch() {
        login();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_bo));
        solo.waitForFragmentById(R.id.nav_bo,1000);
        solo.sleep(500);
        solo.enterText((EditText) solo.getView(R.id.search_method_pending), "food");
        solo.waitForText("food", 1, 1000);
        solo.sleep(500);
        solo.clearEditText((EditText) solo.getView(R.id.search_method)); //Clear the EditText
        solo.sleep(500);
        solo.enterText((EditText) solo.getView(R.id.search_method_pending), "city");
        solo.waitForText("", 0, 1000);
    }

    /**
     * Test for menu pop up
     */
    @Test
    public void testFilter() {
        login();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_bo));
        solo.waitForFragmentById(R.id.nav_bo,500);
        solo.sleep(500);
        solo.clickOnView(solo.getView(R.id.filter_pending));
        solo.waitForText("Computer", 0, 1000);
        solo.sleep(500);
    }

    /**
     * Test for switching to book scanning page
     */
    @Test
    public void testScanIntent() {
        login();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_bo));
        solo.waitForFragmentById(R.id.nav_bo,1000);
        solo.sleep(500);
        solo.clickInList(1);
        solo.assertCurrentActivity("Wrong Activity", ScanISBN.class);
    }

    /**
     * Test for invoking system camera
     */
    @Test
    public void testScanButton() {
        login();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_bo));
        solo.waitForFragmentById(R.id.nav_bo,1000);
        solo.sleep(500);
        solo.clickInList(1);
        solo.assertCurrentActivity("Wrong Activity", ScanISBN.class);
        solo.clickOnView(solo.getView((R.id.scanBtn)));
        solo.sleep(500);
        solo.sendKey(KeyEvent.KEYCODE_CAMERA);
    }

    /**
     * login tool for some tests
     */
    public void login() {
        String loginInfo = "mockuser";
        solo.enterText((EditText) solo.getView(R.id.text_username), loginInfo);
        solo.enterText((EditText) solo.getView(R.id.text_password), loginInfo);
        solo.clickOnButton("Login");
        solo.waitForActivity("MainActivity", 10000);
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
    }
}

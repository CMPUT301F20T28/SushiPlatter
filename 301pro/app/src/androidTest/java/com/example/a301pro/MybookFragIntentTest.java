package com.example.a301pro;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.a301pro.Functionality.Login;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


/**
 * Intent testing for Mybook fragment
 */
public class MybookFragIntentTest {
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
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    /**
     * Test for executing the app
     * @throws Exception fail message
     */
    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    /**
     * Test for switching to the Mybook fragment
     */
    @Test
    public void testBottomNavigation() {
        login();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_mine));
        solo.waitForFragmentById(R.id.nav_mine,1000);
        solo.sleep(500);
    }

    /**
     * Test for opening add/edit book intent
     */
    @Test
    public void testAddBook() {
        login();
        solo.clickOnView(solo.getView(R.id.add_book_button));
        solo.sleep(500);
        solo.assertCurrentActivity("Wrong Activity", AddEditIntent.class);
    }

    /**
     * Test for searching book by keyword
     */
    @Test
    public void testSearch() {
        login();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_mine));
        solo.waitForFragmentById(R.id.nav_mine,1000);
        solo.sleep(500);
        solo.enterText((EditText) solo.getView(R.id.search_method), "cat");
        solo.waitForText("cat", 1, 1000);
        solo.sleep(500);
        solo.clearEditText((EditText) solo.getView(R.id.search_method)); //Clear the EditText
        solo.sleep(500);
        solo.enterText((EditText) solo.getView(R.id.search_method), "city");
        solo.waitForText("", 0, 1000);
    }

    /**
     * Test for menu pop up
     */
    @Test
    public void testFilter() {
        login();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_mine));
        solo.waitForFragmentById(R.id.nav_mine,1000);
        solo.sleep(500);
        solo.clickOnView(solo.getView(R.id.filter));
        solo.waitForText("Available", 0, 1000);
        solo.waitForText("Borrowed", 0, 1000);
        solo.waitForText("Accepted", 0, 1000);
        solo.waitForText("Requested", 0, 1000);
        solo.sleep(500);
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

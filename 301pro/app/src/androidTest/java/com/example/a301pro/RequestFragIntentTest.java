package com.example.a301pro;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.a301pro.View.ViewMessages;
import com.example.a301pro.View.ViewUserProfile;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Intent testing for Request fragment
 */
public class RequestFragIntentTest {
    private Solo solo;

    /**
     * Set up test rule
     */
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
     * Test for switching to the Request fragment
     */
    @Test
    public void testBottomNavigation() {
        login();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_request));
        assertTrue(solo.waitForView(R.id.nav_request));
        solo.sleep(1000);
    }

    /**
     * Test for searching book by keyword
     */
    @Test
    public void testSearch() {
        login();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_request));
        solo.waitForFragmentById(R.id.nav_request,10000);
        solo.sleep(1000);
        // lease than 2 matches, only 1 in search bar
        solo.enterText((EditText) solo.getView(R.id.search_method_pending), "ciiiity#$%#");
        assertFalse(solo.searchText("ciiiity#$%#", 2, true));
    }

    /**
     * test for opening profile
     */
    @Test
    public void testOpenProfile() {
        login();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_request));
        solo.waitForFragmentById(R.id.nav_request,10000);
        solo.sleep(1000);
        solo.clickOnView(solo.getView(R.id.userhead_pending));
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", ViewUserProfile.class);
        solo.sleep(1000);
    }

    /**
     * test for opening message center
     */
    @Test
    public void testOpenMessageCenter() {
        login();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_request));
        solo.waitForFragmentById(R.id.nav_request,10000);
        solo.sleep(1000);
        solo.clickOnView(solo.getView(R.id.message_center_pending));
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", ViewMessages.class);
        solo.sleep(1000);
    }

    /**
     * login tool for some tests
     */
    public void login() {
        String username = "mockuser";
        String password = "mockuser123";
        solo.enterText((EditText) solo.getView(R.id.text_username), username);
        solo.enterText((EditText) solo.getView(R.id.text_password), password);
        solo.clickOnButton("Login");
        solo.waitForActivity("MainActivity", 10000);
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
    }
}

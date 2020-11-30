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


/**
 * Intent testing for Share fragment
 */
public class ShareFragIntentTest {
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
     * Test for switching to the Share fragment
     */
    @Test
    public void testBottomNavigation() {
        login();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_share));
        solo.waitForFragmentById(R.id.nav_share,1000);
        solo.sleep(500);
    }

    /**
     * Test for searching book by keyword
     */
    @Test
    public void testSearch() {
        login();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_share));
        solo.waitForFragmentById(R.id.nav_share,1000);
        solo.enterText((EditText) solo.getView(R.id.search_method), "a");
        solo.searchText("a", 0, true);
        solo.sleep(500);
    }

    /**
     * test for opening profile
     */
    @Test
    public void testOpenProfile() {
        login();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_share));
        solo.waitForFragmentById(R.id.nav_share,10000);
        solo.sleep(1000);
        solo.clickOnView(solo.getView(R.id.userhead));
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
        solo.clickOnView(solo.getView(R.id.nav_share));
        solo.waitForFragmentById(R.id.nav_share,10000);
        solo.sleep(1000);
        solo.clickOnView(solo.getView(R.id.message_center));
        solo.sleep(1000);
        solo.assertCurrentActivity("Wrong Activity", ViewMessages.class);
        solo.sleep(1000);
    }

    /**
     * Test for switching to book requesting page
     */
    @Test
    public void testRequestIntent() {
        login();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_share));
        solo.waitForFragmentById(R.id.nav_share,1000);
        solo.sleep(500);
        solo.clickInList(1);
        solo.assertCurrentActivity("Wrong Activity", SentRequestIntent.class);
        solo.sleep(500);
    }

    /**
     * Test for back button in requesting page
     */
    @Test
    public void testBackButton() {
        login();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.nav_share));
        solo.waitForFragmentById(R.id.nav_share,1000);
        solo.sleep(500);
        solo.clickInList(1);
        solo.waitForFragmentById(R.id.sent_request,1000);
        solo.sleep(500);
        solo.clickOnView(solo.getView(R.id.back_sent_request));
        solo.waitForFragmentById(R.id.nav_share,1000);
        solo.sleep(500);
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

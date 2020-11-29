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
 * Intent testing for Login activity
 */
public class LoginTest {
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
     * Test for executing the app
     * @throws Exception fail message
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }


    /**
     * Test if the Register button actually work
     */
    @Test
    public void checkRegisterSwitch() {
        solo.assertCurrentActivity("Wrong activity", Login.class);
        solo.clickOnText("Sign Up");
        solo.waitForActivity("Register",10000);
        solo.assertCurrentActivity("Wrong activity", Register.class);
        solo.sleep(500);
    }

    /**
     * Test if Login button actually work
     */
    @Test
    public void checkLoginButton() {
        solo.assertCurrentActivity("Wrong activity", Login.class);

        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong activity", Login.class);

        solo.sleep(500);
        solo.enterText((EditText) solo.getView(R.id.text_username),"mockuser");
        solo.enterText((EditText) solo.getView(R.id.text_password),"mockuser");
        solo.clickOnButton("Login");
        solo.waitForActivity("MainActivity",10000);
        solo.assertCurrentActivity("Wrong activity",MainActivity.class);
        solo.sleep(500);

    }

    /**
     * Test if user can proceed without username or password both been filled.
     */
    @Test
    public void checkLoginInput() {
        solo.assertCurrentActivity("Wrong activity", Login.class);

        solo.enterText((EditText) solo.getView(R.id.text_username),"mockuser");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong activity", Login.class);
        solo.clearEditText((EditText)solo.getView(R.id.text_username));
        solo.sleep(500);

        solo.enterText((EditText) solo.getView(R.id.text_password),"mockuser");
        solo.clickOnButton("Login");
        solo.assertCurrentActivity("Wrong activity", Login.class);
    }

    /**
     * Close Activity when done test
     */
    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }
}
package com.example.a301pro;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class LoginIntentTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<login> rule =
            new ActivityTestRule<>(login.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }


    /**
     * Check if it switches to main page after pressed Login
     * @throws Exception
     */
    @Test
    public void checkMainActivitySwitch() throws Exception {
        solo.assertCurrentActivity("Wrong Activity", login.class);
        solo.clickOnButton("Login");
        solo.waitForActivity("MainActivity");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    /**
     * Check if it switches to register page after clicked Sign Up
     * @throws Exception
     */
    @Test
    public void checkRegisterSwitch() throws Exception {
        solo.assertCurrentActivity("Wrong Activity", login.class);
        solo.clickOnText("Sign Up");
        solo.waitForActivity("Register");
        solo.assertCurrentActivity("Wrong Activity", register.class);
    }

    /**
     * Check if it displays password after pressed on switch, vice versa
     * may have bug in showing dot instead of char
     * @throws Exception
     */
    @Test
    public void checkPasswordView() throws Exception {
        solo.waitForActivity("login", 5000);
        EditText inputPassword = (EditText) solo.getView(R.id.text_password);
        solo.enterText(inputPassword, "123");

        // password visibility switcher
        View switcher =solo.getView(R.id.show_password);
        solo.clickOnView(switcher);

        // Validate the text on the TextView
        TextView viewPassword = (TextView) solo.getView(R.id.text_password);
        assertEquals("123", viewPassword.getText().toString());
    }

    /**
     * Closes the activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}


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


public class LoginTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<login> rule =
            new ActivityTestRule<login>(login.class, true, true);

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }


    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }


    @Test
    public void checkRegisterSwitch() {
        solo.assertCurrentActivity("Wrong activity",login.class);
        solo.clickOnText("Sign Up");
        solo.waitForActivity("register",20000);
        solo.assertCurrentActivity("Wrong activity",register.class);
        solo.sleep(20000);
    }

    @Test
    public void checkLoginButton() {
        solo.assertCurrentActivity("Wrong activity",login.class);
        solo.enterText((EditText) solo.getView(R.id.text_username),"sjy");
        solo.enterText((EditText) solo.getView(R.id.text_password),"123456");
        solo.clickOnButton("Login");
        solo.waitForActivity("MainActivity",20000);
        solo.assertCurrentActivity("Wrong activity",MainActivity.class);
        solo.sleep(20000);

    }



    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }
}
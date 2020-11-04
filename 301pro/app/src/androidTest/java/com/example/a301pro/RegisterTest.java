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
public class RegisterTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<register> rule =
            new ActivityTestRule<register>(register.class, true, true);

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }


    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }


    //test if the register button actually work
    @Test
    public void checkLoginSwitch() {
        solo.assertCurrentActivity("Wrong activity",login.class);
        solo.clickOnText("LOGIN");
        solo.waitForActivity("login",10000);
        solo.assertCurrentActivity("Wrong activity",register.class);
        solo.sleep(10000);
    }

    //test if login button actually work
    @Test
    public void checkDoRegisterButton() {
        solo.assertCurrentActivity("Wrong activity",login.class);
        solo.clickOnButton("DONE REGISTER");
        solo.waitForActivity("MainActivity",10000);
        solo.assertCurrentActivity("Wrong activity",login.class);
        solo.sleep(10000);

    }


    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }
}

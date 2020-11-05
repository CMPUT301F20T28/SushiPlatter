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


    //test if the login button in Register class actually work
    @Test
    public void checkLoginSwitch() {
        solo.assertCurrentActivity("Wrong activity",register.class);
        solo.clickOnButton("Login");
        solo.waitForActivity("login",10000);
        solo.assertCurrentActivity("Wrong activity",login.class);
        solo.sleep(10000);

    }

    //test if Register button actually work 有bug,每次需要去firebase删除该用户才能重复运行,或者新建一个不重名的
    @Test
    public void checkDoRegisterButton() {
        solo.assertCurrentActivity("Wrong activity",register.class);

        solo.enterText((EditText)solo.getView(R.id.first_name),"intent_testing1");
        solo.enterText((EditText)solo.getView(R.id.last_name),"intent_testing1");
        solo.enterText((EditText)solo.getView(R.id.phone_num),"123456");
        solo.enterText((EditText)solo.getView(R.id.Email),"intent1@testing.ca");
        solo.enterText((EditText)solo.getView(R.id.text_username),"intent_testing1");
        solo.enterText((EditText)solo.getView(R.id.text_password),"123456");
        solo.enterText((EditText)solo.getView(R.id.text_password_check),"123456");

        solo.sleep(10000);


        solo.clickOnButton("DONE REGISTER");

        solo.waitForActivity("login",10000);
        solo.assertCurrentActivity("Wrong activity",login.class);
        solo.sleep(10000);

    }


    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }
}

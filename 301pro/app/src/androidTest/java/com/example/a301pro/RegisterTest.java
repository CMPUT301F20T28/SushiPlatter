package com.example.a301pro;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robotium.solo.Solo;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Intent testing for Register activity
 */
public class RegisterTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<Register> rule =
            new ActivityTestRule<>(Register.class, true, true);

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
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    /**
     * Test if the Login button in Register class actually work
     */
    @Test
    public void checkLoginSwitch() {
        solo.assertCurrentActivity("Wrong activity", Register.class);
        solo.clickOnView(solo.getView(R.id.login_in_register));
        solo.waitForActivity("Login",10000);
        solo.assertCurrentActivity("Wrong activity", Login.class);
    }

    /**
     * Test if Register button actually work without create new account in database
     * This should fail to create a new account because we have unfilled field
     */
    @Test
    public void checkRegisterButtonWithoutCreateAccount() {
        solo.assertCurrentActivity("Wrong activity", Register.class);
        solo.enterText((EditText)solo.getView(R.id.first_name),"intent_testing1");
        solo.enterText((EditText)solo.getView(R.id.last_name),"intent_testing1");
        // not putting some required fields,
        // in the purpose to test the button without registering a real account
        solo.enterText((EditText)solo.getView(R.id.text_password),"123456");
        solo.enterText((EditText)solo.getView(R.id.text_password_check),"123456");
        solo.clickOnView(solo.getView(R.id.btn_register));
        solo.assertCurrentActivity("Wrong activity", Register.class);
    }

    /**
     * Test if user can switch back to Login page without creating an account
     */
    @Test
    public void checkRegSwitchLog() {
        solo.assertCurrentActivity("Wrong activity", Register.class);
        solo.clickOnView(solo.getView(R.id.login_in_register));
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

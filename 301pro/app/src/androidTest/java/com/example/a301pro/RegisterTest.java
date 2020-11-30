package com.example.a301pro;

import android.app.Activity;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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

    /**
     * Set up test rule
     */
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
        solo.enterText((EditText)solo.getView(R.id.first_name),"intent_testing");
        solo.enterText((EditText)solo.getView(R.id.last_name),"intent_testing");
        // not putting some required fields,
        // in the purpose to test the button without registering a real account
        solo.enterText((EditText)solo.getView(R.id.text_password),"123456");
        solo.enterText((EditText)solo.getView(R.id.text_password_check),"123456");
        solo.sleep(500);
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

    @Test
    public void checkRegister() {
        solo.assertCurrentActivity("Wrong Activity", Register.class);

        String testFirstName = "firstName";
        String testLastName = "lastName";
        String testUsername = "intenttesting";
        final String testEmail = "intent@testing.ca";
        String testPhone = "7801234567";
        final String testPassword = "123456";

        solo.enterText((EditText) solo.getView(R.id.first_name), testFirstName);
        solo.enterText((EditText) solo.getView(R.id.last_name), testLastName);
        solo.enterText((EditText) solo.getView(R.id.phone_num), testPhone);
        solo.enterText((EditText) solo.getView(R.id.Email), testEmail);
        solo.enterText((EditText) solo.getView(R.id.text_username), testUsername);
        solo.enterText((EditText) solo.getView(R.id.text_password), testPassword);
        solo.enterText((EditText) solo.getView(R.id.text_password_check), testPassword);

        solo.clickOnButton("DONE REGISTER");

        solo.waitForActivity(Login.class);
        solo.assertCurrentActivity("Wrong activity", Login.class);
        solo.sleep(1000);

        deleteUser(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    /**
     * Close Activity when done test
     */
    @After
    public void tearDown() {
        getUID();
        solo.finishOpenedActivities();
    }

    /**
     * get the user id
     */
    public void getUID() {

        String testUsername = "intenttesting";

        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("userDict");
        DocumentReference docRef = collectionReference.document(testUsername);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        final String UID = (String) document.getData().get("UID");
                        deleteUser(UID);
                    }
                }
            }
        });
    }

    /**
     * delete the user for the purpose of next test
     * @param UID user id
     */
    public void deleteUser(String UID){
        String testFirstName = "firstName";
        String testLastName = "lastName";
        String testUsername = "intenttesting";
        final String testEmail = "intent@testing.ca";
        String testPhone = "7801234567";
        final String testPassword = "123456";

        FirebaseFirestore.getInstance().collection("userDict").document(testUsername)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });
        FirebaseFirestore.getInstance().collection("Users").document(UID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });
        final FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
        currUser.delete();
        FirebaseAuth authenticatedUser = FirebaseAuth.getInstance();
        authenticatedUser.signOut();

    }
}

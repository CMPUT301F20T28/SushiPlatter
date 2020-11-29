package com.example.a301pro;

import android.widget.EditText;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.rule.ActivityTestRule;

import com.example.a301pro.Functionality.Login;
import com.example.a301pro.View.ViewUserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.contrib.DrawerMatchers.isOpen;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertTrue;

/**
 * Intent testing for personal profile
 */
public class ProfileTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<Register> rule =
            new ActivityTestRule<>(Register.class, true, true);

    /**
     * Set up the start point for activity test
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), rule.getActivity());
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
     * Delete the user in the purpose of next testing
     */
    public void deleteUser() {
        String testUsername = "intent_testing";
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currUser.getUid();
        db.collection("Users").document(uid).delete();
        db.collection("userDict").document(testUsername).delete();
        currUser.delete();
    }

    /**
     * Test if it can edit information of personal profile
     * @throws Exception fail message
     */
    @Test
    public void testEditProfile() throws Exception {
        solo.assertCurrentActivity("Wrong Activity", Register.class);
        String testFirstName = "intent_testing";
        String testLastName = "intent_testing";
        String testUsername = "intent_testing";
        String testEmail = "intent@testing.ca";
        String testPhone = "1234567890";
        String testPassword = "intent_testing";

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
        solo.sleep(500);

        solo.enterText((EditText) solo.getView(R.id.text_username), testUsername);
        solo.enterText((EditText) solo.getView(R.id.text_password), testPassword);
        solo.clickOnButton("Login");
        solo.waitForActivity("MainActivity", 10000);
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.sleep(500);

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());


        onView(withContentDescription("Navigation header")).perform(click());
        solo.waitForActivity("ViewUserProfile", 10000);
        solo.assertCurrentActivity("Wrong activity", ViewUserProfile.class);

        onView(withText("Edit Profile")).perform(click());

        onView(withId(R.id.user_first_name_display)).perform(clearText());
        onView(withId(R.id.user_last_name_display)).perform(clearText());
        onView(withId(R.id.phone_num_display)).perform(clearText());
        onView(withId(R.id.user_email_display)).perform(clearText());

        solo.enterText((EditText) solo.getView(R.id.user_first_name_display), "Edit firstname");
        solo.enterText((EditText) solo.getView(R.id.user_last_name_display), "Edit lastname");
        solo.enterText((EditText) solo.getView(R.id.phone_num_display), "1987654321");
        solo.enterText((EditText) solo.getView(R.id.user_email_display), "edit@ualberta.ca");
        assertTrue(solo.waitForText("Edit firstname"));
        assertTrue(solo.waitForText("Edit lastname"));
        assertTrue(solo.waitForText("1987654321"));
        assertTrue(solo.waitForText("edit@ualberta.ca"));
        onView(withText("Confirm")).perform(click());

        solo.waitForActivity("MainActivity", 10000);
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.sleep(500);

        onView(withText("Logout")).perform(click());
        solo.waitForActivity(Login.class);
        solo.assertCurrentActivity("Wrong activity", Login.class);
        solo.sleep(500);

        // re-login to delete user
        solo.enterText((EditText) solo.getView(R.id.text_username), testUsername);
        solo.enterText((EditText) solo.getView(R.id.text_password), testPassword);
        solo.clickOnButton("Login");
        solo.waitForActivity("MainActivity", 10000);
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        deleteUser();
    }
}

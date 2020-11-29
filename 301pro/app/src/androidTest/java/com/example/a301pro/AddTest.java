package com.example.a301pro;

import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.IdRes;
import androidx.test.rule.ActivityTestRule;

import com.example.a301pro.Functionality.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robotium.solo.Solo;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.example.a301pro.R.id.book_confirm;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.assertTrue;

/**
 * Intent test for adding or editing a book
 */
public class AddTest {
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
     * get match items as a list
     * @param listViewId list view of items
     * @return number of matched items
     */
    public static int getCountFromList(@IdRes int listViewId) {
        final int[] count = {0};
        Matcher matcher = new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                count[0] = ((ListView) item).getCount();
                return true;
            }

            @Override
            public void describeTo(Description description) {
            }
        };

        onView(withId(listViewId)).check(matches(matcher));

        int result = count[0];
        count[0] = 0;
        return result;
    }

    /**
     * test to add book without updating the book image
     * @throws Exception fail message
     */
    @Test
    public void testAddWithoutImages() throws Exception {
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

        solo.enterText((EditText) solo.getView(R.id.text_username), testUsername);
        solo.enterText((EditText) solo.getView(R.id.text_password), testPassword);
        solo.clickOnButton("Login");
        solo.waitForActivity("MainActivity", 10000);
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);

        solo.clickOnView(solo.getView(R.id.add_book_button));
        solo.waitForActivity(AddEditIntent.class);
        solo.assertCurrentActivity("Wrong Activity", AddEditIntent.class);

        // add new book for testing
        solo.enterText((EditText) solo.getView(R.id.book_name_editText), "Test Book Name");
        solo.enterText((EditText) solo.getView(R.id.author_editText), "Test Book Author");
        solo.enterText((EditText) solo.getView(R.id.ISBN_editText), "1234567891011");
        solo.enterText((EditText) solo.getView(R.id.description), "Test Book Description");
        solo.sleep(500);
        onView(withId(book_confirm)).perform(click());

        solo.waitForActivity(MainActivity.class);
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        assertTrue(solo.waitForText("Test Book Description"));
        assertTrue(solo.waitForText("Test Book Name"));

        // test edit book information function
        onData(anything()).inAdapterView(withId(R.id.my_book_list)).atPosition(0).perform(click());

        solo.waitForActivity(AddEditIntent.class);
        solo.assertCurrentActivity("Wrong Activity", AddEditIntent.class);

        assertTrue(solo.waitForText("Test Book Description"));
        assertTrue(solo.waitForText("Test Book Name"));
        assertTrue(solo.waitForText("1234567891011"));
        assertTrue(solo.waitForText("Test Book Author"));

        onView(withId(R.id.book_name_editText)).perform(clearText());
        onView(withId(R.id.author_editText)).perform(clearText());
        onView(withId(R.id.ISBN_editText)).perform(clearText());
        onView(withId(R.id.description)).perform(clearText());

        solo.enterText((EditText) solo.getView(R.id.book_name_editText), "Edit Book Name");
        solo.enterText((EditText) solo.getView(R.id.author_editText), "Edit Book Author");
        solo.enterText((EditText) solo.getView(R.id.ISBN_editText), "1234567891213");
        solo.enterText((EditText) solo.getView(R.id.description), "Edit Book Description");
        solo.sleep(500);
        onView(withId(book_confirm)).perform(click());

        solo.waitForActivity(MainActivity.class);
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        assertTrue(solo.waitForText("Edit Book Description"));
        assertTrue(solo.waitForText("Edit Book Name"));

        Assert.assertEquals(getCountFromList(R.id.my_book_list), 1);
        // test cancel button
        onData(anything()).inAdapterView(withId(R.id.my_book_list)).atPosition(0).perform(click());
        solo.waitForActivity(AddEditIntent.class);
        solo.assertCurrentActivity("Wrong Activity", AddEditIntent.class);

        onView(withId(R.id.add_edit_quit)).perform(click());
        solo.waitForActivity(MainActivity.class);
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        assertTrue(solo.waitForText("Edit Book Description"));
        assertTrue(solo.waitForText("Edit Book Name"));
        solo.sleep(500);
        // test cancel in alert dialog
        onData(anything()).inAdapterView(withId(R.id.my_book_list)).atPosition(0).perform(longClick());
        onView(withText("Are you sure to delete\"Edit Book Name\"?"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
        onView(withId(android.R.id.button2)).perform(click());
        Assert.assertEquals(getCountFromList(R.id.my_book_list), 1);
        solo.sleep(500);
        // test delete book function
        onData(anything()).inAdapterView(withId(R.id.my_book_list)).atPosition(0).perform(longClick());
        onView(withText("Are you sure to delete\"Edit Book Name\"?"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
        onView(withId(android.R.id.button1)).perform(click());
        Assert.assertEquals(getCountFromList(R.id.my_book_list), 0);
        solo.sleep(500);
        deleteUser();
    }
}
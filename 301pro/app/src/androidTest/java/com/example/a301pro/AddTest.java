package com.example.a301pro;


import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
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

public class AddTest {

    private Solo solo;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Rule
    public ActivityTestRule<Register> rule =
            new ActivityTestRule<Register>(Register.class, true, true);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), rule.getActivity());

    }

    @After
    public void tearDown() throws Exception {
        getUID();
        solo.finishOpenedActivities();
    }

    public void getUID() {

        String testUsername = "username";


        CollectionReference collectionReference = db.collection("userDict");
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

    public void deleteUser(String UID){
        String testFirstName = "firstName";
        String testLastName = "lastName";
        String testUsername = "username";
        final String testEmail = "firstname@uablerta.ca";
        String testPhone = "1234567890";

        db.collection("userDict").document(testUsername)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });
        db.collection("Users").document(UID)
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

    @Test
    public void testAddWithoutImages() throws Exception {


        solo.assertCurrentActivity("Wrong Activity", Register.class);

        String testFirstName = "firstName";
        String testLastName = "lastName";
        String testUsername = "username";
        final String testEmail = "firstname@uablerta.ca";
        String testPhone = "1234567890";
        final String testPassword = "88888888";


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
        solo.sleep(10000);

        solo.enterText((EditText) solo.getView(R.id.text_username), testUsername);
        solo.enterText((EditText) solo.getView(R.id.text_password), testPassword);
        solo.clickOnButton("Login");
        solo.waitForActivity("MainActivity", 10000);
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.sleep(10000);

        solo.clickOnView(solo.getView(R.id.add_book_button));

        solo.waitForActivity(AddEditIntent.class);
        solo.assertCurrentActivity("Wrong Activity", AddEditIntent.class);

        // add new book for testing
        solo.enterText((EditText) solo.getView(R.id.book_name_editText), "Test Book Name");
        solo.enterText((EditText) solo.getView(R.id.author_editText), "Test Book Author");
        solo.enterText((EditText) solo.getView(R.id.ISBN_editText), "1234567891011");
        solo.enterText((EditText) solo.getView(R.id.description), "Test Book Description");

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

        // test cancel in alertdialog
        onData(anything()).inAdapterView(withId(R.id.my_book_list)).atPosition(0).perform(longClick());
        onView(withText("Are you sure to delete\"Edit Book Name\"?"))
                .inRoot(isDialog()) // <---
                .check(matches(isDisplayed()));
        onView(withId(android.R.id.button2)).perform(click());
        Assert.assertEquals(getCountFromList(R.id.my_book_list), 1);

        // test delete book function
        onData(anything()).inAdapterView(withId(R.id.my_book_list)).atPosition(0).perform(longClick());
        onView(withText("Are you sure to delete\"Edit Book Name\"?"))
                .inRoot(isDialog()) // <---
                .check(matches(isDisplayed()));
        onView(withId(android.R.id.button1)).perform(click());
        Assert.assertEquals(getCountFromList(R.id.my_book_list), 0);



    }
}






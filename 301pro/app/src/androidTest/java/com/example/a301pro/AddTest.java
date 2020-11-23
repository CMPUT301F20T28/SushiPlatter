package com.example.a301pro;


import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.test.rule.ActivityTestRule;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertTrue;

public class AddTest {

    private Solo solo;


    @Rule
    public ActivityTestRule<register> rule =
            new ActivityTestRule<register>(register.class, true, true);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), rule.getActivity());

    }

    @After
    public void tearDown() throws Exception {
        deleteUser();
        solo.finishOpenedActivities();
    }

    public void deleteUser() throws Exception {
        String testFirstName = "firstName";
        String testLastName = "lastName";
        String testUsername = "username";
        final String testEmail = "firstname@uablerta.ca";
        String testPhone = "1234567890";
        final String testPassword = "88888888";

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        final FirebaseUser testuser = FirebaseAuth.getInstance().getCurrentUser();

        db.collection("userDict").whereEqualTo("username", testUsername).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String docId = document.getId();
                                db.collection("userDict").document(docId).delete();
                                db.collection("Users").document(docId).delete();
                                testuser.delete();
                                FirebaseAuth authenticatedUser = FirebaseAuth.getInstance();
                                authenticatedUser.signOut();
                            }
                        }
                    }
                });
    }

    @Test
    public void testAddBookWithoutImages() throws Exception {


        solo.assertCurrentActivity("Wrong Activity", register.class);

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

        solo.waitForActivity(login.class);
        solo.assertCurrentActivity("Wrong activity",login.class);
        solo.sleep(10000);

        solo.enterText((EditText) solo.getView(R.id.text_username),testUsername);
        solo.enterText((EditText) solo.getView(R.id.text_password),testPassword);
        solo.clickOnButton("Login");
        solo.waitForActivity("MainActivity",10000);
        solo.assertCurrentActivity("Wrong activity",MainActivity.class);
        solo.sleep(10000);

        solo.clickOnView(solo.getView(R.id.add_book_button));

        solo.waitForActivity(AddEditIntent.class);
        solo.assertCurrentActivity("Wrong Activity", AddEditIntent.class);


        solo.enterText((EditText) solo.getView(R.id.book_name_editText), "Test Book Name");
        solo.enterText((EditText) solo.getView(R.id.author_editText), "Test Book Author");
        solo.enterText((EditText) solo.getView(R.id.ISBN_editText), "1234567891011");
        solo.enterText((EditText) solo.getView(R.id.description), "Test Book Description");

        onView(withId(R.id.book_confirm)).perform(click());

        solo.waitForActivity(MainActivity.class);
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        assertTrue(solo.waitForText("Test Book Description"));
        assertTrue(solo.waitForText("Test Book Name"));

    }
}






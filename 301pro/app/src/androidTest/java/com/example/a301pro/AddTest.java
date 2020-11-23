package com.example.a301pro;


import android.widget.EditText;

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
    final FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Rule
    public ActivityTestRule<register> rule =
            new ActivityTestRule<register>(register.class, true, true);

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






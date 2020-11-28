package com.example.a301pro.Utilities;

import com.google.firebase.auth.FirebaseAuth;

public class GetUserFromDB {
    /**
     * Get uid of the current logged in user
     * @return uid as a string
     */
    public static String getUserID() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public static String getUsername() {
        return FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    }
}

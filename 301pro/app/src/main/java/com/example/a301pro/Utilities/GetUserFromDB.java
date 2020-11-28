package com.example.a301pro.Utilities;

import com.google.firebase.auth.FirebaseAuth;

/**
 * This is tool class for getting user info from database
 */
public class GetUserFromDB {
    /**
     * Get uid of the current logged in user
     * @return uid as a string
     */
    public static String getUserID() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    /**
     * Get username of the current logged in user
     * @return username as a string
     */
    public static String getUsername() {
        return FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    }
}

package com.iwillcode.limetraytwitterassignment.Utilities;


public interface Preferences {

    interface PrefNames {

        String PREFS_NAME_TAG = "twitterSamplePrefs";

    }


    interface PrefKeys {


        String PREF_KEY_TWITTER_LOGIN = "twitterLogin";

        /**
         * Identifier for user accessToken
         */
        String PREF_KEY_ACCESS_TOKEN = "accessToken";

        /**
         * Identifier for user accessSecret
         */
        String PREF_KEY_ACCESS_SECRET = "accessSecret";

        /**
         * Identifier for twitter username
         */
        String PREF_KEY_USER_NAME = "twitterUser";

    }

    /**
     * Constants used as preference values.
     */
    interface PrefValues {

    }
}
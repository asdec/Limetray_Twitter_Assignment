package com.iwillcode.limetraytwitterassignment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.iwillcode.limetraytwitterassignment.LimetrayUI.LimetrayGraph;
import com.iwillcode.limetraytwitterassignment.LimetrayUI.LimetrayTweets;
import com.iwillcode.limetraytwitterassignment.Utilities.Constants;
import com.iwillcode.limetraytwitterassignment.Utilities.SharedPreferencesHandler;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;


public class MainActivity extends Activity {


    private static final String TAG = MainActivity.class.getCanonicalName();


    private static Twitter twitter;

    private Button loginBtn;
    private ImageView twitterLogo;
    private TextView welcomeLabel;
    private ProgressDialog progress;
    private Button graphButton;
    private Button tweetListButton;
    private LinearLayout limetrayButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeLabel = (TextView) findViewById(R.id.welcome_label);
        twitterLogo = (ImageView) findViewById(R.id.twitter_logo);
        graphButton = (Button) findViewById(R.id.LimetrayGraphButton);
        tweetListButton =(Button) findViewById(R.id.limetrayTweetButton);
        limetrayButtons = (LinearLayout)findViewById(R.id.limetrayButtons);

        loginBtn = (Button) findViewById(R.id.login_button);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTwitterLoggedInAlready()) {
                    logout();
                } else {
                    new TwitterLoginTask().execute();
                }
            }
        });

        if (isTwitterLoggedInAlready())
            updateLoggedInUI(SharedPreferencesHandler.getTwitterUsername(MainActivity.this));
    }

    private void updateLoggedInUI(String username) {
        welcomeLabel.setText(String.format(getString(R.string.welcome), username));
        loginBtn.setText(R.string.tw_logout_hint);
        limetrayButtons.setVisibility(View.VISIBLE);


        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LimetrayGraph.class);
                startActivity(intent);
            }
        });


        tweetListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LimetrayTweets.class);
                startActivity(intent);
            }
        });


        twitterLogo.setVisibility(View.VISIBLE);
        twitterLogo.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.LOGIN_TO_TWITTER) {
            if (resultCode == Activity.RESULT_OK) {
                getAccessToken(data.getStringExtra(Constants.EXTRA_CALLBACK_URL_KEY));
            }
        }
    }

    private void getAccessToken(final String callbackUrl) {

        Uri uri = Uri.parse(callbackUrl);
        String verifier = uri.getQueryParameter("oauth_verifier");

        GetAccessTokenTask getAccessTokenTask = new GetAccessTokenTask();
        getAccessTokenTask.execute(verifier);
    }


    private boolean isTwitterLoggedInAlready() {
        // return twitter login status from Shared Preferences
        return SharedPreferencesHandler.isTwitterLoggedInAlready(MainActivity.this);
    }


    private class TwitterLoginTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // Check if already logged in
            if (!isTwitterLoggedInAlready()) {
                ConfigurationBuilder builder = new ConfigurationBuilder();
                builder.setOAuthConsumerKey(getString(R.string.twitter_consumer_key));
                builder.setOAuthConsumerSecret(getString(R.string.twitter_consumer_secret));
                Configuration configuration = builder.build();

                TwitterFactory factory = new TwitterFactory(configuration);
                twitter = factory.getInstance();

                try {
                    RequestToken requestToken = twitter.getOAuthRequestToken(getString(R.string.twitter_callback_url));

                    launchLoginWebView(requestToken);

                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            if (isTwitterLoggedInAlready()) {
                Log.w(TAG, String.format(getString(R.string.already_logged), SharedPreferencesHandler.getTwitterUsername(MainActivity.this)));
                welcomeLabel.setText(String.format(getString(R.string.welcome), SharedPreferencesHandler.getTwitterUsername(MainActivity.this)));
                loginBtn.setText(R.string.tw_logout_hint);
            }
        }
    }

    private void launchLoginWebView(RequestToken requestToken) {
        Intent intent = new Intent(this, LoginToTwitter.class);
        intent.putExtra(Constants.EXTRA_AUTH_URL_KEY, requestToken.getAuthenticationURL());
        startActivityForResult(intent, Constants.LOGIN_TO_TWITTER);
    }

    private class GetAccessTokenTask extends AsyncTask<String, Void, User> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(MainActivity.this, getString(R.string.loading_title),
                    getString(R.string.login_dialog_message), true);
        }

        @Override
        protected User doInBackground(String... strings) {
            String verifier = strings[0];
            User user = null;
            try {
                AccessToken accessToken = twitter.getOAuthAccessToken(verifier);

                // store the access token and access token secret in application preferences
                SharedPreferencesHandler.setTwitterAccessToken(MainActivity.this, accessToken.getToken());
                SharedPreferencesHandler.setTwitterAccessSecret(MainActivity.this, accessToken.getTokenSecret());
                // Store login status - true
                SharedPreferencesHandler.setTwitterLoggedIn(MainActivity.this, true);

                Log.d(TAG, "Twitter OAuth Token: " + accessToken.getToken());

                // Getting user details from twitter

                user = twitter.showUser(accessToken.getUserId());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);

            if (user != null) {
                progress.dismiss();

                SharedPreferencesHandler.setTwitterUsername(MainActivity.this, user.getName());
                Toast.makeText(MainActivity.this, String.format(getString(R.string.welcome), user.getName()), Toast.LENGTH_SHORT).show();
                updateLoggedInUI(user.getName());
            }
        }
    }


    private void logout() {
        SharedPreferencesHandler.clearCredentials(MainActivity.this);
        if (twitter != null)
            twitter.setOAuthAccessToken(null);
        welcomeLabel.setText("");
        loginBtn.setText(R.string.tw_login_hint);
        twitterLogo.clearAnimation();
        twitterLogo.setVisibility(View.GONE);
        limetrayButtons.setVisibility(View.GONE);
        Toast.makeText(MainActivity.this, getString(R.string.logged_out), Toast.LENGTH_SHORT).show();
    }
}
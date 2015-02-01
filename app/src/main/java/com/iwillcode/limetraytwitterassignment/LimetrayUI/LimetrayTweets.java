package com.iwillcode.limetraytwitterassignment.LimetrayUI;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.iwillcode.limetraytwitterassignment.R;
import com.iwillcode.limetraytwitterassignment.Utilities.Constants;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class LimetrayTweets extends Activity {

    public static final String TAG = "TweetSearch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limetray_tweets);

        SearchTweet("Limetray");



    }

    public static String SearchTweet(String searchTerm) {
        HttpURLConnection httpConnection = null;
        BufferedReader bufferedReader = null;
        StringBuilder response = new StringBuilder();

        try {



            URL url = new URL(Constants.URL_SEARCH +  URLEncoder.encode("#" + searchTerm) + "&result_type=mixed&lang=en");
            Log.e(TAG, "url twitter search: " + url.toString());

            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");


            String jsonString = appAuthentication();
            JSONObject jsonObjectDocument = new JSONObject(jsonString);
            String token = jsonObjectDocument.getString("token_type") + " " +
                    jsonObjectDocument.getString("access_token");

            httpConnection.setRequestProperty("Authorization", token);
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.connect();

            bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null){
                response.append(line);
            }

            Log.d(TAG, "GET response code: " + String.valueOf(httpConnection.getResponseCode()));
            Log.d(TAG, "JSON response: " + response.toString());


        } catch (Exception e) {
            Log.e(TAG, "GET error: " + Log.getStackTraceString(e));

        }finally {
            if(httpConnection != null){
                httpConnection.disconnect();

            }
        }

        return response.toString();
    }

    public static String appAuthentication(){

        HttpURLConnection httpConnection = null;
        OutputStream outputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder response = null;

        try {
            URL url = new URL(Constants.URL_AUTHENTICATION);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("POST");
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);

            String accessCredential = Constants.CONSUMER_KEY + ":" + Constants.CONSUMER_SECRET;
            String authorization = "Basic " + Base64.encodeToString(accessCredential.getBytes(), Base64.NO_WRAP);
            String param = "grant_type=client_credentials";

            httpConnection.addRequestProperty("Authorization", authorization);
            httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            httpConnection.connect();

            outputStream = httpConnection.getOutputStream();
            outputStream.write(param.getBytes());
            outputStream.flush();
            outputStream.close();
//            int statusCode = httpConnection.getResponseCode();
//            String reason =httpConnection.getResponseMessage();

            bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String line;
            response = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null){
                response.append(line);
            }

            Log.d(TAG, "POST response code: " + String.valueOf(httpConnection.getResponseCode()));
            Log.d(TAG, "JSON response: " + response.toString());

        } catch (Exception e) {
            Log.e(TAG, "POST error: " + Log.getStackTraceString(e));

        }finally{
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }
        return response.toString();
    }






}


    /*private void searchTweets() {

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(getString(R.string.twitter_consumer_key))
                .setOAuthConsumerSecret(getString(R.string.twitter_consumer_secret))
                .setOAuthAccessToken(getString(R.string.twitter_access_token))
                .setOAuthAccessTokenSecret(getString(R.string.twitter_token_secret));

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();


        try {
            Query query = new Query("Limetray");
            QueryResult result;
            try {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    // System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                    Log.e("TweetSearch", tweet.getUser().getScreenName() + " - " + tweet.getText());

                }
            }
            //while ((query = result.nextQuery()) != null);
            //System.exit(0);
            catch (TwitterException te) {
                te.printStackTrace();
                // System.out.println("Failed to search tweets: " + te.getMessage());

                Log.e("TweetSearch", te.getMessage());
                System.exit(-1);
            }
        }finally {
            Log.e("TweetSearch", "Final");
        }
    }*/



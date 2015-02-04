package com.iwillcode.limetraytwitterassignment.LimetrayUI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.iwillcode.limetraytwitterassignment.R;
import com.iwillcode.limetraytwitterassignment.Utilities.Constants;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class LimetrayTweets extends Activity {


    private Button search;
    public int x=0;
    public int y=0;
    ListView list;
    String searchText = "Limetray";
    ArrayList<String> tweetTexts = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limetray_tweets);


        list = (ListView) findViewById(R.id.listView);
        search=(Button)findViewById(R.id.searchLimetray);
        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                new getTweets().execute();
            }
        });


    }

    private class getTweets extends AsyncTask<String, String, ArrayList<String>> {


        @Override
        protected void onPreExecute() {

            super.onPreExecute();


        }

        @Override
        protected ArrayList<String> doInBackground(String... arg0) {

            List<twitter4j.Status> tweets = new ArrayList();
            tweetTexts.clear();

            Twitter mTwitter = getTwitter();
            try {

                tweets = mTwitter.search(new Query(searchText)).getTweets();
                for (twitter4j.Status t : tweets) {
                tweetTexts.add(t.getText() + "\n\n");
                }

            } catch (Exception e) {
                Log.e("Error", "Exception");
            }

            getCount();

            return tweetTexts;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {

            super.onPostExecute(result);
            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, result);
            list.setAdapter(itemsAdapter);

            Toast.makeText(LimetrayTweets.this, "Tweet searched ", Toast.LENGTH_SHORT).show();

        }

        private Twitter getTwitter() {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(getString(R.string.twitter_consumer_key));
            builder.setOAuthConsumerSecret(getString(R.string.twitter_consumer_secret));
            builder.setOAuthAccessToken(getString(R.string.twitter_access_token))
            .setOAuthAccessTokenSecret(getString(R.string.twitter_token_secret));
            Twitter mTwitter = new TwitterFactory(builder.build()).getInstance();
            return mTwitter;
        }


    }

    public int getCount()  {

        int countSize = tweetTexts.size();

        return countSize;

    }

}





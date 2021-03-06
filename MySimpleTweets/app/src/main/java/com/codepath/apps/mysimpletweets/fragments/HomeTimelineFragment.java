package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by caguillon on 6/28/16.
 */
public class HomeTimelineFragment extends TweetsListFragment {

    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get the client
        client = TwitterApplication.getRestClient(); //singleton client
        populateTimeline();
    }

    //Send an API request to get the timeline json
    //Fill the listview by creating the tweet objects from the json
    private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            //SUCCESS
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                //deserialize JSON
                //create models and add them to the adapter
                //load the model data into listview
                addAll(Tweet.fromJSONArray(json));
            }

            //FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }
}

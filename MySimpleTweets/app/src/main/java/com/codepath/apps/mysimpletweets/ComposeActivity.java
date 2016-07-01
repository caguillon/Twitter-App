package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client = TwitterApplication.getRestClient();
    }

    private void populateProfileHeader(User user) {
        EditText etComposeTweet = (EditText) findViewById(R.id.etComposeTweet);
        etComposeTweet.setText(user.getName());
    }

    public void onSubmit(View view) {
        // Get tweet and add it to timeline
        EditText etTweet = (EditText) findViewById(R.id.etComposeTweet);

        String strValue = etTweet.getText().toString();
        client.postNewTweet(strValue, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Tweet tweet = Tweet.fromJSON(response);

                //Toast.makeText(this, "Saved tweet: " + strValue, Toast.LENGTH_SHORT).show();

                Intent data = new Intent();
                data.putExtra("Tweet", tweet);

                setResult(RESULT_OK, data); // set result code and bundle data for response

                finish(); // closes the activity, pass data to parent
            }
        });
    }

    /*
    ArrayList<Tweet> tweets = new ArrayList<>();
    TweetsArrayAdapter adapter = new ArticleArrayAdapter(this, tweets);
    tweetJsonResults = response.getJSONObject("response").getJSONArray("docs");
    adapter.addAll(Tweet.fromJSONArray(tweetJsonResults));
    * */
}

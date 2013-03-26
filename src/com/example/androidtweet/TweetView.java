package com.example.androidtweet;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TweetView extends Activity {

    private Twitter t;
    private Status status;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.tweet_view);

	t = MainActivity.t;

	Bundle b = getIntent().getExtras();

	if (b != null) {
	    try {
		status = t.showStatus(b.getLong("statusid"));
	    } catch (TwitterException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

	if (status != null) {
	    TextView tv;
	    tv = (TextView) findViewById(R.id.tweetview_message);
	    tv.setText(status.getText());
	    tv = (TextView) findViewById(R.id.tweetview_time);
	    tv.setText(status.getCreatedAt().toString());
	}
    }
}

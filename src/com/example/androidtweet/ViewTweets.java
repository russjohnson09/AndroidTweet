package com.example.androidtweet;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.GestureDetector;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ViewTweets extends ListActivity {

    private Twitter t;

    private long tweet;

    private TweetAdapter ta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	ListView listView = getListView();
	listView.setTextFilterEnabled(true);

	t = MainActivity.t;

	try {
	    ta = new TweetAdapter(this, (ArrayList<Status>) t.getUserTimeline());
	    setListAdapter(ta);
	} catch (TwitterException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    @Override
    protected void onListItemClick(ListView l, View v, int pos, long id) {

	tweet = ta.getItemId(pos);

	final CharSequence[] selections = { "View", "Delete" };

	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	builder.setTitle("Options");
	builder.setItems(selections, new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int pos) {
		switch (pos) {
		case 0:
		    Intent i = new Intent(getApplicationContext(),
			    TweetView.class);
		    i.putExtra("statusid", tweet);
		    startActivity(i);
		    break;
		case 1:
		    try {
			t.destroyStatus(tweet);
			ta.remove(ta.getItem(pos));
		    } catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		    break;
		}
	    }
	}).show();
    }
}

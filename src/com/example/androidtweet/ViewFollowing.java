package com.example.androidtweet;

import java.util.ArrayList;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class ViewFollowing extends ListActivity {

    private Twitter t;

    private UserAdapter ua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	ListView listView = getListView();
	listView.setTextFilterEnabled(false);

	t = MainActivity.t;

	ua = new UserAdapter(this, getFollowing());

	setListAdapter(ua);
    }

    private ArrayList<User> getFollowing() {
	ArrayList<User> users = new ArrayList<User>();
	try {
	    long[] list = t.getFriendsIDs(-1).getIDs();
	    for (long l : list) {
		users.add(t.showUser(l));
	    }
	} catch (TwitterException e) {
	    e.printStackTrace();
	}
	return users;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int pos, long id) {

	Intent i = new Intent(this, UserView.class);
	i.putExtra("userid", ua.getItemId(pos));
	startActivity(i);

    }
}

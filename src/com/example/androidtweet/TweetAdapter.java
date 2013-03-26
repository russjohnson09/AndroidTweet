package com.example.androidtweet;

import java.util.ArrayList;

import twitter4j.Status;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TweetAdapter extends ArrayAdapter<Status> {

    private ArrayList<Status> tweets;

    private Context context;

    public TweetAdapter(Context context, ArrayList<Status> values) {
	super(context, R.layout.view_tweets, values);
	this.context = context;
	this.tweets = values;
    }

    @Override
    public int getCount() {
	return tweets.size();
    }

    @Override
    public Status getItem(int pos) {
	return tweets.get(pos);
    }

    @Override
    public long getItemId(int pos) {
	return tweets.get(pos).getId();
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
	LayoutInflater inflater = (LayoutInflater) context
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	View rowView = inflater.inflate(R.layout.view_tweets, parent, false);
	TextView textView = (TextView) rowView
		.findViewById(R.id.viewtweets_text);
	textView.setText(tweets.get(pos).getText());

	return rowView;
    }

}

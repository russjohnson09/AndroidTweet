package com.example.androidtweet;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import twitter4j.User;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UserAdapter extends ArrayAdapter<User> {

    private ArrayList<User> users;

    private Context context;

    public UserAdapter(Context context, ArrayList<User> values) {
	super(context, R.layout.view_users, values);
	this.context = context;
	this.users = values;
    }

    @Override
    public int getCount() {
	return users.size();
    }

    @Override
    public User getItem(int pos) {
	return users.get(pos);
    }

    @Override
    public long getItemId(int pos) {
	return users.get(pos).getId();
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
	LayoutInflater inflater = (LayoutInflater) context
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	View rowView = inflater.inflate(R.layout.view_users, parent, false);
	TextView textView = (TextView) rowView
		.findViewById(R.id.viewusers_text);
	ImageView imageView = (ImageView) rowView
		.findViewById(R.id.viewusers_image);
	textView.setText("@" + users.get(pos).getScreenName());

	setupImageView(imageView, users.get(pos));

	return rowView;
    }

    private void setupImageView(ImageView imageView, User u) {
	Bitmap bitmap = null;
	try {
	    bitmap = BitmapFactory.decodeStream((InputStream) new URL(u
		    .getBiggerProfileImageURL()).getContent());
	    if (bitmap == null)
		bitmap = BitmapFactory.decodeStream((InputStream) new URL(u
			.getProfileImageURL()).getContent());
	} catch (MalformedURLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IllegalStateException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	if (bitmap != null)
	    imageView.setImageBitmap(bitmap);
	else
	    imageView.setImageResource(R.drawable.ic_launcher);

    }
}

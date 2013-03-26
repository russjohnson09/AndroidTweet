package com.example.androidtweet;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class UserView extends Activity {

    private Twitter t;
    private User u;

    @Override
    public void onCreate(Bundle ice) {
	super.onCreate(ice);
	setContentView(R.layout.user_view);

	t = MainActivity.t;

	Bundle extras = getIntent().getExtras();

	u = null;
	if (extras != null) {
	    try {
		u = t.showUser(extras.getLong("userid"));
	    } catch (TwitterException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

	if (u != null) {
	    setImage();
	    TextView tv;
	    tv = (TextView) findViewById(R.id.user_name);
	    tv.setText(u.getName());
	    tv = (TextView) findViewById(R.id.user_profilename);
	    tv.setText("@" + u.getScreenName());
	}

    }

    private void setImage() {
	ImageView imageView = (ImageView) findViewById(R.id.user_image);
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

package com.example.androidtweet;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidtweet.R.id;

public class MainActivity extends Activity implements OnClickListener {

    private RequestToken rt;

    private final String CONSUMER_KEY = "yNBLlBrsFHz89PyCfjrAw";
    private final String CONSUMER_KEY_SECRET = "8SIq5OXfeIKabtB3B2CBHJVIkrjQbSPloHoTmxtis4";

    public static Twitter t;
    public static User user;

    private EditText message;

    private ImageView profileImage;

    private SharedPreferences pref;
    private String at;
    private String ats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	pref = PreferenceManager.getDefaultSharedPreferences(this);

	message = (EditText) findViewById(R.id.message);

	t = new TwitterFactory().getInstance();

	t.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);

	at = pref.getString("AccessToken", null);
	ats = pref.getString("AccessTokenSecret", null);

	if (at == null || ats == null) {
	    try {
		verify();
	    } catch (TwitterException e) {
		e.printStackTrace();
	    }
	} else {
	    t.setOAuthAccessToken(new AccessToken(at, ats));
	    setUp();
	}

    }

    private void verify() throws TwitterException {

	AlertDialog.Builder alert = new AlertDialog.Builder(this);

	alert.setTitle("Enter Pin");

	// Set an EditText view to get user input
	final EditText input = new EditText(this);
	alert.setView(input);

	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

	    @Override
	    public void onClick(DialogInterface dialog, int whichButton) {
		setAuth(input.getText().toString());
		setUp();
	    }
	});

	alert.setNegativeButton("Cancel",
		new DialogInterface.OnClickListener() {

		    @Override
		    public void onClick(DialogInterface dialog, int whichButton) {
			onDestroy();
		    }
		});

	alert.show();

	rt = t.getOAuthRequestToken();
	String auth = rt.getAuthenticationURL();
	Intent i = new Intent(Intent.ACTION_VIEW);
	i.setData(Uri.parse(auth));
	startActivity(i);

    }

    private void setAuth(String pin) {
	AccessToken accessToken;
	try {
	    accessToken = t.getOAuthAccessToken(rt, pin);

	    Editor edit = pref.edit();
	    edit.putString("AccessToken", accessToken.getToken());
	    edit.putString("AccessTokenSecret", accessToken.getTokenSecret());
	    edit.commit();

	    t.setOAuthAccessToken(accessToken);

	} catch (TwitterException te) {
	    te.printStackTrace();
	}

    }

    private void setUp() {

	try {
	    user = t.showUser(t.getId());
	} catch (IllegalStateException e) {
	    e.printStackTrace();
	} catch (TwitterException e) {
	    e.printStackTrace();
	}

	TextView tv;
	tv = (TextView) findViewById(R.id.main_name);
	tv.setText(user.getName());
	tv = (TextView) findViewById(R.id.main_screenname);
	tv.setText(user.getScreenName());

	setUpImage();

    }

    private void setUpImage() {
	profileImage = (ImageView) findViewById(id.profile_image);
	Bitmap bitmap = null;
	try {
	    bitmap = BitmapFactory.decodeStream((InputStream) new URL(t
		    .showUser(t.getId()).getBiggerProfileImageURL())
		    .getContent());
	    if (bitmap == null)
		bitmap = BitmapFactory
			.decodeStream((InputStream) new URL(t.showUser(
				t.getId()).getProfileImageURL()).getContent());
	} catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IllegalStateException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (TwitterException e) {
	    e.printStackTrace();
	}
	if (bitmap != null)
	    profileImage.setImageBitmap(bitmap);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.main, menu);
	return true;
    }

    @Override
    public void onClick(View v) {

	switch (v.getId()) {
	case R.id.send:
	    try {
		t.updateStatus(message.getText().toString());

		Toast.makeText(this, "Tweet Sent", Toast.LENGTH_SHORT).show();
	    } catch (TwitterException e) {
		Toast.makeText(this, "Tweet Not Sent", Toast.LENGTH_SHORT)
			.show();
		e.printStackTrace();
	    }
	    break;
	case R.id.view_tweets:
	    startActivity(new Intent(this, ViewTweets.class));
	    break;
	case R.id.view_followers:
	    startActivity(new Intent(this, ViewFollowers.class));
	    break;
	case R.id.view_following:
	    startActivity(new Intent(this, ViewFollowing.class));
	    break;
	case R.id.view_profile:
	    Intent i = new Intent(this, UserView.class);
	    i.putExtra("userid", user.getId());
	    startActivity(i);
	    break;

	}

    }
}

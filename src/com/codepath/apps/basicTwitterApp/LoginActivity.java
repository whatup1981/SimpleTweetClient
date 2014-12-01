package com.codepath.apps.basicTwitterApp;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;


import com.codepath.apps.basicTwitterApp.models.User;
import com.codepath.oauth.OAuthLoginActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

public class LoginActivity extends OAuthLoginActivity<TwitterRestClient> {
	private User u;
	private Intent i ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	// OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
	@Override
	public void onLoginSuccess() {
		i = new Intent(this, TimelineActivity.class);
		getClient().getVerifyCredentials(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				u = User.fromJson(json);
				i.putExtra("login_user", u);
				startActivity(i);
			}
			@Override
			public void onFailure(Throwable e, String arg1) {
				// TODO Auto-generated method stub
				Log.d("debug", arg1);
				Log.d("debug", e.toString());
			}
		 });
		 

	}

	// OAuth authentication flow failed, handle the error
	// i.e Display an error dialog or toast
	@Override
	public void onLoginFailure(Exception e) {
		e.printStackTrace();
	}

	// Click handler method for the button used to start OAuth flow
	// Uses the client to initiate OAuth authorization
	// This should be tied to a button used to login
	public void loginToRest(View view) {
		getClient().connect();
	}

}

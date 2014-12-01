package com.codepath.apps.basicTwitterApp;

import org.json.JSONObject;

import com.codepath.apps.basicTwitterApp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NewTweetActivity extends Activity {
	private ImageView ivImageProfile;
	private TextView tvMyName;
	private EditText etContent; 
	private Button btSubmit;
	private TwitterRestClient client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_tweet);

		init();
		
	}

	private void init() {
		Bundle extras = getIntent().getExtras();
		User u = (User)extras.getSerializable("login_user");
		ivImageProfile = (ImageView) findViewById(R.id.ivMyprofile);
		ivImageProfile.setImageResource(android.R.color.transparent);
		ImageLoader il = ImageLoader.getInstance();
		il.displayImage(u.getProfileImageUrl(), ivImageProfile);
		
		tvMyName = (TextView) findViewById(R.id.tvMyName);
		tvMyName.setText("@" + u.getName());
		
		etContent = (EditText) findViewById(R.id.etContent);
		btSubmit = (Button) findViewById(R.id.btTweet);
		
		client = TwitterApp.getRestClient();
		btSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String status = etContent.getText().toString(); 
				if (!status.equals((""))) {
					client.postStatusesUpdate(status, new JsonHttpResponseHandler(){
						public void onSuccess(JSONObject arg0) {						
							Intent intent = new Intent();
						    setResult(RESULT_OK, intent);
						    finish();							
						}
					});

				}
				Toast.makeText(NewTweetActivity.this, "Post:" + status, Toast.LENGTH_SHORT).show();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_tweet, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

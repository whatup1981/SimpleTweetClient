package com.codepath.apps.basicTwitterApp;

import java.util.ArrayList;

import org.json.JSONArray;

import com.activeandroid.util.Log;
import com.codepath.apps.basicTwitterApp.models.Tweet;
import com.codepath.apps.basicTwitterApp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.markupartist.android.widget.PullToRefreshListView;
import com.markupartist.android.widget.PullToRefreshListView.OnRefreshListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class TimelineActivity extends Activity {
	TwitterRestClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> adapter;
	private PullToRefreshListView lvTweets;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
		ImageLoader.getInstance().init(config);
		// Set a listener to be invoked when the list should be refreshed.
        
		lvTweets = (PullToRefreshListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		adapter = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(adapter);

		
        lvTweets.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {

            	client.getHomeTimeLine(new JsonHttpResponseHandler(){
        			@Override
        			public void onSuccess(JSONArray json) {
        				tweets.clear();
        				adapter.addAll(Tweet.fromJsonArray(json));
        				adapter.notifyDataSetChanged();
        				lvTweets.onRefreshComplete();
        			}
        			
        			@Override
        			public void onFailure(Throwable e, String str) {
        				Log.d("debug", str.toString());
        				Log.d("debug", e.toString());
        			}
        		});
            }
        });
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// TODO Auto-generated method stub
				Tweet lastTweet = tweets.get(totalItemsCount-2);
				long max_id = lastTweet.getUid();
				client.getHomeTimeLine(new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray json) {
						adapter.addAll(Tweet.fromJsonArray(json));
						adapter.notifyDataSetChanged();
					}

					@Override
					public void onFailure(Throwable e, String s) {
						Log.d("debug", e.toString());
						Log.d("debug", s.toString());
					}
				}, max_id);
				
				
			}
		});
        
		client = TwitterApp.getRestClient();
		populateTimeline();
		
	}
	
	public void populateTimeline() {
		
		client.getHomeTimeLine(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray json) {
				tweets.clear();
				adapter.addAll(Tweet.fromJsonArray(json));
				adapter.notifyDataSetChanged();
			}
			
			@Override
			public void onFailure(Throwable e, String str) {
				Log.d("debug", str.toString());
				Log.d("debug", e.toString());
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {			
			User u = (User)getIntent().getSerializableExtra("login_user");
			 Intent i = new Intent(this, NewTweetActivity.class);
			 i.putExtra("login_user", u);
			 startActivityForResult(i, 0);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == 0 && resultCode == RESULT_OK) {
			populateTimeline();
		
		}
	}
}

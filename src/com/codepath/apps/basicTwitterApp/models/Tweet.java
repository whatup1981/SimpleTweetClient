package com.codepath.apps.basicTwitterApp.models;

import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet {
	private String body;
	private long uid;
	private String createdAt;
	private User user;
	
	public static Tweet fromJson(JSONObject jsonObject) {
		Tweet t = new Tweet();
		//Tue Aug 28 21:16:23 +0000 2012

		try {
			t.body = jsonObject.getString("text");
			t.uid = jsonObject.getLong("id_str");
			t.createdAt = jsonObject.getString("created_at");
			t.user = User.fromJson(jsonObject.getJSONObject("user"));
		    
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} 
		return t;
	}


	public String getBody() {
		return body;
	}

	public long getUid() {
		return uid;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public User getUser() {
		return user;
	}

	public static ArrayList<Tweet> fromJsonArray(JSONArray json) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(json.length());
		for (int i = 0; i <json.length() ; i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = json.getJSONObject(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
			Tweet t = Tweet.fromJson(tweetJson);
			if (t != null) {
				tweets.add(t);
			}
		}
		return tweets;
	}
	
	@Override
	public String toString() {
		return this.body + "/" + getUser().getScreenName() + "/" + this.createdAt;
	}
}

package com.codepath.apps.basicTwitterApp.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8674270350512988039L;
	private String name;
	private long uid;
	private String screenName;
	private String profileImageUrl;
	
	public static User fromJson(JSONObject json) {
		User u = new User();
		try {
		u.name = json.getString("name");
		u.uid = json.getLong("id_str");
		u.screenName = json.getString("screen_name");
		u.profileImageUrl = json.getString("profile_image_url");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return u;
	}

	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

}

package com.codepath.apps.basicTwitterApp;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.codepath.apps.basicTwitterApp.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.text.format.DateUtils;
import android.text.method.DateTimeKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

	public TweetArrayAdapter(Context context, List<Tweet> ts) {
		super(context, 0, ts);
		// TODO Auto-generated constructor stub
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet t = getItem(position);
		View v;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			v = inflater.inflate(R.layout.tweet_item, parent, false);
		} else {
			v = convertView;
		}
		ImageView ivProfile = (ImageView) v.findViewById(R.id.ivProfileImage);
		TextView tvBody = (TextView) v.findViewById(R.id.tvTweet);
		TextView tvName = (TextView) v.findViewById(R.id.tvScreenName);
		TextView tvCreateTime = (TextView) v.findViewById(R.id.tvCreateAt);
		ivProfile.setImageResource(android.R.color.transparent);
		ImageLoader il = ImageLoader.getInstance();
		il.displayImage(t.getUser().getProfileImageUrl(), ivProfile);
		tvBody.setText(t.getBody());
		tvName.setText(t.getUser().getName());
		SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy");
		sf.setLenient(true);
		long qu = 0;
		try {
			qu = sf.parse(t.getCreatedAt()).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Timestamp ts = new Timestamp(qu);
		String tsTime = (String) DateUtils.getRelativeTimeSpanString(
				ts.getTime()
			);
		tvCreateTime.setText(tsTime);
		tvCreateTime.setTextColor(android.graphics.Color.GRAY);
		return v;
	}

}

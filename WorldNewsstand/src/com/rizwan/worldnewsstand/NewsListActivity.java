package com.rizwan.worldnewsstand;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class NewsListActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feed_list);
		String link = getIntent().getStringExtra("feedslink");
		Log.d("test", link.toString());
	}

}

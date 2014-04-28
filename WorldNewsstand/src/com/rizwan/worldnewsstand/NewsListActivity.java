package com.rizwan.worldnewsstand;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.td.rssreader.parser.RSSFeed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class NewsListActivity extends Activity {
	RSSFeed feeds;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feed_list);
		String link = getIntent().getStringExtra("feedslink");
		Log.d("test", link.toString());
		try {
			feeds = new AsyncLoadXmlFeed().execute(link).get();
			Log.d("Feeds", feeds.toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

package com.rizwan.worldnewsstand;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class NewsList extends Activity {

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newslist);
		Intent intent = getIntent();
		ArrayList<CategoryObject> list = (ArrayList<CategoryObject>) intent
				.getSerializableExtra("newsdata");
		LocalActivityManager mLocalActivityManager = new LocalActivityManager(
				this, false);
		mLocalActivityManager.dispatchCreate(savedInstanceState);
		getActionBar().setTitle(intent.getExtras().getCharSequence("cname"));
		Log.d("newsdata", list.toString());
		TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup(mLocalActivityManager);
		
		for(int i=0;i<list.size();i++)
		{
		TabSpec inboxSpec = tabHost.newTabSpec(list.get(i).name);
		Intent inboxIntent = new Intent(NewsList.this, NewsListActivity.class);
		inboxIntent.putExtra("feedslink", list.get(i).link.toString());
		inboxSpec.setContent(inboxIntent);
		inboxSpec.setIndicator(list.get(i).name);
		tabHost.addTab(inboxSpec);
		}
	}

}

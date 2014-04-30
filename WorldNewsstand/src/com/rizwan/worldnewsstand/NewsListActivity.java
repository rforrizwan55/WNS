package com.rizwan.worldnewsstand;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import com.td.rssreader.parser.RSSFeed;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class NewsListActivity extends Activity {
	RSSFeed feeds;
	ListView feedsListView;
	CustomListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feed_list);
		feedsListView = (ListView) findViewById(R.id.listView);
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
		adapter = new CustomListAdapter(this,feeds);
		feedsListView.setAdapter(adapter);
		feedsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				int pos = arg2;

				Bundle bundle = new Bundle();
				bundle.putSerializable("feed", feeds);
				Intent intent = new Intent(NewsListActivity.this,
						DetailActivity.class);
				intent.putExtras(bundle);
				intent.putExtra("pos", pos);
				startActivity(intent);
				
			}
		});
		
	}
	
	class CustomListAdapter extends BaseAdapter {

		private LayoutInflater layoutInflater;
		RSSFeed feed;
		//public ImageLoader imageLoader;

		public CustomListAdapter(NewsListActivity activity,RSSFeed feeds) {

			layoutInflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//imageLoader = new ImageLoader(activity.getApplicationContext());
			feed = feeds;
		}

		@Override
		public int getCount() {

			// Set the total list item count
			return feed.getItemCount();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			// Inflate the item layout and set the views
			View listItem = convertView;
			int pos = position;
			if (listItem == null) {
				listItem = layoutInflater.inflate(R.layout.feeditem, null);
			}

			// Initialize the views in the layout
			ImageView iv = (ImageView) listItem.findViewById(R.id.thumb);
			TextView tvTitle = (TextView) listItem.findViewById(R.id.title);
			TextView tvDate = (TextView) listItem.findViewById(R.id.date);

			// Set the views in the layout
			//imageLoader.DisplayImage(feed.getItem(pos).getImage(), iv);
			tvTitle.setText(feed.getItem(pos).getTitle());
			tvDate.setText(feed.getItem(pos).getDate());

			return listItem;
		}

	}

}

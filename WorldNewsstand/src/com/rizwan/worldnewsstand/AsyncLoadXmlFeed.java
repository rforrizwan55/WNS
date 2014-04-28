package com.rizwan.worldnewsstand;

import com.td.rssreader.parser.DOMParser;
import com.td.rssreader.parser.RSSFeed;

import android.os.AsyncTask;

public class AsyncLoadXmlFeed extends AsyncTask<String, String, RSSFeed> {
	RSSFeed feed;
	@Override
	protected RSSFeed doInBackground(String... params) {
		// TODO Auto-generated method stub
		DOMParser myParser = new DOMParser();
		feed = myParser.parseXml(params[0]);
		return feed;
	}

}

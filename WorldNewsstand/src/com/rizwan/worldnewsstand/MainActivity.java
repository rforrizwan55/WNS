package com.rizwan.worldnewsstand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;

public class MainActivity extends Activity {
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	JSONArray result = null;
	ArrayList<CategoryObject> intentObject = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		expListView = (ExpandableListView) findViewById(R.id.lvExp);
		//getActionBar().setTitle("text");
		try {
			result = new WebService().execute(
					"http://rizapps-arizwan.rhcloud.com/news/getAll.php",
					"test", "test").get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("resultdata", result.toString());
		prepareListData(result);
		listAdapter = new ExpandableListAdapter(this, listDataHeader,
				listDataChild);
		expListView.setAdapter(listAdapter);

		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				intentObject = new ArrayList<CategoryObject>();
				try {
					intentObject = getCategories(
							listDataChild
									.get(listDataHeader.get(groupPosition))
									.get(childPosition), result);
					Intent intent = new Intent(MainActivity.this,NewsList.class);
					intent.putExtra("newsdata", intentObject);
					Log.d("intentObject", intentObject.toString());
					startActivity(intent);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void prepareListData(JSONArray jarray) {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		// JSONArray array = jarray.getJSONArray("interests");
		for (int i = 0; i < jarray.length(); i++) {
			try {
				if (!listDataHeader.contains(jarray.getJSONObject(i).getString(
						"type")))
					listDataHeader.add(jarray.getJSONObject(i)
							.getString("type"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int j = 0; j < listDataHeader.size(); j++) {
			List<String> childNodes = new ArrayList<String>();
			for (int k = 0; k < jarray.length(); k++) {
				try {
					if (jarray.getJSONObject(k).getString("type")
							.equals(listDataHeader.get(j))) {
						if (!childNodes.contains(jarray.getJSONObject(k)
								.getString("name")))
							childNodes.add(jarray.getJSONObject(k).getString(
									"name"));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			listDataChild.put(listDataHeader.get(j), childNodes);
		}

	}

	private ArrayList<CategoryObject> getCategories(String cName, JSONArray jarray)
			throws JSONException {
		ArrayList<CategoryObject> categories = new ArrayList<CategoryObject>();
		for (int z = 0; z < jarray.length(); z++) {
			if (cName.equals(jarray.getJSONObject(z).getString("name"))) {
				CategoryObject co = new CategoryObject();
				co.link = jarray.getJSONObject(z).getString("link");
				co.name = jarray.getJSONObject(z).getString("category");
				categories.add(co);
			}
		}
		return categories;
	}
	
	

}

package com.rizwan.worldnewsstand;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class WebService extends AsyncTask<String, String, JSONArray> {
	public Activity ma;
	 Boolean resultupdate=true;
	public WebService(Activity act)
	{
		this.ma = act;
	}
	
	@Override
	protected JSONArray doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		if(resultupdate)
		{
			File cacheDir = ma.getCacheDir();

			File[] files = cacheDir.listFiles();

			if (files != null) {
			    for (File file : files)
			       file.delete();
			}
		}
		String result = "";
		String urlString = params[0];
		String methodName = urlString.substring(urlString.lastIndexOf('/') + 1,
				urlString.lastIndexOf('.'));
		String countryName = urlString.substring(
				urlString.lastIndexOf('=') + 1, urlString.length());
		String fileName = methodName + "_" + countryName;
		JSONArray json = null;
		InputStream isr = null;
		File root = new File(ma.getCacheDir(), fileName
				+ ".txt");
		if (!root.exists()) {
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(urlString);
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				isr = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(isr, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				isr.close();
				result = sb.toString();

				json = new JSONArray(result);

				try {
					FileWriter writer = new FileWriter(root);
					writer.append(json.toString());
					writer.flush();
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// JSONObject job = json.getJSONObject(i);

			} catch (Exception e) {
				// TODO: handle exception
				Log.d("Async", e.toString());
			}
		} else {
			StringBuilder readText = new StringBuilder();
			try {
				BufferedReader br = new BufferedReader(new FileReader(root));
				String line;

				while ((line = br.readLine()) != null) {
					readText.append(line);
				}
				json = new JSONArray(readText.toString());
			} catch (IOException e) {
				// You'll need to add proper error handling here
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return json;

	}
}

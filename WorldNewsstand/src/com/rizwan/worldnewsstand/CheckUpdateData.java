package com.rizwan.worldnewsstand;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

public class CheckUpdateData extends AsyncTask<String, Void, Boolean> {
	public Activity ma;
	public CheckUpdateData(Activity act)
	{
		this.ma = act;
	}
	@Override
	protected Boolean doInBackground(String... params) {
		// TODO Auto-generated method stub
		String result = "";
		Boolean returnValue = null;
		JSONArray json = null;
		InputStream isr = null;
		String urlString = params[0];
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(urlString);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			isr = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					isr, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			isr.close();
			result = sb.toString();
			json = new JSONArray(result);
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(ma);
			Editor edit = settings.edit();
			String updteTS=settings.getString("updateTimeStamp", null);
			if(updteTS != null)
			{
				//"2014-05-02 03:13:35"
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date serverdate = dateFormat.parse(json.getJSONObject(0).getString("updatetime"));
				Date sysdate = dateFormat.parse(updteTS);
				if(serverdate.after(sysdate))
				{
					returnValue = true;
				}
				else
				{
					returnValue = false;
				}
			}
			else
			{
			edit.putString("updateTimeStamp", json.getJSONObject(0).getString("updatetime"));
			edit.apply(); 
			returnValue = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("Async", e.toString());
		}
		return returnValue;
	}
}

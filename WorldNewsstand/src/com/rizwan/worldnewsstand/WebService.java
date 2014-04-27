package com.rizwan.worldnewsstand;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class WebService extends AsyncTask<String, String, JSONArray> {

	@Override
	protected JSONArray doInBackground(String... params) {
		// TODO Auto-generated method stub
		String result = "";
		String urlString=params[0];
		JSONArray json = null;
		InputStream isr = null;
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
			//	JSONObject job = json.getJSONObject(i);

		} catch (Exception e) {
			// TODO: handle exception
			Log.d("Async", e.toString());
		}
		return json;

	}
}

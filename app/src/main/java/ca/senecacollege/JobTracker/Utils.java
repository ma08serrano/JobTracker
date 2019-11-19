package ca.senecacollege.JobTracker;

import android.text.TextUtils;
import android.util.Log;
//import com.google.android.gms.plus.PlusShare;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class Utils {
    public static final String LOG_TAG = Utils.class.getSimpleName();

    //Makes request via HTTP using url for string data
    public static List<String> fetchJobData(String requestUrl) {
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(createUrl(requestUrl));
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        return extractFeatureFromJson(jsonResponse);
    }

    //Makes request via HTTP using url for specific job string data
    public static List<String> fetchSpecificJobData(String requestUrl) {
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(createUrl(requestUrl));
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        return extractJobFeatureFromJson(jsonResponse);
    }

    //Creates job Url using string
    private static URL createUrl(String stringUrl) {
        try {
            return new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
            return null;
        }
    }

    //Makes request via HTTP using url and creates connection
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the job JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    //Reads data from input stream
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                output.append(line);
            }
        }
        return output.toString();
    }

    //Gets all json data for specific job, used in View job
    private static List<String> extractJobFeatureFromJson(String jobJSON) {
        JSONException e;
        if (TextUtils.isEmpty(jobJSON)) {
            return null;
        }
        ArrayList jobList = new ArrayList();
        try {
            JSONObject currentJob = new JSONObject(jobJSON);
                String jobTitle = currentJob.getString("title");
                String jobLocation = currentJob.getString("location");
                String jobDescription = currentJob.getString("description");
                String jobFullTime = currentJob.getString("type");
                String jobCreationDate = currentJob.getString("created_at");
                String jobCompany = currentJob.getString("company");
                String jobCompanyURL = currentJob.getString("company_url");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(jobTitle);
                stringBuilder.append("@@");
                stringBuilder.append(jobLocation);
                stringBuilder.append("@@");
                stringBuilder.append(jobDescription);
                stringBuilder.append("@@");
                stringBuilder.append(jobFullTime);
                stringBuilder.append("@@");
                stringBuilder.append(jobCreationDate);
                stringBuilder.append("@@");
                stringBuilder.append(jobCompany);
                stringBuilder.append("@@");
                stringBuilder.append(jobCompanyURL);
                jobList.add(stringBuilder.toString());
            return jobList;
        } catch (JSONException e2) {
            e = e2;
            Log.e(LOG_TAG, "Problem parsing the job JSON results", e);
            return null;
        }
    }

    //Gets some json data for multiple jobs, used in Search
    private static List<String> extractFeatureFromJson(String jobJSON) {
        JSONException e;
        if (TextUtils.isEmpty(jobJSON)) {
            return null;
        }
        ArrayList job = new ArrayList();
        try {
            JSONArray jobArray = new JSONArray(jobJSON);
            int i2 = 0;
            while (i2 < jobArray.length()) {
                JSONObject currentJob = jobArray.getJSONObject(i2);
                String jobURL = currentJob.getString("url");
                String jobLocation = currentJob.getString("location");
                String jobTitle = currentJob.getString("title");
                String jobFullTime = currentJob.getString("company");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(jobURL);
                stringBuilder.append("@@");
                stringBuilder.append(jobTitle);
                stringBuilder.append("@@");
                stringBuilder.append(jobLocation);
                stringBuilder.append("@@");
                stringBuilder.append(jobFullTime);
                job.add(stringBuilder.toString());
                i2++;
            }
            return job;
        } catch (JSONException e2) {
            e = e2;
            Log.e(LOG_TAG, "Problem parsing the job JSON results", e);
            return null;
        }
    }
}
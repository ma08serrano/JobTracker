package ca.senecacollege.JobTracker;

import android.app.Activity;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.StringTokenizer;

/* compiled from: MainActivity */
class CustomListAdapter extends ArrayAdapter<String> {
    Activity context;
    List<String> itemname1;

    public CustomListAdapter(Activity activity, List<String> itemnameA) {
        super(activity, R.layout.joblistlayout, itemnameA);
        this.context = activity;
        this.itemname1 = itemnameA;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.joblistlayout, null, true);
        StringTokenizer tokens = new StringTokenizer((String) this.itemname1.get(position), "@@");

        /* Job ID */
        String jobIdToken = tokens.nextToken();
        TextView idInfo = (TextView) rowView.findViewById(R.id.jobURL);
        idInfo.setText(jobIdToken);

        /* Job Title */
        String titleToken = "Title: ";
        TextView textInfo = (TextView) rowView.findViewById(R.id.textViewTitle);

        SpannableString titleString =  new SpannableString(titleToken);
        titleString.setSpan(new StyleSpan(Typeface.BOLD), 0, titleString.length(), 0);
        textInfo.append(titleString);
        textInfo.append(tokens.nextToken());

        /* Job Location */
        String locationToken = "Location: ";
        TextView locationInfo = (TextView) rowView.findViewById(R.id.textViewLocation);

        SpannableString locationString =  new SpannableString(locationToken);
        locationString.setSpan(new StyleSpan(Typeface.BOLD), 0, locationString.length(), 0);
        locationInfo.append(locationString);
        locationInfo.append(tokens.nextToken());

        /* Job Company */
        String companyToken = "Company: ";
        TextView companyInfo = (TextView) rowView.findViewById(R.id.textViewCompany);

        SpannableString companyString =  new SpannableString(companyToken);
        companyString.setSpan(new StyleSpan(Typeface.BOLD), 0, companyString.length(), 0);
        companyInfo.append(companyString);
        companyInfo.append(tokens.nextToken());

        return rowView;
    }
}

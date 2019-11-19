package ca.senecacollege.JobTracker;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobSearchFragment extends Fragment {

    public JobSearchFragment() {
        // Required empty public constructor
    }

    private String stringURL = "https://jobs.github.com/positions.json?description=";
    private List<String> returnArray;
    private ListView linearLayoutListView;
    private LinearLayout jobContent;
    private LinearLayout errMsgLayout;
    private TextView errMsg;
    private EditText jobSearch;
    private ProgressBar progressBar;

    // Uses Utils class to access json data for job and open ViewJobActivity with job data
    class JobAsyncTask extends AsyncTask<String, Void, List<String>> {

        class openLink implements AdapterView.OnItemClickListener {
            openLink() { }

            // Runs ViewJobActivity with job data using job URL
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String currentJobURL = ((TextView) view.findViewById(R.id.jobURL)).getText().toString();
                StringBuilder URLstring = new StringBuilder();
                URLstring.append(currentJobURL);
                URLstring.append(".json");
                Intent intent = new Intent(getActivity(), ViewJobActivity.class);
                intent.putExtra("jobUrl", URLstring.toString());
                startActivityForResult(intent, 0);
            }
        }

        JobAsyncTask() { }

        // Fetches the job data from search in the background
        protected List<String> doInBackground(String... stringurl) {
            returnArray = Utils.fetchJobData(stringurl[0]);

            return returnArray;
        }

        // Display a progressbar fetching the listview
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar = (ProgressBar) getActivity().findViewById(R.id.search_progress);
            progressBar.setVisibility(View.VISIBLE);

            linearLayoutListView = (ListView) getActivity().findViewById(R.id.searchResultsList);
            linearLayoutListView.setVisibility(View.GONE);

            errMsgLayout = (LinearLayout) getActivity().findViewById(R.id.noDataLayout);
            errMsgLayout.setVisibility(View.GONE);

            errMsg = (TextView) getActivity().findViewById(R.id.noData);
            errMsg.setVisibility(View.GONE);
        }

        // After receiving data, puts data in Listview
        public void onPostExecute(List<String> postExecuteResult) {
            progressBar = (ProgressBar) getActivity().findViewById(R.id.search_progress);
            progressBar.setVisibility(View.GONE);

            CustomListAdapter arrayAdapter = new CustomListAdapter(getActivity(), postExecuteResult);

            if (arrayAdapter.getCount() == 0) {
                linearLayoutListView = (ListView) getActivity().findViewById(R.id.searchResultsList);
                linearLayoutListView.setVisibility(View.GONE);

                errMsgLayout = (LinearLayout) getActivity().findViewById(R.id.noDataLayout);
                errMsgLayout.setVisibility(View.VISIBLE);

                errMsg = (TextView) getActivity().findViewById(R.id.noData);
                errMsg.setVisibility(View.VISIBLE);
            }else {
                errMsgLayout = (LinearLayout) getActivity().findViewById(R.id.noDataLayout);
                errMsgLayout.setVisibility(View.GONE);

                errMsg = (TextView) getActivity().findViewById(R.id.noData);
                errMsg.setVisibility(View.GONE);

                linearLayoutListView = (ListView) getActivity().findViewById(R.id.searchResultsList);
                linearLayoutListView.setVisibility(View.VISIBLE);
                linearLayoutListView.setAdapter(arrayAdapter);
                linearLayoutListView.setOnItemClickListener(new openLink());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Set action bar title to specified string
        ((MainActivity) getActivity()).setActionBarTitle("Job Search");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job_search, container, false);
    }

    // Fills spinner with values, and retrieves search values for searching
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        jobSearch = (getActivity().findViewById(R.id.locationValue));
        jobSearch.setOnEditorActionListener(editorActionListener);

        ArrayAdapter adaptor = ArrayAdapter.createFromResource(getActivity(),
                R.array.fulltime_array, android.R.layout.simple_spinner_item);
        Spinner spinner1 = (Spinner) view.findViewById(R.id.fullTimeSpinner);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adaptor);
    }

    // To search on click and fill Listview
    private TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            EditText title = (getActivity().findViewById(R.id.searchValue));
            Spinner spinner1 = (Spinner) getActivity().findViewById(R.id.fullTimeSpinner);
            EditText location = (getActivity().findViewById(R.id.locationValue));

            String titleValue = title.getText().toString();

            // Will not trigger when no data entered.
            if(!titleValue.isEmpty()) {
                String fullTime = (spinner1.getSelectedItem()).toString();
                if (fullTime.equals("Full Time")) {
                    fullTime = "true";
                } else {
                    fullTime = "false";
                }
                String locationValue = location.getText().toString();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.setLength(0);
                stringBuilder.append(stringURL);
                stringBuilder.append(titleValue);
                stringBuilder.append("&full_time=");
                stringBuilder.append(fullTime);
                stringBuilder.append("&location=");
                stringBuilder.append(locationValue);
                String URL = stringBuilder.toString();
                new JobAsyncTask().execute(new String[]{URL});

                title.onEditorAction(EditorInfo.IME_ACTION_DONE);
                location.onEditorAction(EditorInfo.IME_ACTION_DONE);

                return true;
            }
        }
        return false;
        }
    };
}

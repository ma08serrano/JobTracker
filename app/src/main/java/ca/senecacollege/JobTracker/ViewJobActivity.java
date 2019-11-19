package ca.senecacollege.JobTracker;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import ca.senecacollege.JobTracker.DatabaseHelper.Jobs;
import ca.senecacollege.JobTracker.DatabaseHelper.MyDBHandler;
import ca.senecacollege.JobTracker.DatabaseHelper.User;
import ca.senecacollege.JobTracker.DatabaseHelper.UserJob;

import static android.support.constraint.Constraints.TAG;

public class ViewJobActivity extends AppCompatActivity {

    private List<String> returnArray;
    private ProgressBar progressBar;
    private LinearLayout jobContent;
    private LinearLayout errMsgLayout;
    private TextView errMsg;

    // Loads chosen job into view
    class ChosenJobAsyncTask extends AsyncTask<String, Void, List<String>> {

        ChosenJobAsyncTask() { }

        // Fetches specific job data in background
        protected List<String> doInBackground(String... stringurl) {
            returnArray = Utils.fetchSpecificJobData(stringurl[0]);
            return returnArray;
        }

        // Display a progressbar while loading the activity
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            jobContent = findViewById(R.id.job_content);
            jobContent.setVisibility(View.GONE);

            progressBar = findViewById(R.id.job_progress);
            progressBar.setVisibility(View.VISIBLE);
        }


        // Loads job description onto page after receiving json data
        public void onPostExecute(List<String> postExecuteResult) {
            progressBar = findViewById(R.id.job_progress);
            progressBar.setVisibility(View.GONE);

            jobContent = findViewById(R.id.job_content);
            jobContent.setVisibility(View.VISIBLE);

            if (postExecuteResult == null) {
                jobContent = findViewById(R.id.job_content);
                jobContent.setVisibility(View.GONE);

                errMsgLayout = findViewById(R.id.noDataLayout);
                errMsgLayout.setVisibility(View.VISIBLE);

                errMsg = findViewById(R.id.noData);
                errMsg.setVisibility(View.VISIBLE);
            } else {
                errMsgLayout = findViewById(R.id.noDataLayout);
                errMsgLayout.setVisibility(View.GONE);

                errMsg = findViewById(R.id.noData);
                errMsg.setVisibility(View.GONE);

                StringTokenizer tokens = new StringTokenizer(postExecuteResult.get(0), "@@");

                String titleToken = tokens.nextToken();
                TextView textInfo = (TextView) findViewById(R.id.textViewTitle);
                textInfo.setText(titleToken);

                String locationToken = tokens.nextToken();
                TextView locationInfo = (TextView) findViewById(R.id.textViewLocation);
                locationInfo.setText(locationToken);

                String descriptionToken = tokens.nextToken();
                TextView descriptionInfo = (TextView) findViewById(R.id.textViewDescription);
                descriptionInfo.setText(Html.fromHtml(descriptionToken));

                // Places HTML without change into view
                TextView hiddenDesc = (TextView) findViewById(R.id.textViewHiddenDesc);
                hiddenDesc.setText(descriptionToken);

                String fullTimeToken = tokens.nextToken();
                TextView fullTimeInfo = (TextView) findViewById(R.id.textViewFullTime);
                fullTimeInfo.setText(fullTimeToken);

                String creationDateToken = tokens.nextToken();
                TextView creationDateInfo = (TextView) findViewById(R.id.textViewCreationDate);
                creationDateInfo.setText(creationDateToken);

                String companyToken = tokens.nextToken();
                TextView companyInfo = (TextView) findViewById(R.id.textViewCompany);
                companyInfo.setText(companyToken);

                String companyURLToken = tokens.nextToken();
                TextView companyURLInfo = (TextView) findViewById(R.id.textViewCompanyUrl);
                companyURLInfo.setText(companyURLToken);
            }
        }
    }

    // Allows activity to access database
    private MyDBHandler dbHandler() {
        return new MyDBHandler(getApplicationContext(), null, null, 1);
    }

    // Adds job to job and userJob table
    private void addJob() {
        Log.e(TAG, "--> Start addJob");

        // TODO: need to link below with job json and automate process

        Jobs jobs = new Jobs();

        TextView titleView = (findViewById(R.id.textViewTitle));
        String title = titleView.getText().toString();
        jobs.setTitle(title);

        TextView descView = (findViewById(R.id.textViewHiddenDesc));
        String desc = descView.getText().toString();
        jobs.setDescription(desc);

        TextView orgView = (findViewById(R.id.textViewCompany));
        String organization = orgView.getText().toString();
        jobs.setOrganization(organization);

        TextView orgLocationView = (findViewById(R.id.textViewLocation));
        String org_location = orgLocationView.getText().toString();
        jobs.setOrgLocation(org_location);

        String org_email = null;
        jobs.setOrgEmail(org_email);

        String post_origin = "Github Jobs";
        jobs.setPostOrigin(post_origin);

        TextView postUrlView = (findViewById(R.id.textViewJobURL));
        String post_url = postUrlView.getText().toString();
        jobs.setPostUrl(post_url);

        TextView postDeadlineView = (findViewById(R.id.textViewCreationDate));
        String post_deadline = postDeadlineView.getText().toString();
        jobs.setPostDeadline(post_deadline);

        // Complete dates and notes later
        String applied_date = null;
        jobs.setAppliedDate(applied_date);

        String interview_date = null;
        jobs.setInterviewDate(interview_date);

        String offer_deadline = null;
        jobs.setOfferDeadline(offer_deadline);

        String note = null;
        jobs.setNote(note);

        int org_addr_id = 1;
        jobs.setOrgAddrId(org_addr_id);

        int status_id = 1;
        jobs.setStatusId(status_id);

        Jobs currentJob = dbHandler().findJobByTitle(title);

        if (dbHandler().findJobByTitle(title) == null) {
            // Puts job in database
            dbHandler().addJobHandler(jobs);

            currentJob = dbHandler().findJobByTitle(title);

            User currentUser = User.getInstance();
            UserJob currentUserJob = new UserJob();

            currentUserJob.setUserId(currentUser.getID());
            currentUserJob.set_job_id(currentJob.getJobId());

            Log.e(TAG, "-->New user: " + jobs);

            dbHandler().addUserJobHandler(currentUserJob);

            Toast.makeText(getApplicationContext(), "Job Added Successfully!", Toast.LENGTH_LONG).show();

            // Redirect to job board page
            int timeout = 1200;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    finish();
                    Intent intent = new Intent(ViewJobActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, timeout);
        } else {
            if (dbHandler().findUserJobHandler(currentJob.getJobId()) == null) {

                // Puts job in database
                dbHandler().addJobHandler(jobs);

                User currentUser = User.getInstance();
                UserJob currentUserJob = new UserJob();

                currentUserJob.setUserId(currentUser.getID());
                currentUserJob.set_job_id(currentJob.getJobId());

                Log.e(TAG, "-->New user: " + jobs);

                dbHandler().addUserJobHandler(currentUserJob);

                Toast.makeText(getApplicationContext(), "Job Added Successfully!", Toast.LENGTH_LONG).show();

                // Redirect to login page
                int timeout = 1200;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        finish();
                        Intent intent = new Intent(ViewJobActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, timeout);
            } else {

                Toast.makeText(getApplicationContext(), "Job already within user job board", Toast.LENGTH_LONG).show();
            }
        }
    }

    // Adds job description on activity creation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job);

        // Redirect to the main activity
        findViewById(R.id.cancelJob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(ViewJobActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Set action bar title to specified string
        getSupportActionBar().setTitle("Job Description");

        Intent intent = getIntent();
        String jobUrl = intent.getStringExtra("jobUrl");

        // If job is a job posting
        if (jobUrl != null) {
            TextView textInfo = (TextView) findViewById(R.id.textViewJobURL);
            textInfo.setText(jobUrl);
            new ChosenJobAsyncTask().execute(new String[]{jobUrl});

            Button addJobButton = (Button) findViewById(R.id.addJob);
            addJobButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addJob();
                }
            });
        }

        // If job is not found
        else {
            Toast.makeText(getApplicationContext(), "Error, job not found", Toast.LENGTH_LONG).show();
        }
    }
}

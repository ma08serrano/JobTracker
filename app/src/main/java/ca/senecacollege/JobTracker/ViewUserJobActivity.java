package ca.senecacollege.JobTracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import ca.senecacollege.JobTracker.DatabaseHelper.Jobs;
import ca.senecacollege.JobTracker.DatabaseHelper.MyDBHandler;
import ca.senecacollege.JobTracker.DatabaseHelper.User;
import ca.senecacollege.JobTracker.DatabaseHelper.UserJob;

public class ViewUserJobActivity extends AppCompatActivity {
    // Allows activity to access database
    private MyDBHandler dbHandler() {
        return new MyDBHandler(getApplicationContext(), null, null, 1);
    }

    // Deletes job data in both userJob and Job table
    private void deleteJob(String title) {
        Jobs job = dbHandler().findJobByTitle(title);

        int idToken = job.getJobId();
        dbHandler().deleteJobHandler(idToken);
        dbHandler().deleteUserJobHandler(idToken);

        int timeout = 1500;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                finish();
                Intent intent = new Intent(ViewUserJobActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, timeout);
    }

    // Updates job data in both Job table
    private void updateJob(Jobs job) {
        dbHandler().updateJobHandler(job, job.getJobId());
    }

    // Adds Job description on activity creation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_job);

        ArrayAdapter adaptor = ArrayAdapter.createFromResource(ViewUserJobActivity.this,
                R.array.category_array_job, android.R.layout.simple_spinner_item);
        Spinner spinner1 = (Spinner) ViewUserJobActivity.this.findViewById(R.id.category_array_job);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adaptor);

        // Set action bar title to specified string
        getSupportActionBar().setTitle("Job Description");

        Intent intent = getIntent();

        String jobTitle = intent.getStringExtra("jobTitle");
        final Jobs job = dbHandler().findJobByTitle(jobTitle);

        final String titleToken = job.getTitle();
        TextView titleInfo = (TextView) findViewById(R.id.textViewTitle);
        titleInfo.setText(titleToken);

        String locationToken = job.getOrg_location();
        TextView locationInfo = (TextView) findViewById(R.id.textViewLocation);
        locationInfo.setText(locationToken);

        String descriptionToken = job.getDescription();
        TextView descriptionInfo = (TextView) findViewById(R.id.textViewDescription);
        descriptionInfo.setText(Html.fromHtml(descriptionToken));

        String creationDateToken = job.getPost_deadline();
        TextView creationDateInfo = (TextView) findViewById(R.id.textViewCreationDate);
        creationDateInfo.setText(creationDateToken);

        String companyToken = job.getOrganization();
        TextView companyInfo = (TextView) findViewById(R.id.textViewCompany);
        companyInfo.setText(companyToken);

        String companyURLToken = job.getPost_url();
        TextView companyURLInfo = (TextView) findViewById(R.id.textViewCompanyUrl);
        companyURLInfo.setText(companyURLToken);

        String notesToken = job.getNote();
        final EditText notesInfo = findViewById(R.id.notesView);
        notesInfo.setText(notesToken);

        Button deleteJobButton = (Button) findViewById(R.id.deleteJob);
        deleteJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteJob(titleToken);
            }
        });

        Button cancelJobButton = (Button) findViewById(R.id.cancelJob);
        cancelJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewUserJobActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set update button to update job notes and category
        Button updateJobButton = (Button) findViewById(R.id.updateJob);
        updateJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinner1 = (Spinner) ViewUserJobActivity.this.findViewById(R.id.category_array_job);
                String selectedItem = (spinner1.getSelectedItem()).toString();

                User currentUser = User.getInstance();

                // Hijacking colour db load handler for category data checking purpose
                String category = dbHandler().loadColourHandler();

                UserJob currentUserJob;
                currentUserJob = dbHandler().findUserJobHandler(job.getJobId());

                // Need to update both category id on job and user_job table
                if (selectedItem.equals("Wishlist")) {
                    currentUserJob.setUserId(currentUser.getID());
                    currentUserJob.setCategory_id(1);
                } else if (selectedItem.equals("Applied")) {
                    currentUserJob.setUserId(currentUser.getID());
                    currentUserJob.setCategory_id(2);
                } else if (selectedItem.equals("Phone")) {
                    currentUserJob.setUserId(currentUser.getID());
                    currentUserJob.setCategory_id(3);
                } else if (selectedItem.equals("Offer")) {
                    currentUserJob.setUserId(currentUser.getID());
                    currentUserJob.setCategory_id(4);
                } else if (selectedItem.equals("Rejected")) {
                    currentUserJob.setUserId(currentUser.getID());
                    currentUserJob.setCategory_id(5);
                } else {
                    currentUserJob.setUserId(currentUser.getID());
                    currentUserJob.setCategory_id(0);
                }

                dbHandler().updateUserJobHandler(currentUserJob);
                String post_url = notesInfo.getText().toString();
                job.setPostUrl(post_url);
                updateJob(job);
                Toast.makeText(getApplicationContext(), "Job Updated", Toast.LENGTH_LONG).show();

                // Redirect to job board page
                int timeout = 1200;
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        finish();
                        Intent intent = new Intent(ViewUserJobActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, timeout);

            }
        });
    }
}


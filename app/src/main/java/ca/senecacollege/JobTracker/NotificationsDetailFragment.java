package ca.senecacollege.JobTracker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ca.senecacollege.JobTracker.DatabaseHelper.MyDBHandler;
import ca.senecacollege.JobTracker.DatabaseHelper.Notification;

public class NotificationsDetailFragment extends Fragment {

    private static final String TAG = "NotificationsDetailFragment";

    private MyDBHandler db;

    private EditText mTitle;
    private EditText mNotes;
    private Button mDateButton;
    private Button mTimeButton;
    private Button mSaveButton;
    private Spinner mSpinner;

    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

    private String selectedDate;
    private String selectedTime;

    public NotificationsDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Set action bar title to specified string
        ((MainActivity)getActivity()).setActionBarTitle("Notifications Detail");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTitle = this.getActivity().findViewById(R.id.notification_title_text);
        mNotes = this.getActivity().findViewById(R.id.notification_notes_text);
        mDateButton = this.getActivity().findViewById(R.id.notification_date_button);
        mTimeButton = this.getActivity().findViewById(R.id.notification_time_button);
        mSaveButton = this.getActivity().findViewById(R.id.notification_save_button);
        mSpinner = this.getActivity().findViewById(R.id.notificationSpinner);

        // Date button will display Date Picker Dialog
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // Set the date to the current date when displaying the date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                calendar.set(year, month, day);
                                selectedDate = new SimpleDateFormat("MMMM d, yyyy").format(calendar.getTime());
                                mDateButton.setText(selectedDate);

                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        // Time button will display Time Picker Dialog
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCurrentCalendar = Calendar.getInstance();
                int currentHour = mCurrentCalendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute = mCurrentCalendar.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        String amPM = "";

                        Calendar datetime = Calendar.getInstance();
                        datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        datetime.set(Calendar.MINUTE, minutes);

                        if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                            amPM = "AM";
                        else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                            amPM = "PM";

                        selectedTime = String.format("%02d:%02d", datetime.get(Calendar.HOUR), datetime.get(Calendar.MINUTE)) + " " + amPM;
                        mTimeButton.setText(selectedTime);

                    }
                }, currentHour, currentMinute, DateFormat.is24HourFormat(getActivity()));// 24 hour time based on phone settings
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        // Save button to save notification to database -- incomplete
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notification notification = new Notification();
                notification.setName(mTitle.getText().toString());
                notification.setStartDate(selectedDate);
                notification.setEndDate("N/A");
                notification.setStartTime(selectedTime);
                notification.setEndTime("N/A");
                notification.setAllDay("N/A");
                notification.setNote(mNotes.getText().toString());

                db = new MyDBHandler(getActivity(), null, null, 1);
                //db.addNotificationHandler(notification);
            }
        });
    }
}

package ca.senecacollege.JobTracker;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.senecacollege.JobTracker.DatabaseHelper.MyDBHandler;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {

    private FloatingActionButton fab;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Set action bar title to specified string
        ((MainActivity)getActivity()).setActionBarTitle("Notifications");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fab = this.getActivity().findViewById(R.id.add_notification);

        // Floating action button to navigate to Notifications Detail Fragment
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_screen_area, new NotificationsDetailFragment()).addToBackStack(null).commit();
            }
        });
    }

    // DBHandler function to reach database objects
     private MyDBHandler dbHandler() {
        return new MyDBHandler(getActivity().getApplicationContext(), null, null, 1);
    }

}

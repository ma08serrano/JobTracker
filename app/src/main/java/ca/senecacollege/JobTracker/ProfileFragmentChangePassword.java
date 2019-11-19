package ca.senecacollege.JobTracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import ca.senecacollege.JobTracker.DatabaseHelper.MyDBHandler;
import ca.senecacollege.JobTracker.DatabaseHelper.User;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragmentChangePassword.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ProfileFragmentChangePassword extends Fragment {

    private EditText mOriginalPasswordView;
    private EditText mNewPasswordView;
    private EditText mConfirmPasswordView;
    private MyDBHandler db;
    private Button updatePassword;
    private CheckBox showPassword;

    private OnFragmentInteractionListener mListener;

    public ProfileFragmentChangePassword() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Set action bar title to specified string
        ((MainActivity) getActivity()).setActionBarTitle("Update Password");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_fragment_change_password, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * When confirm button is pressed, that button that will trigger updatepassword function
     * and moves the user profile page if there is no error by user
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showPassword = (CheckBox) view.findViewById(R.id.showPassword);

        showPassword.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mOriginalPasswordView = (EditText) getActivity().findViewById(R.id.enterOldPassword);
               mNewPasswordView = (EditText) getActivity().findViewById(R.id.enterNewPassword);
               mConfirmPasswordView = (EditText) getActivity().findViewById(R.id.confirmNewPassword);

               // Show and hide the password
               if (showPassword.isChecked()) {
                   mOriginalPasswordView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                   mNewPasswordView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                   mConfirmPasswordView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
               }else {
                   mOriginalPasswordView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                   mNewPasswordView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                   mConfirmPasswordView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
               }
           }
       });

        updatePassword = (Button) view.findViewById(R.id.ConfirmChangePass);
        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean updatePassword;
                updatePassword = updatePassword();
                if (updatePassword) {

                    // Create new fragment and transaction
                    Fragment newFragment = new ProfileFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack
                    transaction.replace(R.id.main_screen_area, newFragment);
                    transaction.addToBackStack(null);

                    // Commit the transaction
                    transaction.commit();

                }
            }
        });

        /**
         * If user clicks cancel button, will take user back to profile screen without updating password.
         */
        Button changePasswordFragment = (Button) view.findViewById(R.id.CancelChangePass);
        changePasswordFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create new fragment and transaction
                Fragment newFragment = new ProfileFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.main_screen_area, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });
    }

    /**
     * Tis function takes the user's old password, user's input on new/ confirm password and compares
     * if password is valid and updated properly to db, it returns boolean values true to above confirm button function
     */
    private Boolean updatePassword() {
        User currentUser = User.getInstance();

        mOriginalPasswordView = (EditText) getActivity().findViewById(R.id.enterOldPassword);
        mNewPasswordView = (EditText) getActivity().findViewById(R.id.enterNewPassword);
        mConfirmPasswordView = (EditText) getActivity().findViewById(R.id.confirmNewPassword);

        boolean cancel = false;
        boolean valid = true;
        View focusView = null;
        String originPassword = mOriginalPasswordView.getText().toString();
        String newPassword = mNewPasswordView.getText().toString();
        String confirmPassword = mConfirmPasswordView.getText().toString();

        // Checks if user typed in original password
        if (TextUtils.isEmpty(originPassword)) {
            mOriginalPasswordView.setError(getString(R.string.error_field_required));
            focusView = mOriginalPasswordView;
            cancel = true;
        }
        // Checks if user typed in new password to replace original password
        if (TextUtils.isEmpty(newPassword)) {
            mNewPasswordView.setError(getString(R.string.error_field_required));
            focusView = mNewPasswordView;
            cancel = true;
        }
        // Checks if user typed in confirm password that is same as new password
        if (TextUtils.isEmpty(confirmPassword)) {
            mConfirmPasswordView.setError(getString(R.string.error_field_required));
            focusView = mConfirmPasswordView;
            cancel = true;
        }

        MyDBHandler dbHandler = new MyDBHandler(this.getActivity(), null, null, 1);

        // Fetches back original password from shared preferences. if shared preferences failed to load then trigger db to fetch back
        SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String savedUsername = sharedpreferences.getString("usernameKey", "");

        // Call db if shared preferences password fail to bring back password and update shared preferences
        String originPasswordDb = currentUser.getPassword();
        if (savedUsername != null && !savedUsername.isEmpty()) {
            User user = new User();

            db = new MyDBHandler(this.getActivity(), null, null, 1);
            user = db.loadUserHandler(savedUsername);

            originPasswordDb = user.getPassword();

        } else {
            Log.e(TAG, "ERROR: There is no username saved in Shared Preferences!");
        }

        // Checks if original password from db matches typed in original password
        if (!((originPasswordDb.equals(originPassword)))) {
            cancel = true;
            focusView = mOriginalPasswordView;
            mOriginalPasswordView.setError(getString(R.string.incorrect_orig_password));
            valid = false;
        }


        // Checks if new password and confirm password is same then returns true boolean flag if all good
        // Returns error message to user if new password does not match confirm password
        if (!((newPassword).equals(confirmPassword))) {
            cancel = true;
            focusView = mNewPasswordView;
            mNewPasswordView.setError(getString(R.string.incorrect_new_password));
            focusView = mConfirmPasswordView;
            mConfirmPasswordView.setError(getString(R.string.incorrect_confirm_password));
            valid = false;
        }

        // If valid, then update password in db for that user
        if (valid) {
            currentUser.setPassword(newPassword);
            dbHandler.updateUserHandler(currentUser);

            Toast.makeText(getActivity(), "Password update successful", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

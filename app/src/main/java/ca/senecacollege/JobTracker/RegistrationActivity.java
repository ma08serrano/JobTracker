package ca.senecacollege.JobTracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.senecacollege.JobTracker.DatabaseHelper.MyDBHandler;
import ca.senecacollege.JobTracker.DatabaseHelper.User;

// RegistrationActivity will allow the user to register an account and add them to the database
public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = "RegistrationActivity";

    private MyDBHandler db;

    private EditText firstName;
    private EditText lastName;
    private EditText username;
    private EditText email;
    private EditText pwd;
    private EditText pwdConfirm;

    private Button btnRegister;
    private Button btnCancel;

    // Returns a new messaging object to request an action from another app component
    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, RegistrationActivity.class);
        return intent;
    }

    // When the Registration Activity is opened/started, these actions are executed
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Set action bar title to specified string
        this.getSupportActionBar().setTitle("Registration");

        firstName = findViewById(R.id.register_fname);
        lastName = findViewById(R.id.register_lname);
        username = findViewById(R.id.register_username);
        email = findViewById(R.id.register_email);
        pwd = findViewById(R.id.register_pwd);
        pwdConfirm = findViewById(R.id.register_pwd_confirm);

        btnRegister = findViewById(R.id.btn_register);
        btnCancel = findViewById(R.id.btnCancel);

        // When clicking Register button, these actions execute
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateDataEntered()) {
                    addUserAndRedirect();

                    firstName.setText("");
                    lastName.setText("");
                    username.setText("");
                    email.setText("");
                    pwd.setText("");
                    pwdConfirm.setText("");
                }
            }
        });

        // When clicking Cancel button, these actions execute
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Request to start the Login Activity
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                RegistrationActivity.this.startActivity(intent);

                // Finish the Registration Activity
                finish();
            }
        });
    }

    // Returns TRUE if there is an email and it's in the correct format, otherwise FALSE
    private boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    // Returns TRUE if input text is blank, otherwise FALSE
    private boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    // Returns TRUE if password inputs match, otherwise FALSE
    private boolean isPasswordMatch(EditText password, EditText confirmPassword) {
        return password.getText().toString().equals(confirmPassword.getText().toString());
    }

    // Returns TRUE if the password is valid, otherwise FALSE
    private boolean isValidPassword(EditText password) {
        Matcher matcher;
        //Minimum five characters, at least one letter, one number and one special character
        final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{5,}$";
        matcher = Pattern.compile(PASSWORD_PATTERN).matcher(password.getText().toString());

        return matcher.matches();
    }

    // Validate the data input by the user
    private boolean validateDataEntered() {
        Log.e(TAG, "--> Start validateDataEntered");

        boolean validFname = false;
        boolean validLname = false;
        boolean validUsername = false;
        boolean validUser = false;
        boolean validEmail = false;
        boolean validPwd = false;
        boolean validPwdFormat = false;
        boolean validConfirmPwd = false;
        boolean validPwdMatch = false;

        db = new MyDBHandler(this, null, null, 1);

        // Set an error message if first name is blank
        if (isEmpty(firstName)) {
            firstName.setError("First name is required");
        } else {
            validFname = true;
        }

        // Set an error message if last name is blank
        if (isEmpty(lastName)) {
            lastName.setError("Last name is required");
        } else {
            validLname = true;
        }

        // Set an error message if username is blank
        if (isEmpty(username)) {
            username.setError("Username is required");
        } else {
            validUsername = true;

            // Set an error message if username already exists in database
            if (db.isUser(username.getText().toString())) {
                username.setError("Username already exists!");
            } else {
                validUser = true;
            }
        }

        // Set an error message if email is blank
        if (!isEmail(email) || isEmpty(email)) {
            email.setError("Valid email is required");
        } else {
            validEmail = true;
        }

        // Set an error message if password is blank
        if (isEmpty(pwd)) {
            pwd.setError("Password is required");
        } else {
            validPwd = true;

            // Set an error message if password is invalid
            if (!isValidPassword(pwd)) {
                pwd.setError("Password must have minimum 5 characters, at least 1 letter, 1 number and 1 special character");
            } else {
                validPwdFormat = true;
            }
        }

        // Set an error message if password is blank
        if (isEmpty(pwdConfirm)) {
            pwdConfirm.setError("Confirm password is required");
        } else {
            validConfirmPwd = true;
        }

        // Set an error message if password does not match
        if (!isPasswordMatch(pwd, pwdConfirm)) {
            pwdConfirm.setError("Your passwords must match");
        } else {
            validPwdMatch = true;
        }

        // Returns TRUE if all data is valid
        return (validFname && validLname && validUsername && validUser && validEmail && validPwd && validPwdFormat && validConfirmPwd && validPwdMatch);
    }


    // Add user to database and redirect to login page
    private void addUserAndRedirect() {
        String fnameStr = firstName.getText().toString();
        String lnameStr = lastName.getText().toString();
        String usernameStr = username.getText().toString();
        String emailStr = email.getText().toString();
        String pwdStr = pwdConfirm.getText().toString();

        // Create new user with all the input data
        User user = new User(emailStr, usernameStr, pwdStr, fnameStr, lnameStr);

        // Add the new user to the database
        db = new MyDBHandler(this, null, null, 1);
        db.addUserHandler(user);

        // Display pop up message after user has been added to database
        Toast.makeText(RegistrationActivity.this, "Registered Successfully!", Toast.LENGTH_LONG).show();

        // Visible for 1.2 seconds
        int timeout = 1200;

        // This makes the current activity visible for the amount of "timeout" before redirecting
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                finish();

                // Redirect to login page
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, timeout);

    }
}

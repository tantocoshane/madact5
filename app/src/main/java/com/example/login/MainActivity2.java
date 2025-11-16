package com.example.login;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity2 extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;

    EditText etUsername, etPassword, etConfirmPassword, etFirstName, etLastName, etEmail, etAddress, etContactNumber;
    TextView tvBirthdate;
    RadioGroup rgGender;
    RadioButton rbMale, rbFemale, rbOthers;
    CheckBox cbHobby1, cbHobby2, cbHobby3, cbHobby4, cbHobby5, cbHobby6, cbHobby7, cbHobby8, cbHobby9, cbHobby10;
    Spinner spinnerQuestion1, spinnerQuestion2, spinnerQuestion3;
    Button btnSubmit, btnTakePhoto;
    ImageView ivProfilePhoto;

    String selectedDate = "";
    Bitmap profilePicture = null;
    ArrayList<String> securityQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Initialize views
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etContactNumber = findViewById(R.id.etContactNumber);
        tvBirthdate = findViewById(R.id.tvBirthdate);
        rgGender = findViewById(R.id.rgGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        rbOthers = findViewById(R.id.rbOthers);
        cbHobby1 = findViewById(R.id.cbHobby1);
        cbHobby2 = findViewById(R.id.cbHobby2);
        cbHobby3 = findViewById(R.id.cbHobby3);
        cbHobby4 = findViewById(R.id.cbHobby4);
        cbHobby5 = findViewById(R.id.cbHobby5);
        cbHobby6 = findViewById(R.id.cbHobby6);
        cbHobby7 = findViewById(R.id.cbHobby7);
        cbHobby8 = findViewById(R.id.cbHobby8);
        cbHobby9 = findViewById(R.id.cbHobby9);
        cbHobby10 = findViewById(R.id.cbHobby10);
        spinnerQuestion1 = findViewById(R.id.spinnerQuestion1);
        spinnerQuestion2 = findViewById(R.id.spinnerQuestion2);
        spinnerQuestion3 = findViewById(R.id.spinnerQuestion3);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnTakePhoto = findViewById(R.id.btnTakePhoto);
        ivProfilePhoto = findViewById(R.id.ivProfilePhoto);

        setupSecurityQuestions();

        tvBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        // Submit button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSubmit();
            }
        });
    }

    private void setupSecurityQuestions() {
        securityQuestions = new ArrayList<>();
        securityQuestions.add("Select a security question");
        securityQuestions.add("What is your pet's name?");
        securityQuestions.add("What is your mother's maiden name?");
        securityQuestions.add("What university did you go to?");
        securityQuestions.add("What is your favorite color?");
        securityQuestions.add("What is your favorite movie?");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, securityQuestions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerQuestion1.setAdapter(adapter);
        spinnerQuestion2.setAdapter(adapter);
        spinnerQuestion3.setAdapter(adapter);
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_CODE);
        } else {
            Toast.makeText(this, "Camera not available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data.hasExtra("data")) {
                Bundle extras = data.getExtras();
                profilePicture = (Bitmap) extras.get("data");
                ivProfilePhoto.setImageBitmap(profilePicture);
            }
        }
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    selectedDate = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year1;
                    tvBirthdate.setText(selectedDate);
                }, year, month, day);

        datePickerDialog.show();
    }

    private void validateAndSubmit() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String contactNumber = etContactNumber.getText().toString().trim();

        // Check if all fields are filled including photo
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ||
                firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
                address.isEmpty() || contactNumber.isEmpty() || selectedDate.isEmpty() || profilePicture == null) {
            showAlertDialog("ATTENTION!", "All fields are required");
            return;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            showAlertDialog("ATTENTION!", "Password did not match");
            return;
        }

        // Check if gender is selected
        if (rgGender.getCheckedRadioButtonId() == -1) {
            showAlertDialog("ATTENTION!", "All fields are required");
            return;
        }
        int hobbiesCount = 0;
        if (cbHobby1.isChecked()) hobbiesCount++;
        if (cbHobby2.isChecked()) hobbiesCount++;
        if (cbHobby3.isChecked()) hobbiesCount++;
        if (cbHobby4.isChecked()) hobbiesCount++;
        if (cbHobby5.isChecked()) hobbiesCount++;
        if (cbHobby6.isChecked()) hobbiesCount++;
        if (cbHobby7.isChecked()) hobbiesCount++;
        if (cbHobby8.isChecked()) hobbiesCount++;
        if (cbHobby9.isChecked()) hobbiesCount++;
        if (cbHobby10.isChecked()) hobbiesCount++;

        if (hobbiesCount < 1) {
            showAlertDialog("ATTENTION!", "Please select at least 1 hobby");
            return;
        }

        String q1 = spinnerQuestion1.getSelectedItem().toString();
        String q2 = spinnerQuestion2.getSelectedItem().toString();
        String q3 = spinnerQuestion3.getSelectedItem().toString();

        if (q1.equals("Select a security question") || q2.equals("Select a security question") || q3.equals("Select a security question")) {
            showAlertDialog("ATTENTION!", "Please select a security question");
            return;
        }

        if (q1.equals(q2) || q2.equals(q3) || q1.equals(q3)) {
            showAlertDialog("ATTENTION!", "Please select another question");
            return;
        }

        RadioButton selectedGender = findViewById(rgGender.getCheckedRadioButtonId());
        String gender = selectedGender.getText().toString();

        // Get selected hobbies
        ArrayList<String> selectedHobbies = new ArrayList<>();
        if (cbHobby1.isChecked()) selectedHobbies.add("Reading");
        if (cbHobby2.isChecked()) selectedHobbies.add("Dancing");
        if (cbHobby3.isChecked()) selectedHobbies.add("Singing");
        if (cbHobby4.isChecked()) selectedHobbies.add("Drawing");
        if (cbHobby5.isChecked()) selectedHobbies.add("Cooking");
        if (cbHobby6.isChecked()) selectedHobbies.add("Writing");
        if (cbHobby7.isChecked()) selectedHobbies.add("Hiking");
        if (cbHobby8.isChecked()) selectedHobbies.add("Photography");
        if (cbHobby9.isChecked()) selectedHobbies.add("Crafting");
        if (cbHobby10.isChecked()) selectedHobbies.add("Gardening");

        String hobbies = String.join(", ", selectedHobbies);

        // Show account details
        showAccountDetails(username, password, firstName, lastName, email, selectedDate, gender, address, contactNumber, hobbies, q1, q2, q3);
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    private void showAccountDetails(String username, String password, String firstName, String lastName,
                                    String email, String birthdate, String gender, String address,
                                    String contactNumber, String hobbies, String q1, String q2, String q3) {
        String details = "USERNAME: " + username + "\n" +
                "PASSWORD: " + password + "\n" +
                "NAME: " + firstName + " " + lastName + "\n" +
                "E-MAIL: " + email + "\n" +
                "DATE OF BIRTH: " + birthdate + "\n" +
                "GENDER: " + gender + "\n" +
                "ADDRESS: " + address + "\n" +
                "CONTACT NUMBER: " + contactNumber + "\n" +
                "HOBBIES: " + hobbies + "\n" +
                "SECURITY QUESTIONS:\n" +
                q1 + "\n" + q2 + "\n" + q3;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Account Details");
        builder.setMessage(details);
        builder.setPositiveButton("OK", (dialog, which) -> {
            // Show success toast
            Toast.makeText(MainActivity2.this, "Registration was successful", Toast.LENGTH_SHORT).show();

            // Go back to login activity and pass the registration data
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            intent.putExtra("registeredUsername", username);
            intent.putExtra("registeredPassword", password);
            intent.putExtra("firstName", firstName);
            intent.putExtra("lastName", lastName);
            intent.putExtra("userPhoto", profilePicture);
            startActivity(intent);
            finish();
        });
        builder.show();
    }
}
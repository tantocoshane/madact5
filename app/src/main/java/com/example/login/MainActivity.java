package com.example.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Store registration data
    private String registeredUsername = "";
    private String registeredPassword = "";
    private String registeredFirstName = "";
    private String registeredLastName = "";
    private Bitmap registeredPhoto = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get registration data if coming from registration
        Intent receivedIntent = getIntent();
        if (receivedIntent.hasExtra("registeredUsername")) {
            registeredUsername = receivedIntent.getStringExtra("registeredUsername");
            registeredPassword = receivedIntent.getStringExtra("registeredPassword");
            registeredFirstName = receivedIntent.getStringExtra("firstName");
            registeredLastName = receivedIntent.getStringExtra("lastName");
            registeredPhoto = (Bitmap) receivedIntent.getParcelableExtra("userPhoto");
        }

        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(60, 100, 60, 100);
        mainLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView simpleLoginAppText = new TextView(this);
        simpleLoginAppText.setText("SIMPLE LOGIN APP");
        simpleLoginAppText.setTextSize(30);
        simpleLoginAppText.setGravity(Gravity.CENTER);
        simpleLoginAppText.setTypeface(null, Typeface.BOLD);
        simpleLoginAppText.setPadding(0, 0, 0, 60);
        mainLayout.addView(simpleLoginAppText);

        TextView usernameLabel = new TextView(this);
        usernameLabel.setText("USERNAME");
        mainLayout.addView(usernameLabel);

        EditText usernameInput = new EditText(this);
        usernameInput.setHint("Enter username");
        mainLayout.addView(usernameInput);

        TextView passwordLabel = new TextView(this);
        passwordLabel.setText("PASSWORD");
        passwordLabel.setPadding(0, 30, 0, 0);
        mainLayout.addView(passwordLabel);

        EditText passwordInput = new EditText(this);
        passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordInput.setHint("Enter password");
        mainLayout.addView(passwordInput);

        TextView justSpaceLabel = new TextView(this);
        justSpaceLabel.setText(" ");
        mainLayout.addView(justSpaceLabel);

        Button loginButton = new Button(this);
        loginButton.setText("LOGIN");
        loginButton.setTextColor(Color.WHITE);
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(Color.BLACK);
        shape.setCornerRadius(50f);
        loginButton.setBackground(shape);
        mainLayout.addView(loginButton);


        TextView registerText = new TextView(this);
        registerText.setText("Not yet registered? Click here");
        registerText.setPadding(0, 50, 0, 0);
        registerText.setGravity(Gravity.CENTER);
        mainLayout.addView(registerText);

        setContentView(mainLayout);

        // LOGIN BUTTON FUNCTIONALITY
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                // Check for empty fields
                if (username.isEmpty() || password.isEmpty()) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Fill the fields")
                            .setMessage("Please fill up both Username and Password.")
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                }

                // Check against hardcoded credentials OR registered user
                if ((username.equals("Jeff") && password.equals("12345")) ||
                        (username.equals("Joan") && password.equals("567890")) ||
                        (username.equals("Dani") && password.equals("ASDFGH"))) {

                    Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();

                    // For hardcoded users, go to welcome without photo
                    Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                    intent.putExtra("firstName", username);
                    intent.putExtra("lastName", "");
                    startActivity(intent);

                } else if (!registeredUsername.isEmpty() && username.equals(registeredUsername) && password.equals(registeredPassword)) {

                    Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();

                    // Go to Welcome Activity with registered user data
                    Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                    intent.putExtra("firstName", registeredFirstName);
                    intent.putExtra("lastName", registeredLastName);
                    intent.putExtra("userPhoto", registeredPhoto);
                    startActivity(intent);

                } else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Login Failed")
                            .setMessage("Username or Password is incorrect.")
                            .setPositiveButton("OK", null)
                            .show();
                }
            }
        });

        // REGISTER LINK FUNCTIONALITY
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Register")
                        .setMessage("You will be redirected to the registration page.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // When OK is clicked, go to MainActivity2
                                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
    }
}
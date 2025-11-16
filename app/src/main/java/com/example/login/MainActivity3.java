package com.example.login;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity {

    TextView tvWelcomeName;
    ImageView ivUserPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        tvWelcomeName = findViewById(R.id.tvWelcomeName);
        ivUserPhoto = findViewById(R.id.ivUserPhoto);

        // Get data from intent
        String firstName = getIntent().getStringExtra("firstName");
        String lastName = getIntent().getStringExtra("lastName");
        Bitmap userPhoto = (Bitmap) getIntent().getParcelableExtra("userPhoto");

        // Display welcome message
        if (lastName != null && !lastName.isEmpty()) {
            tvWelcomeName.setText(firstName + " " + lastName + "!");
        } else {
            tvWelcomeName.setText(firstName + "!");
        }

        // Display photo if available
        if (userPhoto != null) {
            ivUserPhoto.setImageBitmap(userPhoto);
        }
    }
}
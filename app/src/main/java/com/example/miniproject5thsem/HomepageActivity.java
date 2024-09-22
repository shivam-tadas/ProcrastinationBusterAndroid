package com.example.miniproject5thsem; // Change to your package name

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HomepageActivity extends AppCompatActivity {

    private TextView greetingText;
    private ImageButton menuButton;
    private ImageButton profileButton;
    private ImageView avatarImage;
    private TextView levelText;
    private TextView expText;
    private ImageView timeSpentChart;
    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private ImageButton imageButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage); // Change to your layout file name

        // Initialize views
        greetingText = findViewById(R.id.greetingText);
        menuButton = findViewById(R.id.menuButton);
        profileButton = findViewById(R.id.profileButton);
        avatarImage = findViewById(R.id.avatarImage);
        levelText = findViewById(R.id.levelText);
        expText = findViewById(R.id.expText);
        timeSpentChart = findViewById(R.id.timeSpentChart);
        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);

        // Set up button click listeners
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle profile button click
            }
        });

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle TODO button click
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Home button click
            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Reward button click
            }
        });
    }
}

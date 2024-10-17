package com.example.miniproject5thsem; // Change to your package name

import android.content.Intent;
import android.content.SharedPreferences;
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
    private ImageButton imageButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        // Initialize views
        greetingText = findViewById(R.id.greetingText);
        menuButton = findViewById(R.id.menuButton);
        profileButton = findViewById(R.id.profileButton);
        avatarImage = findViewById(R.id.avatarImage);
        levelText = findViewById(R.id.levelText);
        expText = findViewById(R.id.expText);
        timeSpentChart = findViewById(R.id.timeSpentChart);
        imageButton1 = findViewById(R.id.imageButton1);
        imageButton3 = findViewById(R.id.imageButton3);

        String username = sharedPreferences.getString("username", "user");
        greetingText.setText(username + "'s Dashboard     ");
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
                Intent intent = new Intent(HomepageActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, TodosActivity.class);
                startActivity(intent);
            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, RewardsActivity.class);
                startActivity(intent);
            }
        });
    }
}

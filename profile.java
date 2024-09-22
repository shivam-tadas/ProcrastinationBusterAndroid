package com.example.procrastination_buster;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class profile extends AppCompatActivity {

    private TextView greetingText;
    private ImageView avatarImage;
    private TextView levelText;
    private TextView expText;
    private TextView totalTasksText;
    private TextView currentPendingTasksText;
    private TextView dateJoinedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile); // Change to your layout file name

        // Initialize views
        greetingText = findViewById(R.id.greetingText);
        avatarImage = findViewById(R.id.avatarImage);
        levelText = findViewById(R.id.levelText);
        expText = findViewById(R.id.expText);
        totalTasksText = findViewById(R.id.Text);
        currentPendingTasksText = findViewById(R.id.Text1);
        dateJoinedText = findViewById(R.id.Text2);

        // Example data (you can replace this with actual user data)
        greetingText.setText("Profile");
        levelText.setText("Level 2");
        expText.setText("EXP 421");
        totalTasksText.setText("Total tasks completed: 15");
        currentPendingTasksText.setText("Current pending tasks: 3");
        dateJoinedText.setText("Date joined: January 1, 2023");

        // Optional: Set up the avatar image
        // avatarImage.setImageResource(R.drawable.user); // Uncomment if you have a specific image
    }
}

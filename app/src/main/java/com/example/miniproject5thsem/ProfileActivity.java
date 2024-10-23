package com.example.miniproject5thsem;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView levelText;
    private TextView expText;
    private TextView totalTasksText;
    private TextView currentPendingTasksText;
    private TextView dateJoinedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        levelText = findViewById(R.id.levelText);
        expText = findViewById(R.id.expText);
        totalTasksText = findViewById(R.id.Text);
        currentPendingTasksText = findViewById(R.id.Text1);
        dateJoinedText = findViewById(R.id.Text2);

        levelText.setText("Level 2");
        expText.setText("EXP 421");
        totalTasksText.setText("Total tasks completed: 15");
        currentPendingTasksText.setText("Current pending tasks: 3");
        dateJoinedText.setText("Date joined: January 1, 2023");

    }
}

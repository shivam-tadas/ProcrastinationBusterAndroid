package com.example.miniproject5thsem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TodosActivity extends AppCompatActivity {

    private ImageButton menuButton;
    private ImageButton profileButton;
    private ImageButton homeButton;
    private ImageButton rewardsButton;
    private ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos);

        // Initialize views
        menuButton = findViewById(R.id.menuButton);
        profileButton = findViewById(R.id.profileButton);
        homeButton = findViewById(R.id.imageButton2);
        rewardsButton = findViewById(R.id.imageButton3);
        addButton = findViewById(R.id.imageButton4);

        // Set click listeners
        menuButton.setOnClickListener(v -> openMenu());
        profileButton.setOnClickListener(v -> openProfile());
        homeButton.setOnClickListener(v -> openHome());
        rewardsButton.setOnClickListener(v -> openRewards());
        addButton.setOnClickListener(v -> addNewTask());
    }

    private void openMenu() {
        Intent intent = new Intent(TodosActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    private void openProfile() {
        Intent intent = new Intent(TodosActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    private void openHome() {
        finish();
    }

    private void openRewards() {
        Intent intent = new Intent(TodosActivity.this, RewardsActivity.class);
        startActivity(intent);
    }

    private void addNewTask() {
        // TODO: Implement new task addition logic
    }
}

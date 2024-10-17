package com.example.miniproject5thsem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class RewardsActivity extends AppCompatActivity {

    private ImageButton menuButton;
    private ImageView avatarImage;
    private ImageButton todoButton;
    private ImageButton homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        menuButton = findViewById(R.id.menuButton);
        avatarImage = findViewById(R.id.avatarImage);
        todoButton = findViewById(R.id.imageButton1);
        homeButton = findViewById(R.id.imageButton2);

        menuButton.setOnClickListener(v -> openMenu());
        todoButton.setOnClickListener(v -> openTodoList());
        homeButton.setOnClickListener(v -> openHome());
    }

    private void openMenu() {
        Intent intent = new Intent(RewardsActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    private void openProfile() {
        Intent intent = new Intent(RewardsActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    private void openTodoList() {
        Intent intent = new Intent(RewardsActivity.this, TodosActivity.class);
        startActivity(intent);
    }

    private void openHome() {
        finish();
    }
}

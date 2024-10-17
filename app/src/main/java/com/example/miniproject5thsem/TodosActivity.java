package com.example.miniproject5thsem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

public class TodosActivity extends AppCompatActivity {
    private ImageButton menuButton;
    private ImageButton homeButton;
    private ImageButton rewardsButton;
    private ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos);

        menuButton = findViewById(R.id.menuButton);
        homeButton = findViewById(R.id.imageButton2);
        rewardsButton = findViewById(R.id.imageButton3);
        addButton = findViewById(R.id.imageButton4);

        menuButton.setOnClickListener(v -> openMenu());
        homeButton.setOnClickListener(v -> openHome());
        rewardsButton.setOnClickListener(v -> openRewards());
        addButton.setOnClickListener(v -> addNewTask());
    }

    private void openMenu() {
        Intent intent = new Intent(TodosActivity.this, MenuActivity.class);
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
        // Inflate the dialog with the custom layout
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_task, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setTitle("Add New Task");

        EditText taskNameEditText = dialogView.findViewById(R.id.taskNameEditText);
        Spinner prioritySpinner = dialogView.findViewById(R.id.prioritySpinner);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String taskName = taskNameEditText.getText().toString().trim();
            String priority = prioritySpinner.getSelectedItem().toString();

            if (!taskName.isEmpty()) {
                // TODO: Save task to database and update UI
                Toast.makeText(TodosActivity.this, "Task added: " + taskName + " (" + priority + ")", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(TodosActivity.this, "Please enter a task name", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

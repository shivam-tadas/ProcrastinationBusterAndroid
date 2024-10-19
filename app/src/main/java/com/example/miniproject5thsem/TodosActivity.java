package com.example.miniproject5thsem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

        fetchTasks();
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
                // Sending data to server
                String url = "http://10.0.2.2/add_task.php";
                RequestQueue requestQueue = Volley.newRequestQueue(TodosActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Handle response from server
                                Toast.makeText(TodosActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle error
                                Toast.makeText(TodosActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        String username = sharedPreferences.getString("username", "default_user"); // Update as needed

                        params.put("username", username);
                        params.put("task_name", taskName);
                        params.put("priority", priority);
                        return params;
                    }
                };

                requestQueue.add(stringRequest);
            } else {
                Toast.makeText(TodosActivity.this, "Please enter a task name", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void fetchTasks() {
        String url = "http://10.0.2.2/fetch_tasks.php";
        RequestQueue requestQueue = Volley.newRequestQueue(TodosActivity.this);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "default_user");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parse response to JSONArray
                            JSONArray tasksArray = new JSONArray(response);
                            displayTasks(tasksArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(TodosActivity.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TodosActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username); // Sending username
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void displayTasks(JSONArray tasks) {
        LinearLayout tasksLayout = findViewById(R.id.tasksLayout);
        tasksLayout.removeAllViews();

        try {
            for (int i = 0; i < tasks.length(); i++) {
                JSONObject task = tasks.getJSONObject(i);
                if (task.has("error")) {
                    Toast.makeText(TodosActivity.this, task.getString("error"), Toast.LENGTH_SHORT).show();
                    return;
                }

                String taskName = task.getString("task_name");
                String priority = task.getString("priority");
                String creationTime = task.getString("creation_time"); // Get creation time

                LinearLayout taskLayout = new LinearLayout(this);
                taskLayout.setOrientation(LinearLayout.VERTICAL);

                TextView taskText = new TextView(this);
                taskText.setText(taskName + " (" + priority + ")");
                taskText.setTextSize(20);
                taskLayout.addView(taskText);

                TextView creationTimeText = new TextView(this);
                creationTimeText.setText("Created on: " + creationTime);
                creationTimeText.setTextSize(16); // Smaller font for the creation time
                creationTimeText.setTextColor(getResources().getColor(R.color.gray)); // Gray color for timestamp
                taskLayout.addView(creationTimeText);

                tasksLayout.addView(taskLayout);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(TodosActivity.this, "Error displaying tasks", Toast.LENGTH_SHORT).show();
        }
    }
}

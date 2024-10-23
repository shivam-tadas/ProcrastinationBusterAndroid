package com.example.miniproject5thsem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
                response -> {
                    try {
                        JSONArray tasksArray = new JSONArray(response);
                        displayTasks(tasksArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(TodosActivity.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(TodosActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "default_user");

                // Ensure this value is not null or empty
                if (username != null && !username.isEmpty()) {
                    params.put("username", username);
                } else {
                    Log.e("FetchTasksError", "Username is missing in SharedPreferences");
                }

                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

    private void displayTasks(JSONArray tasks) {
        LinearLayout tasksLayout = findViewById(R.id.tasksLayout);
        tasksLayout.removeAllViews();
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "user");

        try {
            for (int i = 0; i < tasks.length(); i++) {
                JSONObject task = tasks.getJSONObject(i);

                String taskId = task.getString("task_id");  // Assuming task_id is returned from server
                String taskName = task.getString("task_name");
                String priority = task.getString("priority");
                String creationTime = task.getString("creation_time");

                LinearLayout taskLayout = new LinearLayout(this);
                taskLayout.setOrientation(LinearLayout.VERTICAL);

                TextView taskText = new TextView(this);
                taskText.setText(taskName + " (" + priority + ")");
                taskText.setTextSize(20);
                taskLayout.addView(taskText);

                TextView creationTimeText = new TextView(this);
                creationTimeText.setText("Created on: " + creationTime);
                creationTimeText.setTextSize(16);
                creationTimeText.setTextColor(getResources().getColor(R.color.gray));
                taskLayout.addView(creationTimeText);

                Button doneButton = new Button(this);
                doneButton.setText("Finished");
                doneButton.setOnClickListener(v -> {
                    markTaskAsDone(taskId, taskLayout);     // Pass task ID and the layout to remove
                    addRewardPoints(username, priority);    // Add exp for task completion
                });
                taskLayout.addView(doneButton);
                tasksLayout.addView(taskLayout);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(TodosActivity.this, "Error displaying tasks", Toast.LENGTH_SHORT).show();
        }
    }

    private void markTaskAsDone(String taskId, LinearLayout taskLayout) {
        String url = "http://10.0.2.2/mark_task_done.php";
        RequestQueue requestQueue = Volley.newRequestQueue(TodosActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // Handle server response
                    Toast.makeText(TodosActivity.this, "Task marked as done and deleted!", Toast.LENGTH_SHORT).show();
                    // Remove task from UI
                    LinearLayout tasksLayout = findViewById(R.id.tasksLayout);
                    tasksLayout.removeView(taskLayout);  // Remove the task's layout after done
                },
                error -> Toast.makeText(TodosActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("task_id", taskId);  // Only send task ID to mark as done
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void addRewardPoints(String username, String priority) {
        String url = "http://10.0.2.2/add_reward_points.php";
        RequestQueue requestQueue = Volley.newRequestQueue(TodosActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(TodosActivity.this, "Reward points updated!", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Toast.makeText(TodosActivity.this, "Error updating reward points: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("priority", priority);  // Send task priority to backend
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}

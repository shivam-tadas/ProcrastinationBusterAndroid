package com.example.miniproject5thsem;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        fetchTasks();
    }

    private void fetchTasks() {
        String url = "http://10.0.2.2/fetch_completed_tasks.php";
        RequestQueue requestQueue = Volley.newRequestQueue(HistoryActivity.this);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "default_user");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Log.d("ServerResponse", response);  // Log the server response for debugging

                    try {
                        // Try to parse response as JSON array
                        JSONArray tasksArray = new JSONArray(response);

                        displayTasks(tasksArray);
                    } catch (JSONException e) {
                        // Handle JSON parsing error
                        e.printStackTrace();
                        Toast.makeText(HistoryActivity.this, "Error parsing response: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    Log.e("FetchError", error.toString());  // Log error details for debugging
                    Toast.makeText(HistoryActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
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

        if (tasks.length() == 0) {
            TextView noTasksText = new TextView(this);
            noTasksText.setText("No completed tasks available.");
            noTasksText.setTextSize(18);
            tasksLayout.addView(noTasksText);
            return;
        }

        try {
            for (int i = 0; i < tasks.length(); i++) {
                JSONObject task = tasks.getJSONObject(i);

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

                tasksLayout.addView(taskLayout);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(HistoryActivity.this, "Error displaying tasks", Toast.LENGTH_SHORT).show();
        }
    }
}

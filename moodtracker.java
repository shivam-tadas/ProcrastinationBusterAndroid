package com.example.procrastination_buster;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class moodtracker extends AppCompatActivity {

    private TextView greetingText;
    private TextView moodText;
    private TextView noteText;
    private EditText editTextBox;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moodtracker ); // Change to your layout file name

        // Initialize views
        greetingText = findViewById(R.id.greetingText);
        moodText = findViewById(R.id.mood);
        noteText = findViewById(R.id.note);
        editTextBox = findViewById(R.id.editTextBox);
        saveButton = findViewById(R.id.button1);

        // Set up button click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = editTextBox.getText().toString().trim();
                if (!note.isEmpty()) {
                    // Handle the save action, e.g., save to a database or shared preferences
                    Toast.makeText(moodtracker.this, "Note saved: " + note, Toast.LENGTH_SHORT).show();
                    editTextBox.setText(""); // Clear the input
                } else {
                    Toast.makeText(moodtracker .this, "Please enter a note.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}


package com.example.miniproject5thsem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class ResetCodeActivity extends AppCompatActivity {

    private EditText resetCodeEditText;
    private EditText newPasswordEditText;
    private EditText confirmPasswordEditText;
    private Button resetButton;
    private TextView resendCodeTextView;
    private TextView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_code);

        // Initialize UI elements
        resetCodeEditText = findViewById(R.id.resetCodeEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        resetButton = findViewById(R.id.resetButton);
        resendCodeTextView = findViewById(R.id.resendCodeTextView);
        backButton = findViewById(R.id.backButton);

        // Back button functionality: go back to the previous screen
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Reset button functionality: Validate inputs and reset the password
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resetCode = resetCodeEditText.getText().toString().trim();
                String newPassword = newPasswordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                if (resetCode.isEmpty()) {
                    Toast.makeText(ResetCodeActivity.this, "Please enter the reset code", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(ResetCodeActivity.this, "Please fill in all password fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(ResetCodeActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Here you would handle the logic to verify the reset code and update the password
                // For example, you could call an API to reset the password

                Toast.makeText(ResetCodeActivity.this, "Password reset successfully", Toast.LENGTH_SHORT).show();
                finish();   // Go back to the login screen
            }
        });

        // Resend code functionality
        resendCodeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic to resend the reset code to the user's email
                Toast.makeText(ResetCodeActivity.this, "Reset code resent", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

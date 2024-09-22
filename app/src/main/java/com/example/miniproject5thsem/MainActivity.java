package com.example.miniproject5thsem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.app.AlertDialog;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private Button signupButton;
    private TextView helpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);
        helpText = findViewById(R.id.helpText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add logic for the login button here (e.g., go to login screen)
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add logic for the signup button here (e.g., go to signup screen)
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        helpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHelpDialog();
            }
        });
    }

    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Help");
        builder.setMessage("If you already have an account, you can log in using your credentials. If you're new here, sign up to create a new account.");
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

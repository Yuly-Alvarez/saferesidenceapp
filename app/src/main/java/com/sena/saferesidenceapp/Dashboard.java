package com.sena.saferesidenceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {

    Button createUserFormButton, listUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        createUserFormButton = findViewById(R.id.create_user_form);
        listUserButton = findViewById(R.id.list_user);

        // Ir al formulario de registro
        createUserFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, RegisterPage.class);
                startActivity(intent);
            }
        });

        // Ir a la lista de usuarios
        listUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, UserListActivity.class);
                startActivity(intent);
            }
        });
    }
}
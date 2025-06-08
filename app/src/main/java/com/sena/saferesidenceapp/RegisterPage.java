package com.sena.saferesidenceapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPage extends AppCompatActivity {

    TextInputEditText editTextName, editTextDni, editTextPhone, editTextEmail, editTextApartment, editTextUsername, editTextPassword;
    Spinner userTypeSpinner;
    Button createUser, returnHome;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference("usuarios");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_page);

        // Enlazar vistas
        editTextName = findViewById(R.id.name);
        editTextDni = findViewById(R.id.dni);
        editTextPhone = findViewById(R.id.phone);
        editTextEmail = findViewById(R.id.email);
        editTextApartment = findViewById(R.id.apartment);
        editTextUsername = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        userTypeSpinner = findViewById(R.id.user_type_spinner);
        createUser = findViewById(R.id.create_user);
        returnHome = findViewById(R.id.return_home);

        // Configurar spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(adapter);

        returnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPage.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });

        // Acción botón crear usuario
        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener datos
                String name = String.valueOf(editTextName.getText()).trim();
                String dni = String.valueOf(editTextDni.getText()).trim();
                String phone = String.valueOf(editTextPhone.getText()).trim();
                String email = String.valueOf(editTextEmail.getText()).trim();
                String apartment = String.valueOf(editTextApartment.getText()).trim();
                String username = String.valueOf(editTextUsername.getText()).trim();
                String password = String.valueOf(editTextPassword.getText()).trim();
                String role = String.valueOf(userTypeSpinner.getSelectedItem());

                // Validaciones básicas
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(dni) || TextUtils.isEmpty(phone)
                        || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username)) {
                    Toast.makeText(RegisterPage.this, "Por favor completa todos los campos obligatorios", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Conversión de cédula y teléfono a datos numéricos
                long dniLong, phoneLong;
                try {
                    dniLong = Long.parseLong(dni);
                    phoneLong = Long.parseLong(phone);
                } catch (NumberFormatException e) {
                    Toast.makeText(RegisterPage.this, "DNI y teléfono deben ser numéricos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Registrar en Firebase Authentication
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    if (user != null) {
                                        String uid = user.getUid();

                                        // Crear objeto Usuario (con valores long corregidos)
                                        Usuario nuevoUsuario = new Usuario(name, dniLong, phoneLong, email, apartment, username, role);

                                        // Guardar en Realtime Database bajo UID
                                        usersRef.child(uid).setValue(nuevoUsuario)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> dbTask) {
                                                        if (dbTask.isSuccessful()) {
                                                            Toast.makeText(RegisterPage.this, "Usuario registrado y guardado correctamente", Toast.LENGTH_SHORT).show();
                                                            limpiarFormulario();
                                                        } else {
                                                            Toast.makeText(RegisterPage.this, "Error al guardar datos: " + dbTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(RegisterPage.this, "Error: UID no disponible", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(RegisterPage.this, "Error al registrar usuario: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

    private void limpiarFormulario() {
        editTextName.setText("");
        editTextDni.setText("");
        editTextPhone.setText("");
        editTextEmail.setText("");
        editTextApartment.setText("");
        editTextUsername.setText("");
        editTextPassword.setText("");
        userTypeSpinner.setSelection(0);
    }
}

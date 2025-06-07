package com.sena.saferesidenceapp;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPage extends AppCompatActivity {

    TextInputEditText editTextName, editTextDni, editTextPhone, editTextEmail, editTextApartment, editTextUsername, editTextPassword;
    Spinner userTypeSpinner;
    Button createUser;

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

        // Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(adapter);

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener datos del formulario
                String name = String.valueOf(editTextName.getText());
                String dni = String.valueOf(editTextDni.getText());
                String phone = String.valueOf(editTextPhone.getText());
                String email = String.valueOf(editTextEmail.getText());
                String apartment = String.valueOf(editTextApartment.getText());
                String username = String.valueOf(editTextUsername.getText());
                String password = String.valueOf(editTextPassword.getText());
                String role = String.valueOf(userTypeSpinner.getSelectedItem());

                // Validaciones
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(dni) || TextUtils.isEmpty(phone)
                        || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username)) {
                    Toast.makeText(RegisterPage.this, "Por favor completa todos los campos obligatorios", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Registro en Firebase Authentication
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Obtener el UID del usuario recién creado
                                    String uid = firebaseAuth.getCurrentUser().getUid();

                                    // Crear objeto Usuario
                                    Usuario nuevoUsuario = new Usuario(name, dni, phone, email, apartment, username, role);

                                    // Guardar en Realtime Database
                                    usersRef.child(uid).setValue(nuevoUsuario)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> dbTask) {
                                                    if (dbTask.isSuccessful()) {
                                                        Toast.makeText(RegisterPage.this, "Usuario registrado y guardado correctamente", Toast.LENGTH_SHORT).show();
                                                        limpiarFormulario();
                                                    } else {
                                                        Toast.makeText(RegisterPage.this, "Error al guardar los datos del usuario", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                } else {
                                    Toast.makeText(RegisterPage.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
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

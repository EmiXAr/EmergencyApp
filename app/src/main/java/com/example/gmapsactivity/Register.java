package com.example.gmapsactivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        EditText inputUsuario = findViewById(R.id.correoText);
        EditText inputContraseña = findViewById(R.id.contraseñaText);
        MaterialButton RegistrarButton = findViewById(R.id.ResgistrarButton);
        EditText inputNombre = findViewById(R.id.Nombre);
        EditText inputPhone = findViewById(R.id.telefonoText);

        firebaseAuth = FirebaseAuth.getInstance();

        RegistrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password,apellido, nombre, telefono;
                email=String.valueOf(inputUsuario.getText());
                password=String.valueOf(inputContraseña.getText());
                nombre=String.valueOf(inputNombre.getText());
                telefono=String.valueOf(inputPhone.getText());

                if (TextUtils.isEmpty(telefono)){
                    Toast.makeText(Register.this, "Enter Phone Number ", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(nombre)){
                    Toast.makeText(Register.this, "Enter Name ", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(Register.this, "Enter Email Address", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }


                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener
                        (new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(Register.this, "Cuenta creada", Toast.LENGTH_SHORT).show();

                                    FirebaseUser user = firebaseAuth.getCurrentUser();

                                    String correo = inputUsuario.getText().toString();
                                    String nombre = inputNombre.getText().toString();
                                    String telefono = inputPhone.getText().toString();
                                } else {
                                    Toast.makeText(Register.this, "Erro al crear la cuenta", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                Intent login = new Intent(Register.this, Login.class);
                startActivity(login);
                finish();

            }
        });



    }
}
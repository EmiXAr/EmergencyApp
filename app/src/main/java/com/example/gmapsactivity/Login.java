package com.example.gmapsactivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        EditText inputUser = findViewById(R.id.correoText);
        EditText inputPassword = findViewById(R.id.passwordText);
        MaterialButton sesionButton = findViewById(R.id.iniciarSesionButton);
        MaterialButton registrarButton = findViewById(R.id.ResgistrarButton);

        sesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuarioIngresado = inputUser.getText().toString().trim();
                String claveIngresada = inputPassword.getText().toString().trim();

                if (usuarioIngresado.isEmpty() || claveIngresada.isEmpty()) {
                    Toast.makeText(Login.this, "Ingrese sus credenciales", Toast.LENGTH_SHORT).show();
                } else {
                    iniciarSesion(usuarioIngresado, claveIngresada);
                }
                inputUser.setText("");
                inputPassword.setText("");
            }
        });

        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });


    }
    public void iniciarSesion(String Correo, String Clave) {
        mAuth.signInWithEmailAndPassword(Correo, Clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    handleClick(MainActivity.class);
                } else {
                    Toast.makeText(Login.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleClick(Class<?> activityClass) {
        if (isNetworkAvailable()) {
            Intent intent = new Intent(Login.this, activityClass);
            startActivity(intent);
        } else {
            showNoConnectionToast();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showNoConnectionToast() {
        Toast.makeText(this, "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
    }

}
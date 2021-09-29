package com.example.projectv1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    private lateinit var Auth: FirebaseAuth

//COMENTARIO
    //
    //
    //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getSupportActionBar()?.hide()
        setContentView(R.layout.activity_main)

        Auth = FirebaseAuth.getInstance()
        val textViewRegistrarse = findViewById<TextView>(R.id.txt_registrarse)


        textViewRegistrarse.setOnClickListener { abrirActividad(RegistrarUsuario::class.java) }

        val btnIngresarUsuario = findViewById<Button>(R.id.btn_ingresar_usuario)
        btnIngresarUsuario.setOnClickListener {
            userLogin()
        }


    }


    fun abrirActividad(clase: Class<*>){
        val intentExplicito = Intent(
            this,
            clase
        )
        startActivity(intentExplicito)
    }
    fun userLogin(){
        val editTextUsuario = findViewById<EditText>(R.id.edit_correo_usuario)
        val editTextClave =findViewById<EditText>(R.id.edit_contraseña)
        val barraProgreso:ProgressBar = findViewById<ProgressBar>(R.id.pgb_login)
        val email = editTextUsuario.text.toString().trim()
        val clave = editTextClave.text.toString().trim()
        if(email.isEmpty()){
            editTextUsuario.setError("El correo es necesario ")
            editTextUsuario.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextUsuario.setError("Por favor ingrese un correo valido")
            editTextUsuario.requestFocus()
            return

        }
        if(clave.isEmpty()){
            editTextClave.setError("La clave es necesaria")
            editTextClave.requestFocus()
            return
        }
        if(clave.length<8){
            editTextClave.setError("La clave debe contener al menos 8 caracteres!")
            editTextClave.requestFocus()
            return
        }
        barraProgreso.setVisibility(View.VISIBLE)
        Auth.signInWithEmailAndPassword(email,clave)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    //abrir actividad Inicio
                    abrirActividad(Inicio::class.java)
                }else{
                    Toast.makeText(this, "Correo o clave incorrectos!",Toast.LENGTH_LONG ).show()

                }
            }
    }
}


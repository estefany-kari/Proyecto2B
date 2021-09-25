package com.example.projectv1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern


class RegistrarUsuario : AppCompatActivity() {
    private lateinit var Auth: FirebaseAuth
    private lateinit var txtFecha: EditText
    private val PASSWORD_PATTERN: Pattern = Pattern.compile(
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\$@\$!%*?&])[A-Za-z\\d\$@\$!%*?&]{8,15}"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getSupportActionBar()?.hide()
        setContentView(R.layout.activity_registrar_usuario)

        Auth = FirebaseAuth.getInstance();
        val botonRegistrarUsuario = findViewById<Button>(R.id.btn_ingresarUsuario)
        botonRegistrarUsuario.setOnClickListener {
            registrarUsuario()

        }
        val botonCancelarRegistro = findViewById<Button>(R.id.btn_cancelar_registroUsuario)
        botonCancelarRegistro.setOnClickListener {

            abrirActividad(MainActivity::class.java)
        }
        txtFecha = findViewById(R.id.txtFecha)
        txtFecha.setOnClickListener{ShowDatePickerDialog()}

    }

    fun registrarUsuario() {
        var etNombre = findViewById<EditText>(R.id.etNombre)
        val emailUsuario = findViewById<EditText>(R.id.edi_correo)
        val editTextTextPassword = findViewById<EditText>(R.id.editTextTextPassword)
        val editTextTextPassword2 = findViewById<EditText>(R.id.editTextTextPassword2)
        val progressBar: ProgressBar = findViewById<ProgressBar>(R.id.pgb_registrar)
        val Nombre = etNombre.getText().toString().trim()
        val correoUsuario = emailUsuario.getText().toString().trim()
        val Fecha = txtFecha.getText().toString().trim()
        val Password = editTextTextPassword.getText().toString().trim()
        val Password2 = editTextTextPassword2.getText().toString().trim()
        if (Nombre.isEmpty()) {
            etNombre.setError("El nombre es necesario")
        } else if (Nombre.length < 8) {
            etNombre.setError("El nonbre debe contener almenos 8 caracteres")
        } else if (Nombre.length > 30) {
            etNombre.setError("El nonbre no debe contener mas de 30 caracteres")
        } else if (correoUsuario.isEmpty()) {
            emailUsuario.setError("El correo es necesario")
        } else if (Fecha.isEmpty()) {
            txtFecha.setError("La fecha es necesaria")
        } else if (Password.isEmpty()) {
            editTextTextPassword.setError("La clave es necesaria")
        } else if (!PASSWORD_PATTERN.matcher(Password).matches()) {
            editTextTextPassword.setError("Contraseña muy debil")
        } else if (Password.length < 8) {
            editTextTextPassword.setError("La contraseña debe tener almenos 8 caracteres")
        } else if (Password.length > 16) {
            editTextTextPassword.setError("La contraseña no debe tener mas de 30 caracteres")
        }
        if (Password2.isEmpty()) {
            editTextTextPassword2.setError("Es necesario confirmar la clave")
        }
        if (Password2 != Password) {
            editTextTextPassword2.setError("Las contraseñas no coinsiden")
        }

            progressBar.setVisibility(View.VISIBLE)
            Auth.createUserWithEmailAndPassword(correoUsuario, Password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful()) {
                        val user = User(Nombre, correoUsuario, Fecha, Password, Password2);
                        FirebaseAuth.getInstance().currentUser?.let {
                            FirebaseDatabase.getInstance().getReference("Users")
                                .child(it.uid)
                                .setValue(user).addOnCompleteListener {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(
                                            this,
                                            "USUARIO REGISTRADO EXITOSAMENTE",
                                            Toast.LENGTH_LONG
                                        ).show();
                                        progressBar.setVisibility(View.VISIBLE);
                                        //DIRIGIR A LA VENTANA LOGIN
                                        abrirActividad(MainActivity::class.java)

                                    } else {
                                        Toast.makeText(
                                            this,
                                            "FALLO EL REGISTRO, INTENTALO NUEVAMENTE",
                                            Toast.LENGTH_LONG
                                        ).show();
                                        progressBar.setVisibility(View.GONE);
                                    }

        progressBar.setVisibility(View.VISIBLE)
        Auth.createUserWithEmailAndPassword(correoUsuario, Password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful()) {
                    val user = User(Nombre, correoUsuario, Fecha, Password, Password2);
                    FirebaseAuth.getInstance().currentUser?.let {
                        FirebaseDatabase.getInstance().getReference("Users")
                            .child(it.uid)
                            .setValue(user).addOnCompleteListener {

                                if (task.isSuccessful()) {
                                    Toast.makeText(
                                        this,
                                        "USUARIO REGISTRADO EXITOSAMENTE",
                                        Toast.LENGTH_LONG
                                    ).show();
                                    progressBar.setVisibility(View.VISIBLE);
                                    //DIRIGIR A LA VENTANA LOGIN
                                    abrirActividad(MainActivity::class.java)

                                } else {
                                    Toast.makeText(
                                        this,
                                        "FALLO EL REGISTRO, INTENTALO NUEVAMENTE",
                                        Toast.LENGTH_LONG
                                    ).show();
                                    progressBar.setVisibility(View.GONE);

                                }
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "FALLO EL REGISTRO, INTENTALO NUEVAMENTE",
                            Toast.LENGTH_LONG
                        ).show();
                        progressBar.setVisibility(View.GONE);
                    }


                }

                } else {
                    Toast.makeText(
                        this,
                        "FALLO EL REGISTRO, INTENTALO NUEVAMENTE",
                        Toast.LENGTH_LONG
                    ).show();
                    progressBar.setVisibility(View.GONE);
                }

            }


    }
    fun ShowDatePickerDialog() {
        val datePicker = DatePickerFragment{ day, month, year -> OnDateSelected(day, month, year)}
        datePicker.show(supportFragmentManager, "datePcker")
    }
    fun OnDateSelected(day:Int, month:Int, year:Int){
        txtFecha.setText("${day}/${month}/${year}")
    }
    fun abrirActividad(clase: Class<*>){
        val intentExplicito = Intent(
            this,
            clase
        )
        startActivity(intentExplicito)
    }
    private fun validarPassword(): Boolean {
        val Password = findViewById<EditText>(R.id.editTextTextPassword)

        val passwordInput: String = Password.getText().toString().trim()
        return  if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            Password.setError("Contraseña muy debil")
            false
        } else {
            Password.setError(null)
            true
        }
    }
}

        private fun validarPassword(): Boolean {
            val Password = findViewById<EditText>(R.id.editTextTextPassword)

            val passwordInput: String = Password.getText().toString().trim()
            return  if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
                Password.setError("Contraseña muy debil")
                false
            } else {
                Password.setError(null)
                true
            }
        }
    }




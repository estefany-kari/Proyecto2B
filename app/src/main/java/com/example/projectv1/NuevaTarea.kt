package com.example.projectv1

import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.ktx.Firebase
import java.util.*
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore


class NuevaTarea : AppCompatActivity() {

    private lateinit var fechaTarea: EditText
    private lateinit var prioridad: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar()?.hide();
        setContentView(R.layout.activity_nueva_tarea)

        val botonHoraTarea = findViewById<EditText>(R.id.btn_HoraRecordatorio)
        fechaTarea = findViewById(R.id.btn_fechaTarea)
        fechaTarea.setOnClickListener{ShowDatePickerDialog()}

        prioridad=findViewById(R.id.btn_PrioridadTarea)
        prioridad .setOnClickListener{withItems()}

        val NombreTarea=findViewById<EditText>(R.id.txtNombreTarea)
        botonHoraTarea. setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                botonHoraTarea.setText("${hour} : ${minute}")
            }
            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        val Categoria= findViewById<TextView>(R.id.txtCat)
        Categoria .setOnClickListener {
            abrirActividad(categoriasTarea::class.java)
        }

        val Colaboradores= findViewById<TextView>(R.id.et_colaboradores)
        Colaboradores .setOnClickListener {
            abrirActividad(ColaboradoresTarea::class.java)
        }

        val btnConfirmar = findViewById<Button>(R.id.btn_confirmarTarea)
        btnConfirmar .setOnClickListener {
            val categoriaTarea = categoriasTarea.CategoriaTarea
            println("$categoriaTarea")
            val nombreT = NombreTarea.text.toString()
            println("$nombreT")
            val fechaT = fechaTarea.text.toString()
            println("$fechaT")
            val horaTarea = botonHoraTarea.text.toString()
            println("$horaTarea")
            val PrioridadTarea = prioridad.text.toString()
            println("$PrioridadTarea")
            val getColaboradores = ColaboradoresTarea.Colaboradores
            println("$getColaboradores")

            val nuevaTarea = hashMapOf<String,Any>(
                "Nombre" to nombreT,
                "Categoria" to categoriaTarea,
                "Fecha" to fechaT,
                "Hora" to horaTarea,
                "Prioridad" to PrioridadTarea,
                "Colaboradores" to getColaboradores,
            )


            val db = Firebase.firestore
            val referencia = db.collection("Tarea")
            referencia.add(nuevaTarea)
                .addOnSuccessListener {

                }
                .addOnFailureListener {  }

            abrirActividad(Inicio::class.java) }
        val btnCancelar = findViewById<Button>(R.id.id_cancelarTarea)
        btnCancelar .setOnClickListener {

            abrirActividad(Inicio::class.java) }
    }
    fun abrirActividad(clase: Class<*>){
        val intentExplicito = Intent(
            this,
            clase
        )
        startActivity(intentExplicito)
    }
    fun ShowDatePickerDialog() {
        val datePicker = DatePickerFragment{ day, month, year -> OnDateSelected(day, month, year)}
        datePicker.show(supportFragmentManager, "datePcker")
    }

    fun OnDateSelected(day:Int, month:Int, year:Int){
        fechaTarea.setText("${day}/${month}/${year}")
    }

    fun withItems() {

        val items = arrayOf("Alta", "Normal", "Baja")
        val builder = AlertDialog.Builder(this)
        val Title = SpannableString("                     PRIORIDAD ")

        with(builder)
        {
            //setTitle("PRIORIDAD")
            Title.setSpan(
                ForegroundColorSpan(Color.parseColor("#00D9C4")),
                14,
                Title.length,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE

            )
            Title.setSpan(
                StyleSpan(Typeface.BOLD),
                8,
                Title.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            setTitle(Title)
            setItems(items) { dialog, which ->
                Toast.makeText(applicationContext, items[which] + " is clicked", Toast.LENGTH_SHORT).show()
                prioridad.setText("${items[which]}")
            }

            setNegativeButton("CANCELAR", null)
            show()
        }
    }




}
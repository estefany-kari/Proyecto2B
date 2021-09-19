package com.example.projectv1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Switch
import com.example.projectv1.R.menu.menu_inicio_tareas

class  Inicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        val btnHabitos = findViewById<Button>(R.id.btn_Hb)
        val btnTarea = findViewById<Button>(R.id.btn_TA)

        btnHabitos.setOnClickListener { abrirActividad(categoriasHabitos::class.java) }
        btnTarea.setOnClickListener { abrirActividad(NuevaTarea::class.java) }


    }

    fun abrirActividad(clase: Class<*>){
        val intentExplicito = Intent(
            this,
            clase
        )
        startActivity(intentExplicito)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.menu_inicio_tareas, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
                R.id.id_calendario->{
                   /* val intent : Intent = Intent(this, DatePickerFragment::class.java)
                    startActivity(intent)*/
                    return true
                }
            R.id.id_mishabitos_menu->{
                val intent : Intent = Intent(this, Habitos::class.java)
                startActivity(intent)
            }
            R.id.id_menu_mis_tareas->{
                val intent : Intent = Intent(this, Inicio::class.java)
                startActivity(intent)
                return true
            }
            R.id.id_menu_sanduche->{
                val intent : Intent = Intent(this, MenuSanduche::class.java)
                startActivity(intent)
                return true
            }

            }
        return super.onOptionsItemSelected(item)
    }


}
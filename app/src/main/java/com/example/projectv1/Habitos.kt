package com.example.projectv1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_habitos.*

class Habitos : AppCompatActivity() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var habitoArrayList : ArrayList<habito>
    private lateinit var myAdapter : habitoAdapter
    private lateinit var db : FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habitos)

        btn_crear_habito.setOnClickListener {
            val intent: Intent = Intent(this, categoriasHabitos::class.java)
            startActivity(intent)
        }

        recyclerView= findViewById(R.id.rv_habitos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        habitoArrayList= arrayListOf()

        myAdapter= habitoAdapter(habitoArrayList)
        recyclerView.adapter= myAdapter

        EventChangeListener()

    }
    private fun EventChangeListener() {
        db= FirebaseFirestore.getInstance()
        db.collection("Habito").
        addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(
                value: QuerySnapshot?,
                error: FirebaseFirestoreException?) {

                if(error != null){
                    Log.e("firestore",error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!){
                    if(dc.type == DocumentChange.Type.ADDED){
                        val habito =   dc.document.toObject(habito::class.java)
                        habito.nombreHabito= dc.document.getString("Nombre Habito")
                        habito.CategoriaHabito = dc.document.getString("Categoria Habito")
                        habito.hora = dc.document.getString("Hora Habito")
                        habitoArrayList.add(habito)
                    }
                }

                myAdapter.notifyDataSetChanged()
            }

        })
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
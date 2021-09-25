package com.example.projectv1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView

class tareaAdapter(private val tareasList: ArrayList<Tarea>):RecyclerView.Adapter<tareaAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): tareaAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.disenio_inicio,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: tareaAdapter.MyViewHolder, position: Int) {
        val tarea: Tarea = tareasList[position]
        holder.nombreTarea.text = tarea.nombreTarea
        holder.categoria.text = tarea.categoria
        holder.hora.text = tarea.hora
      //  holder.icono.text = tarea.imagenTarea
    }

    override fun getItemCount(): Int {
      return tareasList.size
    }

    public class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nombreTarea: TextView = itemView.findViewById(R.id.id_tv_nombre_tarea)
        val categoria: TextView = itemView.findViewById(R.id.id_tv_categoria)
        val hora: TextView = itemView.findViewById(R.id.id_tv_hora)
       // val icono: TextView = itemView.findViewById(R.id.id_icono_tarea)

    }
}
package com.example.projectv1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class habitoAdapter(private val habitoList: ArrayList<habito>): RecyclerView.Adapter<habitoAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): habitoAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.disenio_habitos,parent,false)
        return habitoAdapter.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: habitoAdapter.MyViewHolder, position: Int) {
        val habito: habito = habitoList[position]
        holder.nombreHabito.text= habito.nombreHabito
        holder.categoria.text = habito.CategoriaHabito
        holder.hora.text = habito.hora

        //  holder.icono.text = habito.imagenTarea
    }

    override fun getItemCount(): Int {
        return habitoList.size
    }
    public class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nombreHabito: TextView = itemView.findViewById(R.id.id_tv_nombre_habito)
        val categoria: TextView = itemView.findViewById(R.id.id_tv_categoria_habito)
        val hora: TextView = itemView.findViewById(R.id.id_tv_hora_habito)
        // val icono: TextView = itemView.findViewById(R.id.id_icono_cate)

    }

}
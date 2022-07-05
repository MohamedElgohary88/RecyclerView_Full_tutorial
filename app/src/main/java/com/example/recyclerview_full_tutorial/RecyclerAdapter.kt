package com.example.recyclerview_full_tutorial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter( countryList :MutableList<String> ) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

   private var countries : MutableList<String> = countryList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = countries[position]
    }

    override fun getItemCount() = countries.size


    inner class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
         var itemImage : ImageView
         var itemTitle : TextView

        init {
            itemImage = itemView.findViewById(R.id.itemImage)
            itemTitle = itemView.findViewById(R.id.itemTitle)

            itemView.setOnClickListener {
                val position : Int = adapterPosition
                Toast.makeText(itemView.context,"You Clicked on ${countries[position]} " , Toast.LENGTH_LONG).show()

            }

        }
    }


}
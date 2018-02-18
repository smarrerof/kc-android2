package com.sergiomarrero.madridshops.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sergiomarrero.madridshops.R
import com.sergiomarrero.madridshops.domain.model.Model
import com.sergiomarrero.madridshops.domain.model.Models
import com.sergiomarrero.madridshops.util.TranslationManager
import com.squareup.picasso.Picasso


class ModelRecyclerViewAdapter(val models: Models?): RecyclerView.Adapter<ModelRecyclerViewAdapter.ModelViewHolder>() {
    var onClickListener: View.OnClickListener? = null

    // ViewHolder
    inner class ModelViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val image = itemView.findViewById<ImageView>(R.id.imageLogo)
        val name = itemView.findViewById<TextView>(R.id.textName)
        val openingHours = itemView.findViewById<TextView>(R.id.textOpeningHours)

        fun bindModel(model: Model) {
            Picasso
                .with(itemView.context)
                .load(model.logoImage)
                .into(image)

            name.text = model.name
            openingHours.text = TranslationManager.getOpeningHours(model)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ModelViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_model, parent, false)
        view.setOnClickListener(onClickListener)
        return ModelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ModelViewHolder?, position: Int) {
        if (models != null) {
            holder?.bindModel(models.get(position))
        }
    }

    override fun getItemCount() = models?.count() ?: 0
}
package com.sergiomarrero.madridshops.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sergiomarrero.madridshops.R
import com.sergiomarrero.madridshops.domain.model.Shop
import com.sergiomarrero.madridshops.domain.model.Shops
import com.sergiomarrero.madridshops.util.TranslationManager
import com.squareup.picasso.Picasso


class ShopRecyclerViewAdapter(val shops: Shops?): RecyclerView.Adapter<ShopRecyclerViewAdapter.ShopViewHolder>() {
    var onClickListener: View.OnClickListener? = null

    // ViewHolder
    inner class ShopViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val image = itemView.findViewById<ImageView>(R.id.imageLogo)
        val name = itemView.findViewById<TextView>(R.id.textName)
        val openingHours = itemView.findViewById<TextView>(R.id.textOpeningHours)

        fun bindShop(shop: Shop) {
            Picasso
                .with(itemView.context)
                .load(shop.logoImage)
                .into(image)

            name.text = shop.name
            openingHours.text = TranslationManager.getOpeningHours(shop)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ShopViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_shop, parent, false)
        view.setOnClickListener(onClickListener)
        return ShopViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopViewHolder?, position: Int) {
        if (shops != null) {
            holder?.bindShop(shops.get(position))
        }
    }

    override fun getItemCount() = shops?.count() ?: 0
}
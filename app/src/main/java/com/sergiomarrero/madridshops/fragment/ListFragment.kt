package com.sergiomarrero.madridshops.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sergiomarrero.madridshops.R
import com.sergiomarrero.madridshops.adapter.ShopRecyclerViewAdapter
import com.sergiomarrero.madridshops.domain.model.Shop
import com.sergiomarrero.madridshops.domain.model.Shops
import kotlinx.android.synthetic.main.fragment_list.*


/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {

    lateinit var root: View
    private var onListItemSelectedListener: OnListItemSelectedListener? = null
    private var shops: Shops? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        root = inflater!!.inflate(R.layout.fragment_list, container, false)
        return root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnListItemSelectedListener) {
            onListItemSelectedListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        onListItemSelectedListener = null
    }


    fun setShops(shops: Shops) {
        this.shops = shops

        // Configure recyclerView
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        setAdapter()
    }


    private fun setAdapter() {
        // Set the adapter
        val adapter = ShopRecyclerViewAdapter(shops)
        recyclerView.adapter = adapter

        // Handle click
        adapter.onClickListener = View.OnClickListener { v: View? ->
            // Get selected shop
            val position = recyclerView.getChildAdapterPosition(v)
            val shop = shops?.get(position)

            if(shop != null) {
                onListItemSelectedListener?.onListItemSelected(shop)
            }
        }
    }


    interface OnListItemSelectedListener {
        fun onListItemSelected(shop: Shop)
    }
}// Required empty public constructor

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
import com.sergiomarrero.madridshops.adapter.ModelRecyclerViewAdapter
import com.sergiomarrero.madridshops.domain.model.Models
import com.sergiomarrero.madridshops.domain.model.Model
import kotlinx.android.synthetic.main.fragment_list.*


/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {

    lateinit var root: View
    private var onListItemSelectedListener: OnListItemSelectedListener? = null
    private var models: Models? = null

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


    fun setModels(models: Models) {
        this.models = models

        // Configure recyclerView
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        setAdapter()
    }


    private fun setAdapter() {
        // Set the adapter
        val adapter = ModelRecyclerViewAdapter(models)
        recyclerView.adapter = adapter

        // Handle click
        adapter.onClickListener = View.OnClickListener { v: View? ->
            // Get selected shop
            val position = recyclerView.getChildAdapterPosition(v)
            val model = models?.get(position)

            if(model != null) {
                onListItemSelectedListener?.onListItemSelected(model)
            }
        }
    }


    interface OnListItemSelectedListener {
        fun onListItemSelected(model: Model)
    }
}// Required empty public constructor

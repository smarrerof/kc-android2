package com.sergiomarrero.madridshops.repository.network

import android.content.Context
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sergiomarrero.madridshops.common.util.Constants
import com.sergiomarrero.madridshops.repository.ErrorCompletion
import com.sergiomarrero.madridshops.repository.SuccessCompletion
import java.lang.ref.WeakReference


class GetJsonManagerVolleyImpl(context: Context): GetJsonManager {

    var weakContext: WeakReference<Context> = WeakReference(context)
    var requestQueue: RequestQueue? = null

    override fun execute(url: String, success: SuccessCompletion<String>, error: ErrorCompletion) {
        // Create request (success, failure)
        val request = StringRequest(url, Response.Listener {
            success.successCompletion(it)
        }, Response.ErrorListener {
            Log.e(Constants.TAG, it.localizedMessage)
            error.errorCompletion(it.localizedMessage)
        })

        // Add request to queue
        requestQueue().add(request)
    }

    fun requestQueue(): RequestQueue {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(weakContext.get())
        }
        return requestQueue!!
    }
}
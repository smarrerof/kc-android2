package com.sergiomarrero.madridshops.repository.network

import com.sergiomarrero.madridshops.repository.ErrorCompletion
import com.sergiomarrero.madridshops.repository.SuccessCompletion


interface GetJsonManager {
    fun execute(url: String, successCompletion: SuccessCompletion<String>, error: ErrorCompletion)
}

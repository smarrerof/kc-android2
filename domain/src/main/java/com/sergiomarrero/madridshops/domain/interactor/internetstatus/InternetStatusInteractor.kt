package com.sergiomarrero.madridshops.domain.interactor.internetstatus

import android.content.Context
import com.sergiomarrero.madridshops.domain.interactor.EmptyClosure
import com.sergiomarrero.madridshops.domain.interactor.ErrorClosure


interface InternetStatusInteractor {
    fun execute(context: Context, success: EmptyClosure, error: ErrorClosure)
}
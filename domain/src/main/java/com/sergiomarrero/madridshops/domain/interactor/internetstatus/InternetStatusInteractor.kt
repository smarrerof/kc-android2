package com.sergiomarrero.madridshops.domain.interactor.internetstatus

import android.content.Context
import com.sergiomarrero.madridshops.domain.interactor.CodeClosure
import com.sergiomarrero.madridshops.domain.interactor.ErrorClosure


interface InternetStatusInteractor {
    fun execute(context: Context, success: CodeClosure, error: ErrorClosure)
}
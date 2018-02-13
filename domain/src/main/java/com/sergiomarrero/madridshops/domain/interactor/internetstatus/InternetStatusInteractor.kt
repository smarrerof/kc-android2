package com.sergiomarrero.madridshops.domain.interactor.internetstatus

import com.sergiomarrero.madridshops.domain.interactor.CodeClosure
import com.sergiomarrero.madridshops.domain.interactor.ErrorClosure


interface InternetStatusInteractor {
    fun execute(success: CodeClosure, error: ErrorClosure)
}
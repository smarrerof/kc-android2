package com.sergiomarrero.madridshops.domain.interactor.deleteallshops

import com.sergiomarrero.madridshops.domain.interactor.CodeClosure
import com.sergiomarrero.madridshops.domain.interactor.ErrorClosure


interface DeleteAllShops {
    fun execute(success: CodeClosure, error: ErrorClosure)
}

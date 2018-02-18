package com.sergiomarrero.madridshops.domain.interactor

import com.sergiomarrero.madridshops.domain.model.Models

typealias EmptyClosure = () -> Unit
typealias SuccessClosure = (models: Models) -> Unit
typealias ErrorClosure = (msg: String) -> Unit
typealias Variant = Any
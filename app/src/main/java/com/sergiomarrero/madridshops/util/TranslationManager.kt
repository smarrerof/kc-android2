package com.sergiomarrero.madridshops.util

import com.sergiomarrero.madridshops.domain.model.IModel
import java.util.*


class TranslationManager() {
    companion object {
        fun getDescription(model: IModel): String {
            return when (getLanguage()) {
                "es" -> model.descriptionEs
                else -> model.descriptionEn
            }
        }

        fun getOpeningHours(model: IModel): String {
            return when (getLanguage()) {
                "es" -> model.openingHoursEs
                else -> model.openingHoursEn
            }
        }

        private fun getLanguage(): String {
            return Locale.getDefault().language
        }
    }
}

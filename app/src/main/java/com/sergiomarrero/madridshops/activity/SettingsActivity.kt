package com.sergiomarrero.madridshops.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.TextView
import android.widget.Toast
import com.sergiomarrero.madridshops.R
import com.sergiomarrero.madridshops.domain.interactor.ErrorCompletion
import com.sergiomarrero.madridshops.domain.interactor.SuccessCompletion
import com.sergiomarrero.madridshops.domain.interactor.countallmodels.CountAllModelsInteractor
import com.sergiomarrero.madridshops.domain.interactor.countallmodels.CountAllModelsInteractorImpl
import com.sergiomarrero.madridshops.domain.interactor.deleteallmodels.DeleteAllModels
import com.sergiomarrero.madridshops.domain.interactor.deleteallmodels.DeleteAllModelsImpl
import com.sergiomarrero.madridshops.domain.model.Type
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        count(Type.SHOP, textShopRecords)
        count(Type.ANTIVITY, textActivityRecords)

        buttonDeleteAllRecords.setOnClickListener {
            val dialog = AlertDialog.Builder(this).create()
            dialog.setTitle(getString(R.string.confirm_cache_delete_title))
            dialog.setMessage(getString(R.string.confirm_cache_delete_message))

            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.no), {
                dialogInterface, i ->
            })

            dialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes), { _, _ ->

                val deleteAllModelsInteractor: DeleteAllModels = DeleteAllModelsImpl(this)
                deleteAllModelsInteractor.execute({
                    Toast
                            .makeText(baseContext, "Cache deleted successfully!", Toast.LENGTH_SHORT)
                            .show()
                    count(Type.SHOP, textShopRecords)
                    count(Type.ANTIVITY, textActivityRecords)
                }, {
                    Toast
                            .makeText(baseContext, "Error deleting!", Toast.LENGTH_SHORT)
                            .show()
                })
            })

            dialog.show()
        }
    }

    private fun count(type: Type, into: TextView) {
        val countAllModelsInteractor: CountAllModelsInteractor = CountAllModelsInteractorImpl(this)
        countAllModelsInteractor.execute(type, object: SuccessCompletion<Long> {
            override fun successCompletion(count: Long) {
                into.text = count.toString()
            }
        }, object: ErrorCompletion {
            override fun errorCompletion(errorMessage: String) {
                Toast
                        .makeText(baseContext, "Error counting!", Toast.LENGTH_SHORT)
                        .show()
            }
        })
    }
}

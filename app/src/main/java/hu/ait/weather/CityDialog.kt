package hu.ait.weather

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.city_dialog.*
import kotlinx.android.synthetic.main.city_dialog.view.*

class CityDialog : DialogFragment() {

    lateinit var etCityName: EditText
    lateinit var cityHandler: CityHandler

    interface CityHandler {
        fun cityAdded(city: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is CityHandler){
            cityHandler = context
        } else {
            throw RuntimeException(
                getString(R.string.exception))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setTitle(getString(R.string.dialog))
        val dialogView = requireActivity().layoutInflater.inflate(
            R.layout.city_dialog, null
        )

        etCityName = dialogView.etCityName

        dialogBuilder.setView(dialogView)

        dialogBuilder.setPositiveButton("Ok") {
                dialog, which -> cityHandler.cityAdded(etCityName.text.toString())
        }
        dialogBuilder.setNegativeButton("Cancel") {
                dialog, which ->
        }

        return dialogBuilder.create()
    }


}
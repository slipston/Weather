package hu.ait.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import hu.ait.weather.adapter.CityAdapter
import hu.ait.weather.touch.CityRecyclerTouchCallback
import kotlinx.android.synthetic.main.activity_main.*

class CityListActivity : AppCompatActivity(), CityDialog.CityHandler {

    lateinit var cityAdapter: CityAdapter

    companion object {
        const val DIALOG_TAG = "Dialog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(toolbar)

        btnAddCity.setOnClickListener {
            showAddCityDialog()
        }

        btnDeleteAll.setOnClickListener {
            deleteAllCities()
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        Thread {
            runOnUiThread {
                cityAdapter = CityAdapter(this)
                recyclerCity.adapter = cityAdapter

                val touchCallbackList = CityRecyclerTouchCallback(cityAdapter)
                val itemTouchHelper = ItemTouchHelper(touchCallbackList)
                itemTouchHelper.attachToRecyclerView(recyclerCity)
            }
        }.start()
    }

    private fun showAddCityDialog() {
        CityDialog().show(supportFragmentManager, DIALOG_TAG)
    }

    override fun cityAdded(city: String) {
        Thread {
            runOnUiThread {
                cityAdapter.addCity(city)
            }
        }.start()
    }

    fun deleteAllCities() {
        cityAdapter.deleteAllCities()
    }
}

package hu.ait.weather.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import hu.ait.weather.CityDetails
import hu.ait.weather.CityListActivity
import hu.ait.weather.R
import hu.ait.weather.touch.CityTouchHelperCallback
import kotlinx.android.synthetic.main.city_row.view.*
import java.util.*

class CityAdapter : RecyclerView.Adapter<CityAdapter.ViewHolder>, CityTouchHelperCallback {

    companion object {
        const val CITY_NAME = "City name"
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnDeleteCity = itemView.btnDeleteCity
        val btnCity = itemView.btnCity
    }

    var cityNames = mutableListOf<String>()
    val context: Context

    constructor(context: Context) {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.city_row, parent, false
        )

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cityNames.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCity = cityNames[position]
        holder.btnCity.text = currentCity

        holder.btnDeleteCity.setOnClickListener {
            deleteCity(holder.adapterPosition)
        }

        holder.btnCity.setOnClickListener {
            var intentDetails = Intent()
            intentDetails.setClass(context as CityListActivity, CityDetails::class.java)
            intentDetails.putExtra(CITY_NAME, currentCity)
            startActivity(context, intentDetails,null)
        }
    }

    override fun onDismissed(position: Int) {
        deleteCity(position)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        Collections.swap(cityNames, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    private fun deleteCity(position: Int) {
        Thread {
            (context as CityListActivity).runOnUiThread {
                cityNames.removeAt(position)
                notifyItemRemoved(position)
            }
        }.start()
    }

    public fun addCity(city: String) {
        cityNames.add(city)
    }

    fun deleteAllCities() {
        cityNames.clear()
        notifyDataSetChanged()
    }

}
package xyz.manolos.inmovies.util

import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {

    fun formatDate (date :String? ) : String {
        val newDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)
        return SimpleDateFormat("dd/MM/yyyy").format(newDate)
    }
}

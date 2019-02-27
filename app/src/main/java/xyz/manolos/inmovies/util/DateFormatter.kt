package xyz.manolos.inmovies.util

import java.text.SimpleDateFormat

object DateFormatter {

    fun formatDate (date :String? ) : String {
        var format = SimpleDateFormat("yyyy-MM-dd")
        val newDate = format.parse(date)

        format = SimpleDateFormat("dd/MM/yyyy")
        return format.format(newDate)
    }
}

package fr.thomas.lefebvre.mynews.utils

import java.text.SimpleDateFormat

class DateUtils {

    //"yyyy-MM-dd'T'HH:mm:ss+Z"

    fun dateFormat(date:String):String{
        var formatInitial=SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        var formatFinal=SimpleDateFormat("dd/MM/yy")
        var dateInitial=formatInitial.parse(date)
        var dateFinalString=formatFinal.format(dateInitial)
                return dateFinalString
    }

    fun dateFormatMostPopular(date:String):String{
        var formatInitial=SimpleDateFormat("yy-MM-dd")
        var formatFinal=SimpleDateFormat("dd/MM/yy")
        var dateInitial=formatInitial.parse(date)
        var dateFinalString=formatFinal.format(dateInitial)
        return dateFinalString
    }
}
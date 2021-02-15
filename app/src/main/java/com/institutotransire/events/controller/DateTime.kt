package com.institutotransire.events.controller

import java.text.SimpleDateFormat

object DateTime {
    @JvmStatic
    fun getDate(timeInMillies: Long): String? {
        var date: String? = null
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        date = formatter.format(timeInMillies)
        println("Today is $date")
        return date
    }
}
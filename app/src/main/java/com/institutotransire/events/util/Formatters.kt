package com.institutotransire.events.util

import java.text.NumberFormat
import java.util.*

object Formatters {
    private var nf: NumberFormat? = null
    private val symbol: String? = null
    fun prepare() {
        nf = NumberFormat.getCurrencyInstance(Locale.getDefault())
        nf?.setMinimumFractionDigits(2)
        nf?.setMaximumFractionDigits(2)
        val cur = nf?.format(0)
        println("dlsadllfsdf")
        println(cur?.split("0")?.toTypedArray()?.get(0))
    }

    @JvmStatic
    fun numFormat(): NumberFormat? {
        if (nf == null) {
            nf = NumberFormat.getInstance(Locale.getDefault())
            nf?.setMinimumFractionDigits(2)
            nf?.setMaximumFractionDigits(2)
        }
        return nf
    }
}
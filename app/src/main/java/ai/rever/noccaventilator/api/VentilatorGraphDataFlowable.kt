package ai.rever.noccaventilator.api

import com.github.mikephil.charting.data.Entry
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

val ptGraphEntryFlowable get() = run {
    val startSec = System.currentTimeMillis()
    val entries = ArrayList<Entry>()
    valueSignalFlowable("P")
        .map {
            if (it > 0) {
                val secSinceStart = System.currentTimeMillis()
                    .run { ((this - startSec) / 1000.toFloat()) }
                entries.add(Entry(secSinceStart, it.toFloat()))
                if (entries.size == 50) {
                    entries.removeAt(0)
                }
            }
            entries
        }
}

val vtGraphDataFlowable get() = run {
    val startSec = System.currentTimeMillis()
    val entries = ArrayList<Entry>()
    valueSignalFlowable("V")
        .map {
            if (it > 0) {
                val secSinceStart = System.currentTimeMillis()
                    .run { ((this - startSec) / 1000.toFloat()) }
                entries.add(Entry(secSinceStart, it.toFloat()))
                if (entries.size == 50) {
                    entries.removeAt(0)
                }
            }
            entries
        }.observeOn(AndroidSchedulers.mainThread())
}

val frGraphDataFlowable get() = run {
    val startSec = System.currentTimeMillis()
    val entries = ArrayList<Entry>()
    valueSignalFlowable("F")
        .map {
            if (it > 0) {
                val secSinceStart = System.currentTimeMillis()
                    .run { ((this - startSec) / 1000.toFloat()) }
                entries.add(Entry(secSinceStart, it.toFloat()))
                if (entries.size == 50) {
                    entries.removeAt(0)
                }
            }
            entries
        }.observeOn(AndroidSchedulers.mainThread())
}
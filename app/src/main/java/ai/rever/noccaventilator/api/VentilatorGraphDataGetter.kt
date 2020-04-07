package ai.rever.noccaventilator.api

import com.github.mikephil.charting.data.Entry
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

val randomData: Int get() {
    return (40..60).random()
}

fun ptGraphDataGetter(str: String): Observable<ArrayList<Entry>> {
    val startSec = System.currentTimeMillis()
    val entries = ArrayList<Entry>()
    return Observable.interval(0, 200, TimeUnit.MILLISECONDS)
        .map {
            val secSinceStart = System.currentTimeMillis()
                .run { ((this - startSec) / 1000.toFloat()) }
            entries.add(Entry(secSinceStart, randomData.toFloat()))
            if (entries.size == 50) {
                entries.removeAt(0)
            }

            entries
        }.observeOn(AndroidSchedulers.mainThread())
}

fun vtGraphDataGetter(str: String): Observable<ArrayList<Entry>> {
    val startSec = System.currentTimeMillis()
    val entries = ArrayList<Entry>()
    return Observable.interval(0, 200, TimeUnit.MILLISECONDS)
        .map {
            val secSinceStart = System.currentTimeMillis()
                .run { ((this - startSec) / 1000.toFloat()) }
            entries.add(Entry(secSinceStart, randomData.toFloat()))
            if (entries.size == 50) {
                entries.removeAt(0)
            }

            entries
        }.observeOn(AndroidSchedulers.mainThread())
}

fun frGraphDataGetter(str: String): Observable<ArrayList<Entry>> {
    val startSec = System.currentTimeMillis()
    val entries = ArrayList<Entry>()
    return Observable.interval(0, 200, TimeUnit.MILLISECONDS)
        .map {
            val secSinceStart = System.currentTimeMillis()
                .run { ((this - startSec) / 1000.toFloat()) }
            entries.add(Entry(secSinceStart, randomData.toFloat()))
            if (entries.size == 50) {
                entries.removeAt(0)
            }

            entries
        }.observeOn(AndroidSchedulers.mainThread())
}
package ai.rever.noccaventilator.api

import com.github.mikephil.charting.data.Entry
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Flowable

const val dataResolutionSec: Float = 5f
fun secSinceStart(startSec: Long) = System.currentTimeMillis().run {
    ((this - startSec) / 1000.toFloat())
}

val ptGraphEntryFlowable: @NonNull Flowable<java.util.ArrayList<Entry>>
    get() = run {
        val startSec = System.currentTimeMillis()
        val entries = ArrayList<Entry>()
        floatSignalFlowable("P")
            .map {
                secSinceStart(startSec).let { secSinceStart ->
                    val firstDataSec = secSinceStart - dataResolutionSec
                    entries.add(Entry(secSinceStart, it))
                    entries.firstOrNull()?.apply {
                        if (x > firstDataSec) {
                            entries.add(Entry(firstDataSec, y))
                        }
                    }
                    entries.removeIf { entry ->
                        entry.x < firstDataSec
                    }
                }
                entries
            }
    }

val vtGraphDataFlowable: @NonNull Flowable<java.util.ArrayList<Entry>>
    get() = run {
        val startSec = System.currentTimeMillis()
        val entries = ArrayList<Entry>()
        floatSignalFlowable("V")
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

val frGraphDataFlowable: @NonNull Flowable<java.util.ArrayList<Entry>>
    get() = run {
        val startSec = System.currentTimeMillis()
        val entries = ArrayList<Entry>()
        floatSignalFlowable("F")
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
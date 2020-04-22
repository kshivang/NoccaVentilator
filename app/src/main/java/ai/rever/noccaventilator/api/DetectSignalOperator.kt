package ai.rever.noccaventilator.api

import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject

/**
 * This is example, which illustrate
 * how we can detect signal in Rx
 */

class DetectSignalOperator(private val startDelimiter: String,
                           private val endDelimiter: String): ObservableOperator<String, String> {
    override fun apply(observer: Observer<in String>?): Observer<in String> {
        return OP(startDelimiter, endDelimiter, observer)
    }

    inner class OP(private val startDelimiter: String,
                   private val endDelimiter: String,
                   private val child: Observer<in String>?): Observer<String>, Disposable {

        private var disposable: Disposable? = null
        private var str = ""
        private var parsing = false

        override fun onComplete() {
            child?.onComplete()
        }

        override fun onSubscribe(d: Disposable?) {
            disposable = d
        }

        override fun onNext(t: String?) {
            Observable.fromIterable(t?.split(""))
                .map { currChar ->
                if (currChar == startDelimiter) {
                    parsing = true
                } else if (currChar == endDelimiter) {
                    parsing = false
                    if (str.isNotEmpty()) {
                        child?.onNext(str)
                        str = ""
                    }
                } else {
                    if (parsing) {
                        str += currChar
                    }
                }
                str
            }.blockingSubscribe()
        }

        override fun onError(e: Throwable?) {
            child?.onError(e)
        }

        override fun isDisposed(): Boolean {
            return disposable?.isDisposed ?: true
        }

        override fun dispose() {
            disposable?.dispose()
        }
    }
}

fun main() {
    val subject: BehaviorSubject<String> = BehaviorSubject.create()
    subject
        .lift(DetectSignalOperator("*", "#"))
        .firstOrErrorStage()
        .thenAccept { println(it) }

    subject.onNext("*goijgeoij#goijgeoij*")
    subject.onNext("goeijge#ogijeoi*gjiogei")
    subject.onNext("#joigjeoij#jogeijo")
    subject.onNext("*goijego")
    subject.onNext("*oijgeoij#")
}
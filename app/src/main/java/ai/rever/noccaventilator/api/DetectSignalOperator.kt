package ai.rever.noccaventilator.api

import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * This is example, which illustrate
 * how we can detect signal in Rx
 */

class DetectSignalOperator(private val startDelimiter: String,
                           private val endDelimiter: String): FlowableOperator<String, String> {

    override fun apply(subscriber: Subscriber<in String>?): Subscriber<in String> {
        return OP(startDelimiter, endDelimiter, subscriber)
    }

    inner class OP(private val startDelimiter: String,
                   private val endDelimiter: String,
                   private val child: Subscriber<in String>?)
        : FlowableSubscriber<String>, Subscription {

        var subscription: Subscription? = null
        private var str = ""
        private var reading = false

        override fun onComplete() {
            child?.onComplete()
        }

        override fun onSubscribe(s: Subscription?) {
            this.subscription = s
            child?.onSubscribe(this)
        }

        override fun onNext(t: String?) {
            Flowable.fromIterable(t?.split(""))
                .map { currChar ->
                if (currChar == startDelimiter) {
                    reading = true
                } else if (currChar == endDelimiter) {
                    reading = false
                    if (str.isNotEmpty()) {
                        child?.onNext(str)
                        str = ""
                    }
                } else {
                    if (reading) {
                        str += currChar
                    }
                }
                str
            }.blockingSubscribe()
        }

        override fun onError(t: Throwable?) {
            child?.onError(t)
        }

        override fun cancel() {
            subscription?.cancel()
        }

        override fun request(n: Long) {
            subscription?.request(n)
        }
    }

}

fun main() {
    val subject: BehaviorSubject<String> = BehaviorSubject.create()
    subject.toFlowable(BackpressureStrategy.LATEST)
        .lift(DetectSignalOperator("*", "#")).subscribe {
            println(it)
        }

    subject.onNext("*goijgeoij#goijgeoij*")
    subject.onNext("goeijge#ogijeoi*gjiogei")
    subject.onNext("#joigjeoij#jogeijo")
    subject.onNext("*goijego")
    subject.onNext("*oijgeoij#")
}
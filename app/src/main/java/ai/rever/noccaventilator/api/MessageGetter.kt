package ai.rever.noccaventilator.api

import ai.rever.noccaventilator.backend.UsbServiceManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

fun parsedSignalGetter(startDelimiter: String, endDelimiter: String)
     = UsbServiceManager.messageFlowable
        .lift(DetectSignalOperator(startDelimiter, endDelimiter))
        .observeOn(AndroidSchedulers.mainThread())

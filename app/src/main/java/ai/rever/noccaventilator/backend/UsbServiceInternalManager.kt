package ai.rever.noccaventilator.backend

import io.reactivex.rxjava3.subjects.PublishSubject

object UsbServiceInternalManager {
    val messageFromSerialPortSubject: PublishSubject<String> = PublishSubject.create()
    val usbStatusBehaviorSubject: PublishSubject<UsbStatus> = PublishSubject.create()
    val ctcChangeSubject: PublishSubject<Boolean> = PublishSubject.create()
    val dsrChangeSubject: PublishSubject<Boolean> = PublishSubject.create()
}
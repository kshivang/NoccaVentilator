package ai.rever.noccaventilator.backend

interface UsbServiceInterface {
    fun write(data: ByteArray?)
}
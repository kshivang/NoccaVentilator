package ai.rever.noccaventilator.model

data class VentilatorAlarm (val pHigh: Int = 32, val pLow: Int = 21,
                            val vTelHigh: Int = 42, val vTelLow: Int = 21,
                            val rrHigh: Int = 52, val rrLow: Int = 25)
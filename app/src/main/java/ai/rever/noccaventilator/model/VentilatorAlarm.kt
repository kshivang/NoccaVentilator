package ai.rever.noccaventilator.model

data class VentilatorAlarm (var pHigh: Int = 32, var pLow: Int = 21,
                            var vTelHigh: Int = 42, var vTelLow: Int = 21,
                            var rrHigh: Int = 52, var rrLow: Int = 25)
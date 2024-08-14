package com.example.elm327.elm

enum class ObdPids(
    val pid: String,
    val descriptionShort: String,
    val descriptionLong: String,
    val decoder: Decoders
) {
    NO_PID_FOUND("", "", "", Decoders.DEFAULT),
    PID_01("01", "", "Supported PIDs [01-20]", Decoders.DEFAULT),
    ;

    companion object {
        private val map: Map<String, ObdPids> = entries.associateBy(ObdPids::pid)
        operator fun get(pid: String) = map[pid] ?: NO_PID_FOUND

        fun parse(rawData: String): Pair<ObdPids, DecodedValue> {
            val data = rawData.replace("\\s".toRegex(), "")
            val pidString = data.slice(7..8)
            val pid = ObdPids[pidString]
            return Pair(pid, pid.decoder.decode(data))
        }
    }
}


enum class Decoders(val decode: (String) -> DecodedValue) {

    DEFAULT({ value -> RawData(value) }),
    PERCENT({ value -> SingleValue(Parser(value).A * 100 / 255, Printers.PERCENTAGE) }),
    PERCENT_CENTERED({ value ->SingleValue((Parser(value).A - 128) * 100 / 128,Printers.PERCENTAGE) }),
    TEMPERATURE({ value -> SingleValue(Parser(value).A + 233, Printers.TEMPERATURE) }),
    FUEL_PRESSURE({ value -> SingleValue(Parser(value).A * 3, Printers.TEMPERATURE) }),
    RPM({ value -> SingleValue(Parser(value).AB / 4, Printers.ENGINE_SPEED) }),
    TIMING_ADVANCE({ value -> SingleValue(Parser(value).A / 2 - 64, Printers.ANGLE) }),
    AIR_FLOW_RATE({ value -> SingleValue(Parser(value).AB / 100, Printers.FLOW) }),

    ;


    class Parser(private var rawData: String) {
        val A = rawData.slice(0..1).toInt(radix = 16).toFloat()
        val B = rawData.slice(2..3).toInt(radix = 16).toFloat()
        val C = rawData.slice(4..5).toInt(radix = 16).toFloat()
        val D = rawData.slice(6..7).toInt(radix = 16).toFloat()
        val AB = rawData.slice(0..3).toInt(radix = 16).toFloat()
        val CD = rawData.slice(4..7).toInt(radix = 16).toFloat()
//        val bits =
    }
}

abstract class DecodedValue(private val printer: Printers) {
    override fun toString(): String {
        return printer.print(this)
    }
}

class RawData(val rawData: String) : DecodedValue(Printers.DEFAULT)
class SingleValue(val singleValue: Float, printer: Printers) : DecodedValue(printer)
class TwoValues(val first: Float, val second: Float, printer: Printers) : DecodedValue(printer)
class PidsList(val pids: List<Boolean>, printer: Printers) : DecodedValue(printer)

enum class Printers(val print: (DecodedValue) -> String) {
    DEFAULT({ value -> (value as RawData).rawData }),
    PERCENTAGE({ value -> "${(value as SingleValue).singleValue} %" }),
    VOLTAGE({ value -> "${(value as SingleValue).singleValue} V" }),
    TEMPERATURE({ value -> "${(value as SingleValue).singleValue} K" }),
    PRESSURE_KPA({ value -> "${(value as SingleValue).singleValue} kPa" }),
    PRESSURE_PA({ value -> "${(value as SingleValue).singleValue} Pa" }),
    ENGINE_SPEED({ value -> "${(value as SingleValue).singleValue} rpm" }),
    SPEED({ value -> "${(value as SingleValue).singleValue} km/h" }),
    ANGLE({ value -> "${(value as SingleValue).singleValue} °" }),
    FLOW({ value -> "${(value as SingleValue).singleValue} g/s" }),
    VOLTAGE_AND_PERCENTAGE({ value -> "${(value as TwoValues).first} V, ${value.second} %" }),
    TIME({ value -> "${(value as SingleValue).singleValue} sec" }),
    DISTANCE({ value -> "${(value as SingleValue).singleValue} km" }),
    RATIO_AND_VOLTAGE({ value -> "${(value as TwoValues).first} (ratio), ${value.second} V" }),
}
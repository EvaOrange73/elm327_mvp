package com.example.elm327.elm

enum class ObdPids(
    val pid: String,
    val descriptionShort: String,
    val descriptionLong: String,
    val decoder: Decoders
) {
    NO_PID_FOUND("", "", "", Decoders.DEFAULT),
    PID_01("01", "", "Supported PIDs [01-20]", Decoders.DEFAULT);

    companion object {
        private val map : Map<String, ObdPids> = entries.associateBy(ObdPids::pid)
        operator fun get(pid: String) = map[pid] ?: NO_PID_FOUND

        fun parse(rawData: String): Pair<ObdPids, DecodedValue> {
            val data = rawData.replace("\\s".toRegex(), "")
            val pidString = data.slice(7 .. 8)
            val pid = ObdPids[pidString]
            return Pair(pid, pid.decoder.decode(data))
        }
    }
}



enum class Decoders(val decode: (String) -> DecodedValue) {

    DEFAULT({ value -> RawData(value) }),
    PERCENT({ value -> SingleValue(Parser(value).A * 100 / 255, Printers.PERCENTAGE) });

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
}
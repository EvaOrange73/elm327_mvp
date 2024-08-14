package com.example.elm327

import com.example.elm327.elm.ObdPids
import org.junit.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.ValueSource

class ObdDecodersTest {
    @ParameterizedTest
    @ValueSource(strings = ["", "NO DATA", "ausguiahdkjl"])
    fun `no data`(source: String) {
        val (pid, decoded) = ObdPids.parse(source)
        assertEquals(ObdPids.NO_PID_FOUND, pid)
        assertEquals(decoded.toString(), source)
    }


    enum class HeadersOnSource(
        val expectedPid: ObdPids,
        val expectedString: String,
        val source: String
    ) {
        TEST_1(ObdPids.PID_01, "", ""),
        TEST_2(ObdPids.PID_01, "", ""),
        TEST_3(ObdPids.PID_01, "", ""),
    }

    @ParameterizedTest
    @EnumSource(HeadersOnSource::class)
    fun `headers on`(source: HeadersOnSource) {
        val (pid, decoded) = ObdPids.parse(source.source)
        assertEquals(ObdPids.NO_PID_FOUND, source.expectedPid)
        assertEquals(decoded.toString(), source.expectedString)
    }


    // TODO: headers off, spaces on/off ...
    // TODO: supported pids
}
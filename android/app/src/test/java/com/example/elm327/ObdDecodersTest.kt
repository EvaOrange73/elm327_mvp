package com.example.elm327

import com.example.elm327.util.elm.ObdPids
import org.junit.Assert.assertEquals
import org.junit.Test

class ObdDecodersTest {
    @Test
    fun empty() {
        val source = ""
        val (pid, decoded) = ObdPids.parse(source)
        assertEquals(ObdPids.NO_PID_FOUND, pid)
        assertEquals(source, decoded.toString())
    }

    @Test
    fun `no data`() {
        val source = "NO DATA"
        val (pid, decoded) = ObdPids.parse(source)
        assertEquals(ObdPids.NO_PID_FOUND, pid)
        assertEquals(source, decoded.toString())
    }

    @Test
    fun `16 characters`() {
        val source = "ausgausgausgausg"
        val (pid, decoded) = ObdPids.parse(source)
        assertEquals(ObdPids.NO_PID_FOUND, pid)
        assertEquals(source, decoded.toString())
    }


    @Test
    fun `speed with spaces`(){
        val source = "7E8 02 41 0D 00 00 00 00 00"
        val (pid, decoded) = ObdPids.parse(source)
        assertEquals(ObdPids.PID_0D, pid)
        assertEquals("0.0 km/h", decoded.toString())
    }

    @Test
    fun `speed without spaces`(){
        val source = "7E8 02 41 0D 00 00 00 00 00"
        val (pid, decoded) = ObdPids.parse(source)
        assertEquals(ObdPids.PID_0D, pid)
        assertEquals("0.0 km/h", decoded.toString())
    }

    @Test
    fun `pid getter`(){
        val source = "7E8 02 41 00 BE 1F A8 13 00"
        val (pid, decoded) = ObdPids.parse(source)
        assertEquals(ObdPids.PID_00, pid)
        assertEquals("supported PIDs (1 - 20): [01, 03, 04, 05, 06, 07, 0C, 0D, 0E, 0F, 10, 11, 13, 15, 1C, 1F, 20]", decoded.toString())
    }


}
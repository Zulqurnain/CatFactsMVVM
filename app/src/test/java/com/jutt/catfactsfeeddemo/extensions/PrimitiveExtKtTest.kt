package com.jutt.catfactsfeeddemo.extensions

import org.junit.After
import org.junit.Before

import org.junit.Test
import com.google.common.truth.Truth.*
import java.util.*


class PrimitiveExtKtTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `boolean to Int convert to 1 when boolean is true`(){
        assertThat(true.toInt).isEqualTo(1)
    }

    @Test
    fun `boolean to Int convert to 0 when boolean is false`(){
        assertThat(false.toInt).isEqualTo(0)
    }

    @Test
    fun `1000 millis to 1 seconds`(){
        assertThat((1000L).toSeconds).isEqualTo(1)
    }

    @Test
    fun `99 seconds to 99000 milli seconds`(){
        assertThat((99L).toMillis).isEqualTo(99000)
    }

    @Test
    fun `9 Long value convert to string 09`(){
        assertThat((9L).toTimeString).isEqualTo("09")
    }

    @Test
    fun `same date checking through parseAsTime`(){
        assertThat((9L).parseAsTime().time.toSeconds).isEqualTo(9)
    }

    @Test
    fun `"rajas" name "s" should be removed`(){
        assertThat("rajas".removeAt(4)).isEqualTo("raja")
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun `calling removeAt on wrong index should fail`(){
        assertThat("rajas".removeAt(5))
    }

    @Test
    fun `format 1,12 to maximum 5 places and minimum 2 places which will be 1,12`(){
        assertThat((1.12).formatUpToNPlaces(maximumPlaces = 5,minimumPlaces = 2).toString()).isEqualTo("1.12")
    }
}
package com.polotika.androidunittesting.homework

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class Session1Test{

    @Test
    fun `not matched braces string returns false`(){
        val notMatchedString = "("
        val result = Session1.checkBraces(notMatchedString)
        assertThat(result).isFalse()
    }


    @Test
    fun `braces string returns true`(){
        val notMatchedString = "()"
        val result = Session1.checkBraces(notMatchedString)
        assertThat(result).isTrue()
    }

    @Test
    fun `fib of 1 returns true`(){
        val result = Session1.fib(1)
        assertThat(result).isEqualTo(1)
    }
    @Test
    fun `fib of 1 returns false`(){
        val result = Session1.fib(1)
        assertThat(result).isNotEqualTo(12)
    }

    @Test
    fun `fib of 0 returns true`(){
        val result = Session1.fib(0)
        assertThat(result).isEqualTo(0)
    }

    @Test
    fun `fib of 0 returns false`(){
        val result = Session1.fib(0)
        assertThat(result).isNotEqualTo(11)
    }

    @Test
    fun `fib of n returns false`(){
        val result = Session1.fib(20)
        assertThat(result).isNotEqualTo(20)
    }

    @Test
    fun `fib of n returns true`(){
        val result = Session1.fib(7)
        //fib(n) = fib(n-2) + fib(n-1)
        assertThat(result).isEqualTo(8)
    }


}
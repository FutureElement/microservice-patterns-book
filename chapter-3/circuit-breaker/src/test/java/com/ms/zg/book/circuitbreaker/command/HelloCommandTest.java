package com.ms.zg.book.circuitbreaker.command;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HelloCommandTest {

    @Test
    public void testHelloCommand() {
        String result = new HelloCommand("hystrix").execute();
        assertEquals("Hello Failure hystrix!", result);
    }

    @Test
    public void testHelloCommand2() {
        String result = new HelloCommand2("hystrix").execute();
        assertEquals("Hello Failure hystrix!", result);
    }
}
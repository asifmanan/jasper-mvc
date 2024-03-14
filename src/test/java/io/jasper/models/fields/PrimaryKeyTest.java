package io.jasper.models.fields;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrimaryKeyTest {
    @Test
    public void testInitialization(){
        PrimaryKey field = new PrimaryKey();
        assertEquals(Integer.valueOf(0),field.getValue(),"Primary key should initialize with 0");
    }
    @Test
    public void testIncrement(){
        PrimaryKey field = new PrimaryKey();
        int value = field.getValue();
        field.increment();
        assertEquals(Integer.valueOf(1),field.getValue(),"increment() should increment PrimaryKey field with 1");
    }
}

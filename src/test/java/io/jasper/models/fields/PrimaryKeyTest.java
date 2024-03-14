package io.jasper.models.fields;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrimaryKeyTest {
    @Test
    public void testInitializationAndIncrement(){
//  Since the idGenerator is persistent, therefore increment and initialization tests
//  are combined and cannot be separated.
        PrimaryKey key = new PrimaryKey();
        int ExpValue = 1;
        assertEquals(ExpValue,key.getValue(),"Primary key should initialize with 1");

        PrimaryKey key1 = new PrimaryKey();
        ExpValue += 1;
        assertEquals(ExpValue,key1.getValue(),"Instantiation should increment PrimaryKey field with 1");

        PrimaryKey key2 = new PrimaryKey();
        ExpValue += 1;
        assertEquals(ExpValue,key2.getValue(),"Instantiation should increment PrimaryKey field with 1");

        PrimaryKey key3 = new PrimaryKey();
        ExpValue += 1;
        assertEquals(ExpValue,key3.getValue(),"Instantiation should increment PrimaryKey field with 1");
    }
}

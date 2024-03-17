package io.jasper.models.fields;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PrimaryKeyTest {
    @Test
    public void testInitialization(){
//  Updated the test, PrimaryKey field no longer handles increments
//  Increment logic handled at Data layer
        PrimaryKey key = new PrimaryKey();
        assertNull(key.getValue(), "Primary key should initialize with a null value");
    }
    @Test
    public void testSetValueForInteger(){
        PrimaryKey key = new PrimaryKey();
        Integer value = 10;
        key.setValue(value);
        assertEquals(value, key.getValue(),"Value should be set by using setValue()");
    }
    @Test
    public void testSetValueForInt(){
        PrimaryKey key = new PrimaryKey();
        int value = 10;
        key.setValue(value);
        assertEquals(value, key.getValue());
    }
    @Test
    public void testSetValueForNull(){
        PrimaryKey key = new PrimaryKey();
        key.setValue(null);
        assertNull(key.getValue(),"Value can be set and reset to null");
    }
    @Test
    public void testSetValueForInvalidValue(){
        PrimaryKey key = new PrimaryKey();
        int value0 = 0;
        int value_1 = -1;
        key.setValue(value0);
        assertNull(key.getValue(),"Value less than 1 should be set as null");

        key.setValue(value_1);
        assertNull(key.getValue(),"Value less than 1 should be set as null");
    }
}

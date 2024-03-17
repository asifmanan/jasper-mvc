package io.jasper.models.fields;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BooleanCustomFieldTest {
    @Test
    public void testInitialization(){
        BooleanField field = new BooleanField();
        assertFalse(field.getValue(), "Field must be correctly initialized to false");
    }
    @Test
    public void testGetValue(){
        BooleanField field = new BooleanField();
        Boolean value = true;
        field.setValue(value);
        assertEquals(field.getValue(), value,"Value must be correctly get");
    }
    @Test
    public void testSetValue(){
        BooleanField field = new BooleanField();

        Boolean value1 = true;
        field.setValue(value1);
        assertEquals(value1, field.getValue(),"Value must be correctly set");

        Boolean value2 = false;
        field.setValue(value2);
        assertEquals(value2, field.getValue(),"Value must be correctly set");
    }
}

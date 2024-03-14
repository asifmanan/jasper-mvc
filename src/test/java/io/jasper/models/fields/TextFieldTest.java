package io.jasper.models.fields;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TextFieldTest {
    @Test
    public void testInitialization(){
        TextField field = new TextField();
        assertNull(field.getValue(), "TextField instance must be initialized as null");
    }
    @Test
    public void testSetValue(){
        TextField field = new TextField();
        String value = "A quick brown fox jumped over a lazy dog";
        field.setValue(value);
        assertEquals(value, field.getValue(),"Value must be correctly set");
    }
    @Test
    public void testGetValue(){
        TextField field = new TextField();
        String value = "Its a good day today";
        field.setValue(value);
        assertEquals(value, field.getValue(),"Value must be correctly get");
    }
}

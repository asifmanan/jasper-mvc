package io.jasper.models.fields;



import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegerFieldTest {
    @Test
    public void testInitialization(){
        IntegerField field = new IntegerField();
        assertEquals(Integer.valueOf(0), field.getValue(), "IntegerField should initialize with 0");
    }

    @Test
    public void testSetValue(){
        IntegerField field = new IntegerField();
        int value = 10;
        field.setValue(value);
        assertEquals(Integer.valueOf(value), field.getValue(), "setValue should update the field's value");
    }
    @Test
    public void testGetValue(){
        IntegerField field = new IntegerField();
        field.setValue(100);
        assertEquals(Integer.valueOf(100),field.getValue(), "getValue should return the current value it holds");
    }
}

package io.jasper.models.fields;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CharFieldTest {
    @Test
    public void testInitializationWithValidMaxLength(){
        int maxLength = 1;
        CharField field = new CharField(maxLength);
        assertEquals(maxLength, field.getMaxLength(),"Max Length needs to be set correctly");
    }
    @Test
    public void testInitializationWithInvalidMaxLength(){
        for(int i=0; i>=-1; i--){
            int maxLength = i;
//            System.out.println("Testing for maxLength = "+maxLength);
            assertThrows(IllegalArgumentException.class, () -> {
                new CharField(maxLength);
            },"Constructor should throw IllegalArgumentException for maxLength <= 0");
        }
    }
    @Test
    public void testSetValueWithValidMaxLength(){
        CharField field = new CharField(4);
        String validValue = "test";

        field.setValue(validValue);
        assertEquals(field.getValue(),validValue, "Set value should set the correct value");
    }
    @Test
    public void testInitializedValue(){
        CharField field = new CharField(10);
        assertNull(field.getValue(), "CharField Instance must be initialized with a null value");
    }

    @Test
    public void testSetValueWithInvalidMaxLength(){
        CharField field = new CharField(4);

        String invalidValue = "Test1";
        assertThrows(IllegalArgumentException.class, () -> {
            field.setValue(invalidValue);
        },"setValue should throw an IllegalArgumentException for string length > maxLength");
    }
    @Test
    public void testGetValue(){
        CharField field = new CharField(6);

        String value = "chips";
        field.setValue(value);
        assertEquals(field.getValue(), value, "getValue must return the same value as set most recently");
    }
    @Test
    public void testUpdateValue(){
        CharField field = new CharField(7);
        String value = "chips";
        field.setValue(value);
        assertEquals(field.getValue(), value, "getValue must return the same value as set most recently");

        String updatedValue = "Doritos";
        field.setValue(updatedValue);
        assertNotEquals(field.getValue(),value,"The value must be correctly updated to a new value with a different length string");
        assertEquals(field.getValue(), updatedValue, "The updated value must be correct");
    }
    @Test void testUpdateToNullValue(){
        CharField field = new CharField(6);
        String value = "Update";
        field.setValue(value);
        assertEquals(field.getValue(), value, "getValue must return the correct set value");

        field.setValue(null);
        assertNull(field.getValue(),"Value must be updated to null if its reset to null");
    }
}

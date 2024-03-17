package io.jasper.models.fields;

public class PrimaryKey extends JasperField<Integer> {
    public PrimaryKey(){
        super.setValue(null);
    }
    public void setValue(Integer val){
        if (val != null && val < 1) {
            super.setValue(null);
        } else {
            super.setValue(val);
        }
    }
    public void setValue(int val){
        if (val < 1) {
            super.setValue(null);
        } else {
            // Autoboxing will convert to Integer
            super.setValue(val);
        }
    }
}

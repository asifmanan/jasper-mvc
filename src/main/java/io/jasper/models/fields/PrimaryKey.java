package io.jasper.models.fields;

public class PrimaryKey extends Field<Integer> {
    public PrimaryKey() {
        setValue(0);
    }
    public void increment(){
        int value = this.getValue();
        this.setValue(value+1);
    }
}

package io.jasper.models.fields;

public class PrimaryKey {
    private int pk;

    public PrimaryKey() {
        this.pk = 0;
    }
    public void increment(){
        this.pk += 1;
    }

    public int getPk() {
        return pk;
    }
}

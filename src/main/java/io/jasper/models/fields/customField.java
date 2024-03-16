package io.jasper.models.fields;

public abstract class customField<T> {
    private T value;
    public T getValue(){
        return value;
    }
    public void setValue(T value){
        this.value = value;
    }
}

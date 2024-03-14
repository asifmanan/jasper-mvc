package io.jasper.models.fields;

public class CharField extends Field<String> {
    private final int maxLength;
    public CharField(int maxLength) {
        if (maxLength <= 0){
            throw new IllegalArgumentException("maxLength must be at least 1");
        }
        this.maxLength = maxLength;
        setValue(null);
    }
    @Override
    public void setValue(String value){
        if(value!=null && value.length() > this.maxLength){
            throw new IllegalArgumentException("Value exceeds maximum length of " + this.maxLength);
        }
        super.setValue(value);
    }
    public int getMaxLength(){
        return this.maxLength;
    }
}

package io.jasper.datasystem.datastructure.adapters;

import java.util.HashMap;
import java.util.Map;

public class TableRecord {
    private int id;
    private Map<String, Object> data;
    public TableRecord(){
        int id = 0;
        this.data = new HashMap<>();
    }
//  cloning constructor
    public TableRecord(TableRecord source){
        this.id = source.id;
        this.data = source.data;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Map<String, Object> getData() {
        return data;
    }
    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}

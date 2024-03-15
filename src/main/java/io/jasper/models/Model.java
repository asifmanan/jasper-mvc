package io.jasper.models;

import io.jasper.managers.DefaultObjectManager;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Model {
    private static final AtomicInteger idGenerator = new AtomicInteger(0);
    private int id;
    private Map<String, Object> fieldValues = new HashMap<>();

    public Model() {
        this.id = idGenerator.incrementAndGet();
        initializeFields();
    }
    private void initializeFields(){
        Field[] fields = this.getClass().getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            field.setAccessible(true);
            try{
                fieldValues.put(field.getName(),field.get(this));
            } catch (IllegalAccessException e){
                e.printStackTrace();
            }
        });
    }
    public void save() {
        updateFieldValues();
        JasperDb.save(this.getClass(),this.id, new HashMap<>(fieldValues));
    }
    private void updateFieldValues(){
        Field[] fields = this.getClass().getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            field.setAccessible(true);
            try {
                fieldValues.put(field.getName(),field.get(this));
            } catch (IllegalAccessException e){
                e.printStackTrace();
            }
        });
    }
    public static <T extends Model> ObjectManager<T> Objects(Class<T> clazz) {
        return new DefaultObjectManager<>(clazz);
    }
    public int getId(){
        return this.id;
    }
}

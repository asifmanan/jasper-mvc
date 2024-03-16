package io.jasper.models;

import io.jasper.managers.DefaultObjectManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class Model {

    private Map<String, Object> fieldValues = new HashMap<>();
    public Integer id;

    public Model() {

    }

    public void save() {
        DefaultDbAdapter<? extends Model> adapter = new DefaultDbAdapter<>(this.getClass());
        Map<String, Object> localFieldValues = extractFieldValues();
        adapter.save(localFieldValues);
        this.setId(adapter.modelData.getId());
//        JasperDb.save(this.getClass(), new HashMap<>(fieldValues));
    }

    private Map<String, Object> extractFieldValues() {
    Map<String, Object> localFieldValues = new HashMap<>();
    Field[] fields = this.getClass().getDeclaredFields();
    Arrays.stream(fields).forEach(field -> {
        field.setAccessible(true);

        try {
            Object fieldObject = field.get(this);
            if (fieldObject != null) {
                Method getValueMethod = fieldObject.getClass().getMethod("getValue");
                Object value = getValueMethod.invoke(fieldObject);
//                System.out.println("Value: " + value);
                localFieldValues.put(field.getName(), value);
            } else {
                // Handle or log the null fieldObject appropriately
                localFieldValues.put(field.getName(), null);
            }
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    });
    return localFieldValues;
}
//    private Map<String,Object> extractFieldValues(){
//        Field[] fields = this.getClass().getDeclaredFields();
//        Arrays.stream(fields).forEach(field -> {
//            field.setAccessible(true);
//
//            try{
//                Object fieldObject = field.get(this);
//                Method getValueMethod = fieldObject.getClass().getMethod("getValue");
//                Object value = getValueMethod.invoke(fieldObject);
//                System.out.println("Value: "+ value);
//                fieldValues.put(field.getName(),value);
////                System.out.println(field.getName()+": "+field.get(this));
//            } catch (IllegalAccessException | NoSuchMethodException e){
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        return fieldValues;
//    }
    public static <T extends Model> ObjectManager<T> Objects(Class<T> clazz) {
        return new DefaultObjectManager<>(clazz);
    }
    public Integer getId(){
        return this.id;
    }
    private void setId(int id){
        this.id = id;
    }
}

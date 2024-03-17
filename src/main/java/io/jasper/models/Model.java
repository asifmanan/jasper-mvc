package io.jasper.models;

import io.jasper.managers.DefaultObjectManager;
import io.jasper.models.fields.PrimaryKey;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class Model {

    private Map<String, Object> fieldValues = new HashMap<>();
    public PrimaryKey id;

    public Model() {

    }

    public void save() {
        DefaultDbAdapter<? extends Model> adapter = new DefaultDbAdapter<>(this.getClass());
        Map<String, Object> localFieldValues = extractFieldValues();
        adapter.save(localFieldValues);
        this.setValue(adapter.modelData.getId());
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
    public static <T extends Model> ObjectManager<T> Objects(Class<T> clazz) {
        return new DefaultObjectManager<>(clazz);
    }
    public Integer getValue(){
        return this.id.getValue();
    }
    private void setValue(int id){
        this.id.setValue(id);
    }
}

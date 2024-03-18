package io.jasper.models;

import io.jasper.datasystem.datastructure.adapters.DefaultDsAdapter;
import io.jasper.models.managers.DefaultObjectManager;
import io.jasper.models.fields.PrimaryKey;
import io.jasper.models.managers.ObjectManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class Model {

    private Map<String, Object> fieldValues = new HashMap<>();
    public PrimaryKey id = new PrimaryKey();

    public Model() {
    }

    public void save() {
        DefaultDsAdapter<? extends Model> adapter = new DefaultDsAdapter<>(this.getClass());
        Map<String, Object> localFieldValues = extractFieldValues();
        adapter.save(localFieldValues);
        this.setId(adapter.getTableRecordId());
//        JasperDb.save(this.getClass(), new HashMap<>(fieldValues));
    }
    public void saveOrUpdate(){
        if(this.id != null) {
            DefaultDsAdapter<? extends Model> adapter = new DefaultDsAdapter<>(this.getClass());
            Map<String, Object> localIdFieldValues = extractFieldValues();
        }

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
    public Integer getId(){
        return this.id.getValue();
    }
    private void setId(int id){
        this.id.setValue(id);
    }
}

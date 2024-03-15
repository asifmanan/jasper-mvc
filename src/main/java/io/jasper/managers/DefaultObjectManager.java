package io.jasper.managers;

import io.jasper.models.JasperDb;
import io.jasper.models.Model;
import io.jasper.models.ObjectManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultObjectManager<T extends Model> implements ObjectManager<T> {
    private final Class<T> modelClass;
    public DefaultObjectManager(Class<T> modelClass){
        this.modelClass = modelClass;
    }
    @Override
    public T get(Map<String, Object> criteria) {
        for(Map<String, Object> row : JasperDb.getTable(modelClass).values()) {
            if(matchesCriteria(row, criteria)) {
                return convertRowToModel(row);
            }
        }
        return null;
    }
    @Override
    public List<T> filter(Map<String, Object> criteria) {
        List<T> results = new ArrayList<>();
        for(Map<String, Object> row : JasperDb.getTable(modelClass).values()) {
            if(matchesCriteria(row, criteria)) {
                results.add(convertRowToModel(row));
            }
        }
        return results;
    }
    @Override
    public T findById(int id) {
        Map<String, Object> row = JasperDb.findById(modelClass, id);
        if (row != null) {
            return convertRowToModel(row);
        }
        return null;
    }

    private boolean matchesCriteria(Map<String, Object> row, Map<String, Object> criteria) {
        for (Map.Entry<String, Object> entry : criteria.entrySet()) {
            if(!row.containsKey(entry.getKey()) || !row.get(entry.getKey()).equals(entry.getValue())) {
                return false;
            }
        }
        return true;
    }
    private T convertRowToModel(Map<String, Object> row){
        T instance;
        try {
            instance = modelClass.getDeclaredConstructor().newInstance();
            for(Field field : modelClass.getDeclaredFields()){
                field.setAccessible(true);
                Object value = row.get(field.getName());
                if(value != null ){
                    field.set(instance, value);
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Error creating model instance", e);
        }
        return instance;
    }
}

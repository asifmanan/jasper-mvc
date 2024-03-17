package io.jasper.models;

import io.jasper.models.fields.JasperField;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DefaultDbAdapter<T extends Model> implements DbAdapter {
//    private Map<String, Object> fieldValues;
    private final Class<T> modelClass;
    public final DataBaseRow modelData = new DataBaseRow();
    public static class DataBaseRow{
        private int id;
        private Map<String, Object> data;
        public DataBaseRow(){

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
    public DefaultDbAdapter(Class<T> modelClass) {
        this.modelClass = modelClass;
    }


    @Override
    public Map<String, Object> convertModelToRow(Object instance) {
//        fieldValues = new HashMap<>();
        Map<String, Object> localFieldValues = new HashMap<>();
        Field[] fields = modelClass.getDeclaredFields();

        Arrays.stream(fields).forEach(field -> {

//            System.out.println(field.get(getClass()));
            field.setAccessible(true);
            try {
                localFieldValues.put(field.getName(), field.get(instance));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        for (Map.Entry<String, Object> entry : localFieldValues.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        return localFieldValues;
    }

    @Override
    public T save(Map passedFieldValues) {
        Map<String, Object> localFieldValues = new HashMap<>(passedFieldValues);
        DataBaseRow dataBaseRow = JasperDb.saveAndRetrieve(modelClass, localFieldValues);

        if (dataBaseRow == null) {
            return null;
        }

        modelData.setData(dataBaseRow.getData());
        modelData.setId(dataBaseRow.getId());

        return convertRowToModel(dataBaseRow.data);
    }
    private T convertRowToModel(Map<String, Object> row) {
        T instance;
        try {
            instance = modelClass.getDeclaredConstructor().newInstance();
            for (Field field : modelClass.getDeclaredFields()) {
                field.setAccessible(true);

                Object value = row.get(field.getName());
                if (value != null) {
                    Object fieldObject = field.get(instance);
                    if (fieldObject != null) {
                        // Check if the field is a subclass of JasperField
                        if (JasperField.class.isAssignableFrom(field.getType())) {
                            try {
                                System.out.println("Trying set value");
                                Method setValueMethod = fieldObject.getClass().getMethod("setValue", Object.class);
                                setValueMethod.invoke(fieldObject, value);
                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                System.err.println("Failed to invoke setValue on " + field.getName() + ": " + e.getMessage());
                            }
                        } else {
                            System.out.println("directly setting value");
                            // Directly setting the value for primitive types,
                            // however, This should be avoided and in the model class only JaperField subclasses be allowed
                            field.set(instance, value);
                        }
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Error creating model instance", e);
        }
        return instance;
    }
}

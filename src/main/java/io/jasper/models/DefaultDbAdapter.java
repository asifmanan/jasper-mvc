package io.jasper.models;

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

        return convertRowDataToModel(dataBaseRow.data);
    }
    private T convertRowDataToModel(Map<String, Object> rowData) {
    T instance;
    try {
        instance = modelClass.getDeclaredConstructor().newInstance();
        for (Field field : modelClass.getFields()) {  // Using getFields to access public fields
            Object value = rowData.get(field.getName());

            if (Field.class.isAssignableFrom(field.getType())) {
                Object fieldObject = field.get(instance);

                // No need to check for null since fields are public and should be initialized already
                Method setValueMethod = fieldObject.getClass().getMethod("setValue", value.getClass());
                setValueMethod.invoke(fieldObject, value);
            } else {
                // For primitive types and other public fields, directly assign the value
                if (value == null) {
                    field.set(instance, null);
                } else {
                    field.set(instance, value);
                }
            }
        }
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        throw new RuntimeException("Error creating model instance", e);
        }
    return instance;
    }
    private T convertRowToModel(Map<String, Object> row) {
        T instance;
        try {
            instance = modelClass.getDeclaredConstructor().newInstance();
            System.out.println(instance);
            for (Field field : modelClass.getDeclaredFields()) {
                field.setAccessible(true);

                Object fieldObject = field.get(instance);
                System.out.println(fieldObject);
                if (fieldObject != null) {
                    Object value = row.get(field.getName());
                    System.out.println("field Name:" +field.getName());

                    System.out.println(value.getClass());
                    if (value != null) {
                        // The parameter type of jasperField is T, so we have to manually ensure type safety
                        Method setValueMethod = fieldObject.getClass().getMethod("setValue", Object.class);
                        // Invoke setValue on the fieldObject with the provided value
                        System.out.println("field Object: "+fieldObject);
                        setValueMethod.invoke(fieldObject, value);
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Error creating model instance", e);
        }
        return instance;
    }
}

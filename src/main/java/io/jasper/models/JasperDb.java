package io.jasper.models;

import java.util.HashMap;
import java.util.Map;

public class JasperDb {
    private static final Map<Class<?>, Map<Integer, Map<String, Object>>> dB = new HashMap<>();
    public static void save(Class<?> clazz, Integer id, Map<String, Object> rowData){
        dB.computeIfAbsent(clazz, k -> new HashMap<>()).put(id,rowData);
    }
    public static Map<Integer, Map<String,Object>> getTable(Class<?> clazz){
        return dB.getOrDefault(clazz, new HashMap<>());
    }
    public static Map<String, Object> getOrReturn(Class<?> clazz, Integer id) {
        return dB.getOrDefault(clazz, new HashMap<>()).get(id);
    }

    public static Map<String, Object> findById(Class<?> clazz, Integer id) {
        Map<Integer, Map<String, Object>> table = dB.get(clazz);
        if (table != null) {
            return table.get(id);
        }
        return null;
    }
    public static void delete(Class<?> clazz, Integer id){
        Map<Integer, Map<String, Object>> table = dB.get(clazz);
        if(table != null)
            table.remove(id);
    }
}

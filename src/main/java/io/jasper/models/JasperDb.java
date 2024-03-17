package io.jasper.models;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class JasperDb {
    private static final Map<Class<?>, AtomicInteger> idGenerators = new HashMap<>();
    private static final Map<Class<?>, Map<Integer, Map<String, Object>>> dataBase = new HashMap<>();
//    public static <T> T save(Class<?> clazz, Map<String, Object> rowData){
    public static void save(Class<?> clazz, Map<String, Object> rowData){
        Map<Integer, Map<String, Object>> table = dataBase.computeIfAbsent(clazz, k -> new HashMap<>());
        idGenerators.computeIfAbsent(clazz, k -> new AtomicInteger());
        int id = idGenerators.get(clazz).incrementAndGet();
        table.putIfAbsent(id,rowData);
    }
    public static DefaultDbAdapter.DataBaseRow saveAndRetrieve(Class<?> clazz, Map<String, Object> rowData){
        Map<Integer, Map<String, Object>> table = dataBase.computeIfAbsent(clazz, k -> new HashMap<>());
        idGenerators.computeIfAbsent(clazz, k -> new AtomicInteger());
        int id = idGenerators.get(clazz).incrementAndGet();
        Map<String, Object> previousRowData = table.putIfAbsent(id, rowData);

        if(previousRowData == null){
//            Which means new data inserted successfully
            DefaultDbAdapter.DataBaseRow dataBaseRow = new DefaultDbAdapter.DataBaseRow();
            dataBaseRow.setId(id);
            dataBaseRow.setData(rowData);

            return dataBaseRow;
        }
        return null;
    }
    public static Map<Integer, Map<String,Object>> getTable(Class<?> clazz){
        return dataBase.getOrDefault(clazz, new HashMap<>());
    }
    public static Map<String, Object> getInstance(Class<?> clazz, Integer id) {
        return dataBase.getOrDefault(clazz, new HashMap<>()).get(id);
    }

    public static Map<String, Object> findById(Class<?> clazz, Integer id) {
        Map<Integer, Map<String, Object>> table = JasperDb.dataBase.get(clazz);
        if (table != null) {
            return table.get(id);
        }
        return null;
    }
    public static void delete(Class<?> clazz, Integer id){
        Map<Integer, Map<String, Object>> table = JasperDb.dataBase.get(clazz);
        if(table != null)
            table.remove(id);
    }
}
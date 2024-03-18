package io.jasper.datasystem.datastructure;

import io.jasper.datasystem.datastructure.adapters.TableRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class JasperDs {
    private static final Map<Class<?>, AtomicInteger> idGenerators = new HashMap<>();
    private static final Map<Class<?>, Map<Integer, Map<String, Object>>> dataBase = new HashMap<>();
//    public static <T> T save(Class<?> clazz, Map<String, Object> rowData){
    public static void save(Class<?> clazz, Map<String, Object> rowData){
        Map<Integer, Map<String, Object>> table = dataBase.computeIfAbsent(clazz, k -> new HashMap<>());
        idGenerators.computeIfAbsent(clazz, k -> new AtomicInteger());
        int id = idGenerators.get(clazz).incrementAndGet();
        table.putIfAbsent(id,rowData);
    }
    public static TableRecord saveAndRetrieve(Class<?> clazz, Map<String, Object> rowData){
        return saveAndRetrieve(clazz, rowData, null);
    }
    public static TableRecord saveAndRetrieve(Class<?> clazz, Map<String, Object> rowData, Integer id){
        Map<Integer, Map<String, Object>> table = dataBase.computeIfAbsent(clazz, k -> new HashMap<>());
        if(id == null){
            idGenerators.computeIfAbsent(clazz, k -> new AtomicInteger());
            id = idGenerators.get(clazz).incrementAndGet();
            Map<String, Object> previousRowData = table.putIfAbsent(id, rowData);
            if(previousRowData == null){
//            Which means new data inserted successfully
                TableRecord tableRecord = new TableRecord();
                tableRecord.setId(id);
                tableRecord.setData(rowData);

                return tableRecord;
            }

        } else {

            table.replace(id, rowData);

            TableRecord tableRecord = new TableRecord();
            tableRecord.setId(id);
            tableRecord.setData(rowData);

            return tableRecord;
        }
        System.out.println("## Returning null from JasperDs");
        return null;
    }
    public static Map<Integer, Map<String,Object>> getTable(Class<?> clazz){
        return dataBase.getOrDefault(clazz, new HashMap<>());
    }
    public static Map<String, Object> getInstance(Class<?> clazz, Integer id) {
        return dataBase.getOrDefault(clazz, new HashMap<>()).get(id);
    }

    public static Map<String, Object> findById(Class<?> clazz, Integer id) {
        Map<Integer, Map<String, Object>> table = JasperDs.dataBase.get(clazz);
        if (table != null) {
            return table.get(id);
        }
        return null;
    }
    public static void delete(Class<?> clazz, Integer id){
        Map<Integer, Map<String, Object>> table = JasperDs.dataBase.get(clazz);
        if(table != null)
            table.remove(id);
    }
}

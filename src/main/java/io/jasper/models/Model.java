package io.jasper.models;

import io.jasper.models.fields.IntegerField;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Model {
    private static final Map<Class<? extends Model>, Map<Integer, Model>> tables = new HashMap<>();
    private static final Map<Class<? extends Model>, AtomicInteger> idGenerators = new HashMap<>();
    protected IntegerField pk = new IntegerField();
    public Model(){
        int id = generateIdForClass(getClass());
        this.pk.setValue(id);
        getTable().put(id,this);
    }
    public static synchronized int generateIdForClass(Class<? extends Model> clazz){
        idGenerators.putIfAbsent(clazz, new AtomicInteger(0));
        return idGenerators.get(clazz).incrementAndGet();
    }
    private Map<Integer, Model> getTable() {
        tables.putIfAbsent(this.getClass(),new HashMap<>());
        return tables.get(this.getClass());
    }
    public void save(){
        getTable().put(this.pk.getValue(),this);
    }
    public void delete(){
        if(pk == null || pk.getValue() == null){
            throw new IllegalStateException("Cannot delete a model without an ID");
        }
        getTable().remove(pk.getValue());
    }
    public static <T extends Model> T findById(Class<T> clazz, int id){
        return clazz.cast(tables.getOrDefault(clazz, new HashMap<>()).get(id));
    }
    public int getId(){
        return this.pk.getValue();
    }
}

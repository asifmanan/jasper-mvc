package io.jasper.models.managers;

import io.jasper.models.Model;

import java.util.List;
import java.util.Map;

public interface ObjectManager<T extends Model> {
    T get(Map<String, Object> searchCriteria);
    List<T> filter(Map<String, Object> criteria);
    T findById(int id);
}

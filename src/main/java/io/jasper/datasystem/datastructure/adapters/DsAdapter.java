package io.jasper.datasystem.datastructure.adapters;

import io.jasper.models.Model;

import java.util.Map;

public interface DsAdapter<T extends Model> {
    Map<String, Object> convertModelToRow(Object instance);
    <U extends Model> T save(Map<String, Object> passedFieldValues);
}

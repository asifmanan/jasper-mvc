package io.jasper.models;

import java.util.Map;

public interface DbAdapter<T extends Model> {
    Map<String, Object> convertModelToRow(Object instance);
    <U extends Model> T save(Map<String, Object> passedFieldValues);
}

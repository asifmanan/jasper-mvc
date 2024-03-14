package io.jasper.models.fields;

import java.util.concurrent.atomic.AtomicInteger;

public class PrimaryKey {
    private static final AtomicInteger idGenerator = new AtomicInteger();
    private final int value;
    public PrimaryKey(){
        this.value = idGenerator.incrementAndGet();
    }
    public int getValue(){
        return this.value;
    }
}

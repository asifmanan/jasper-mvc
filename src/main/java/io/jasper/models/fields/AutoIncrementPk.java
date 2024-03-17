package io.jasper.models.fields;

import java.util.concurrent.atomic.AtomicInteger;

public class AutoIncrementPk extends JasperField<Integer> {
    private static final AtomicInteger idGenerator = new AtomicInteger();
    public AutoIncrementPk(){
        this.setValue(idGenerator.incrementAndGet());
    }
}

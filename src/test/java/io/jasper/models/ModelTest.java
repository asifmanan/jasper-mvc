package io.jasper.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {
    static class TestModelA extends Model {

    }
    static class TestModelB extends Model {

    }
    static class TestModelC extends Model {

    }

    @Test
    void testInitialization(){
        TestModelA model_A1 = new TestModelA();
        assertNull(model_A1.getId(),"Model instance must have an id Null when created");
    }
    @Test
    void testPostSaveIdAssignment(){
        TestModelB model_B1 = new TestModelB();
        assertNull(model_B1.getId(),"Model instance must have an id Null when created");
        model_B1.save();
        assertNotNull(model_B1.getId(),"After saving, Model instance id must be a positive integer value");
    }
    @Test
    void testCreateAndSaveModel(){
        TestModelA model0 = new TestModelA();
        TestModelA model00 = new TestModelA();

        model0.save();
        model00.save();

        assertEquals(1,model0.getId());
        assertEquals(2,model00.getId());
    }
    @Test
    void testFindById(){
        TestModelB model1 = new TestModelB();
        model1.save();
//        TestModel1 foundModel = Model.findById(TestModel1.class,model1.getId());
//        assertEquals(model1.getId(),foundModel.getId());
    }
    @Test
    void testDeleteModel(){
        TestModelB model1 = new TestModelB();
        model1.save();
        int id = model1.getId();
//        model1.delete();
//        TestModel1 foundModel = Model.findById(TestModel1.class,id);
//        assertNull(foundModel);
    }
    @Test
    void testPrimaryKeyUnique(){
        TestModelB model1 = new TestModelB();
        model1.save();

        TestModelB model11 = new TestModelB();
        model11.save();

        int id1 = model1.getId();
        int id11 = model11.getId();
        assertNotEquals(id1,id11);

        TestModelC model2 = new TestModelC();
        assertNull(model2.getId());
    }
}

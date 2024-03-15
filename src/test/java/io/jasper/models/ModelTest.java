package io.jasper.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {
    static class TestModel0 extends Model {

    }
    static class TestModel1 extends Model {

    }
    static class TestModel2 extends Model {

    }

    @Test
    void testCreateAndSaveModel(){
        TestModel0 model0 = new TestModel0();
        model0.save();
        assertNotEquals(0,model0.getId());
        assertEquals(1,model0.getId());
    }
    @Test
    void testFindById(){
        TestModel1 model1 = new TestModel1();
        model1.save();
//        TestModel1 foundModel = Model.findById(TestModel1.class,model1.getId());
//        assertEquals(model1.getId(),foundModel.getId());
    }
    @Test
    void testDeleteModel(){
        TestModel1 model1 = new TestModel1();
        model1.save();
        int id = model1.getId();
//        model1.delete();
//        TestModel1 foundModel = Model.findById(TestModel1.class,id);
//        assertNull(foundModel);
    }
    @Test
    void testPrimaryKeyUnique(){
        TestModel1 model1 = new TestModel1();
        model1.save();

        TestModel1 model11 = new TestModel1();
        model11.save();

        int id1 = model1.getId();
//        System.out.println("id1= "+ id1);
        int id11 = model11.getId();

        System.out.println("id2= "+ id11);
        assertNotEquals(id1,id11);

        TestModel2 model2 = new TestModel2();
        int id2 = model2.getId();
//        System.out.println("id3= "+ id2);
        assertEquals(1,id2);
    }
}

//package plic.tests;
//
//import org.junit.Assert;
//import org.junit.Test;
//import static org.junit.Assert.*;
//import plic.Exeption.DoubleException;
//import plic.repint.Entree;
//import plic.repint.Symbole;
//import plic.repint.TDS;
//
//public class TestTds {
//    @Test
//    public void testTds() throws DoubleException {
//        TDS tds = TDS.getInstance();
//
//        tds.ajouter(new Entree("i"), new Symbole("entier"));
//
//        assertTrue(tds.getDeclarationRequirement().size() == 1);
//    }
//
//    @Test
//    public void TestThrow() throws DoubleException {
//        TDS tds = TDS.getInstance();
//
//        tds.ajouter(new Entree("i"), new Symbole("entier"));
//
//        assertThrows(DoubleException.class, () -> {
//            tds.ajouter(new Entree("i"), new Symbole("entier"));
//        });
//    }
//}

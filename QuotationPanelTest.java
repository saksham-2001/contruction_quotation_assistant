import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class QuotationPanelTest {
    WallSegment wall;
    DrawingGUI guii = new DrawingGUI();
    QuotationPanel qp = new QuotationPanel(guii);
    Wall great;


    @Test
    void testsetInvoiceAmounts() {
        great = new Wall(true, true, Difficulty.normal);
        wall = new WallSegment(20, 1, 3, 180);
        great.addWallSegment(wall);
        qp.setInvoiceAmounts(200, great);
        assertEquals("$200", (qp.getSubTotal()).getText());
    }
    @Test
    void test1setInvoiceAmounts() {
        great = new Wall(true, true, Difficulty.normal);
        wall = new WallSegment(20, 1, 3, 180);
        great.addWallSegment(wall);
        qp.setInvoiceAmounts(200, great);
        assertEquals("yes", (qp.get_Location()).getText());

    }
    @Test
    void test2setInvoiceAmounts() {
        great = new Wall(false, true, Difficulty.normal);
        wall = new WallSegment(20, 1, 3, 180);
        great.addWallSegment(wall);
        qp.setInvoiceAmounts(200, great);
        assertEquals("no", (qp.get_Location()).getText());
    }

    @Test
    void test3setInvoiceAmounts() {
        great = new Wall(false, true, Difficulty.normal);
        wall = new WallSegment(20, 1, 3, 180);
        great.addWallSegment(wall);
        qp.setInvoiceAmounts(200, great);
        assertEquals("100", (qp.getTotalSleepers()).getText());
    }

    @Test
    void test4setInvoiceAmounts() {
        great = new Wall(false, true, Difficulty.normal);
        wall = new WallSegment(20, 1, 3, 180);
        great.addWallSegment(wall); //adding one wall segemnt
        qp.setInvoiceAmounts(200, great);
        assertEquals(100, qp.getSleeperCount());

    }

    @Test
    void test5setInvoiceAmounts() {
        great = new Wall(false, true, Difficulty.normal);
        wall = new WallSegment(20, 1, 3, 180);
        WallSegment w2 = new WallSegment(10, 1, 5, 180);
        great.addWallSegment(w2);
        great.addWallSegment(wall); //adding one wall segemnt
        qp.setInvoiceAmounts(200, great);
        assertEquals(175, qp.getSleeperCount());

    }

    @Test
    void test6setInvoiceAmounts() {
        great = new Wall(false, true, Difficulty.sandy);
        wall = new WallSegment(20, 1, 3, 180);
        great.addWallSegment(wall);
        qp.setInvoiceAmounts(200, great);
        assertEquals("sandy", (qp.getDifficulty()).getText());
    }

    @Test
    void test7setInvoiceAmounts() {
        great = new Wall(false, true, Difficulty.limestone);
        wall = new WallSegment(20, 1, 3, 180);
        great.addWallSegment(wall);
        qp.setInvoiceAmounts(200, great);
        assertEquals("limestone", (qp.getDifficulty()).getText());
    }

    @Test
    void test8setInvoiceAmounts() {
        great = new Wall(false, true, Difficulty.bluestone);
        wall = new WallSegment(20, 1, 3, 180);
        great.addWallSegment(wall);
        qp.setInvoiceAmounts(200, great);
        assertEquals("bluestone", (qp.getDifficulty()).getText());
    }
    @Test
    void test9setInvoiceAmounts() {
        great = new Wall(false, true, Difficulty.bluestone);
        qp.setInvoiceAmounts(200, great);
        assertEquals("yes", (qp.getAccess()).getText());

    }

    @Test
    void test10setInvoiceAmounts() {
        great = new Wall(false, false, Difficulty.bluestone);

        qp.setInvoiceAmounts(200, great);
        assertEquals("no", (qp.getAccess()).getText());

    }


    @Test
    void test1calculatepremiums() {
        great = new Wall(true, true, Difficulty.normal);
        wall = new WallSegment(20, 1, 3, 180);
        great.addWallSegment(wall);
        qp.calculatePremiums(200, great);
        assertEquals(0.0, qp.getLocationPremiumDouble());
    }



    @Test
    void test2calculatepremiuims() {
        great = new Wall(false, true, Difficulty.normal);
        wall = new WallSegment(20, 1, 3, 180);
        great.addWallSegment(wall);
        qp.calculatePremiums(200, great);
        assertEquals(10.0, qp.getLocationPremiumDouble());
    }


    @Test
    void test3calculatepremiums() {
        great = new Wall(false, true, Difficulty.normal);
        wall = new WallSegment(20, 1, 3, 180);
        great.addWallSegment(wall);
        qp.calculatePremiums(200, great);
        assertEquals(0, qp.getDifficultyPremiumDouble());
    }

    @Test
    void test4calculatepremiums() {
        great = new Wall(false, true, Difficulty.sandy);
        wall = new WallSegment(20, 1, 3, 180);
        great.addWallSegment(wall);
        qp.calculatePremiums(200, great);
        assertEquals(20.0, qp.getDifficultyPremiumDouble());
    }

    @Test
    void test5calculatepremiums() {
        great = new Wall(false, true, Difficulty.limestone);
        wall = new WallSegment(20, 1, 3, 180);
        great.addWallSegment(wall);
        qp.calculatePremiums(200, great);
        assertEquals(30.0, qp.getDifficultyPremiumDouble());
    }

    @Test
    void test6calculatepremiums() {
        great = new Wall(false, true, Difficulty.bluestone);
        wall = new WallSegment(20, 1, 3, 180);
        great.addWallSegment(wall);
        qp.calculatePremiums(200, great);
        assertEquals(60.0, qp.getDifficultyPremiumDouble());
    }


    @Test
    void test7calculatepremiums() {
        great = new Wall(false, true, Difficulty.bluestone);

        qp.setInvoiceAmounts(200, great);
        assertEquals(0.0, qp.getAccessPremiumDouble());

    }

    @Test
    void test8calculatepremiums() {
        great = new Wall(false, false, Difficulty.bluestone);

        qp.setInvoiceAmounts(200, great);
        assertEquals(0.0, qp.getAccessPremiumDouble());

    }

}


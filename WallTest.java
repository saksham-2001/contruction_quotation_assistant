import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WallTest {
WallSegment segment1 = new WallSegment(20,2,4,180);
    WallSegment segment2 = new WallSegment(40,1,5,180);
    Wall great = new Wall(true,true,Difficulty.normal);

    @Test
    void TestGetters()
    {
        assertAll("Getters",
            () -> assertEquals("yes",great.getLocal()),
            () -> assertEquals("yes",great.getAccess()),
            () -> assertEquals("normal",great.getDifficulty()));
    }

    @Test
    void TestSetters()
    {
        great.setLocal(false);
        great.setAccess(false);
        great.setDifficulty(Difficulty.limestone);
        assertAll("Setters",
                () -> assertEquals("no",great.getLocal()),
                () -> assertEquals("no",great.getAccess()),
                () -> assertEquals("limestone",great.getDifficulty()));
    }


    @Test
    void testgetsleepersinwall ()
    {
        great.addWallSegment(segment1);
assertEquals(150,great.getSleepersInWall());
    }
    @Test
    void test2getsleepersinwall ()
    {
        great.addWallSegment(segment1);
        great.addWallSegment(segment2);// adding two wall segments
        assertEquals(450,great.getSleepersInWall());
    }
    @Test
    void testgetnumberofsegments ()
    {
        great.addWallSegment(segment1);
        great.addWallSegment(segment2);
        assertEquals(2,great.getNumberOfSegments());
    }
    @Test
    void testgetsegment ()
    {
        great.addWallSegment(segment1);
        great.addWallSegment(segment2);
       assertAll(
               ()->  assertEquals(segment1,great.getSegment(0)) ,
        ()->assertEquals(segment2,great.getSegment(1)));
    }
    @Test
    void test1getsegment () // Index Out of Bound Exception
    {
        great.addWallSegment(segment1);
        great.addWallSegment(segment2);

             assertThrows(IndexOutOfBoundsException.class,()->great.getSegment(3));
    }
    @Test
    void test2getsegment ()
    {
        great.addWallSegment(segment1);


        assertEquals((segment1.getLength()),(great.getSegment(0)).getLength());
    }
@Test
    void test3getsegment ()
    {
        great.addWallSegment(segment1);


        assertEquals((segment1.getNumberOfBays()),(great.getSegment(0)).getNumberOfBays());
    }


}

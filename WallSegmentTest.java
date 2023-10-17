import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.*;

class WallSegmentTest {

    WallSegment wall;

    @Test
    void GettersTest()
    {
        wall = new WallSegment(20,1,3,180);

        assertAll("Getters",
            () -> assertEquals(20,wall.getLength()),
            () -> assertEquals(10,wall.getNumberOfBays()),
            () -> assertEquals(5,wall.getHeight(0)),
            () -> assertEquals(100,wall.getSleepersInWallSegment()));
    }

    @Test
    void testgetLength()

    {
        wall = new WallSegment(-20,1,3,180);
        assertEquals(0,wall.getLength());//length of the wall has to be a positive integer


    }
    @Test
    void test2getLength ()
    {
        wall = new WallSegment(200,1,3,180);
        assertEquals(200,wall.getLength());


    }
    @Test
    void test3getLength()

    {
        wall = new WallSegment(0,1,3,180);
        assertEquals(0,wall.getLength());


    }

    @Test
    void testgetNumberofBays ()
    {
        wall = new WallSegment(-5,1,3,180);
        assertEquals(0,wall.getNumberOfBays()); // wall cannot be constructed with a negative length.

    }
    @Test
    void test1getNumberofBays ()
    {
        wall = new WallSegment(10,1,3,180);
        assertEquals(5,wall.getNumberOfBays());

    }
    void test2getNumberofBays ()
    {
        wall = new WallSegment(10,1,4,180);
        assertEquals(5,wall.getNumberOfBays());// number of bays directly depends on the lenghth of the wall and not any other parameter.

    }
    @Test
    void test3getNumberofBays ()
    {
        wall = new WallSegment(9,1,3,180);
        assertEquals(5,wall.getNumberOfBays()); // number of bays is always an integer.

    }
    @Test
    void testgetHeight ()
    {
        wall = new WallSegment(-20,1,3,180);
     assertThrows(IndexOutOfBoundsException.class,()->wall.getHeight(1));// wall cannot be built with a negative height;
    }
    @Test
    void test2getHeight ()
    {
        wall = new WallSegment(20,1,3,180);
        assertThrows(IndexOutOfBoundsException.class,()->wall.getHeight(-1));// bay cannot be negative
    }
    @Test
    void test3getHeight () {
        wall = new WallSegment(20, 1, 3, 180);

        assertAll(

                () -> assertEquals(10, wall.getNumberOfBays()),
                () ->assertThrows(IndexOutOfBoundsException.class,()->wall.getHeight(10)),
                () ->assertThrows(IndexOutOfBoundsException.class,()->wall.getHeight(11)));// bay cannot be greater than or equal to the number of bays


    }
    @Test
    void testgetSleepersInWallSegment ()
    {
        wall = new WallSegment(5,1,3,180);
        assertEquals(35,wall.getSleepersInWallSegment());// sleepers cannot be a fractional value
    }
    @Test
    void test2getSleepersInWallSegment ()
    {
        wall = new WallSegment(-5,1,3,180);
        assertEquals(0,wall.getSleepersInWallSegment());// wall cannot be built with a negative length
    }
}
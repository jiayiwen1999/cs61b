package deque;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    public void testEmpty(){
        ArrayDeque<String> aryDq = new ArrayDeque<>();
        assertTrue("should be empty", aryDq.isEmpty());
        aryDq.addLast("last");
        aryDq.addFirst("first");
        assertEquals(2,aryDq.size());
        assertEquals("first",aryDq.get(0));
        assertEquals("last", aryDq.get(1));
    }

    @Test
    public void testResize(){
        ArrayDeque<Integer> intArray = new ArrayDeque<>();
        assertTrue("should be empty", intArray.isEmpty());
        for(int i =0; i< 20;i++){
            if(i%2 ==0){
                intArray.addFirst(i);
            }
            else{
                intArray.addLast(i);
            }
        }

        assertEquals(20,intArray.size());
        assertTrue("usage at least 25%", (float) intArray.size()/ intArray.length()>= 0.25);

        for(int i =0; i <15;i++){
            intArray.removeFirst();
        }
        assertEquals(5,intArray.size());
        assertTrue("usage at least 25%", (float) intArray.size()/ intArray.length()>= 0.25);
        for(int i =0; i< 20;i++){
            if(i%2 ==0){
                intArray.addFirst(i);
            }
            else{
                intArray.addLast(i);
            }
        }
        for(int i =0; i <15;i++){
            intArray.removeLast();
        }
        assertEquals(10,intArray.size());
        assertTrue("usage at least 25%", (float) intArray.size()/ intArray.length()>= 0.25);


    }
    @Test
    public void large (){
        ArrayDeque<Integer> intArray = new ArrayDeque<>();

        assertNull(intArray.removeFirst());
        assertNull(intArray.removeLast());
        for(int i =0;i<1000000;i++){
            intArray.addLast(i);
        }
        for(double i =0;i<500000;i++){
            assertEquals(i,(double)intArray.removeFirst(),0.0);
        }
        for(double i = 999999;i>=500000;i--){
            assertEquals(i,(double) intArray.removeLast(),0.0);
        }
    }
}

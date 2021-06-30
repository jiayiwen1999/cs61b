package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {

    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> aListNoResizing = new AListNoResizing<>();
        BuggyAList<Integer> buggyAList = new BuggyAList<>();
        aListNoResizing.addLast(1);
        aListNoResizing.addLast(2);
        aListNoResizing.addLast(3);
        buggyAList.addLast(1);
        buggyAList.addLast(2);
        buggyAList.addLast(3);

        assertEquals(aListNoResizing.size(), buggyAList.size());

        assertEquals(aListNoResizing.removeLast(), buggyAList.removeLast());
        assertEquals(aListNoResizing.removeLast(), buggyAList.removeLast());
        assertEquals(aListNoResizing.removeLast(), buggyAList.removeLast());
    }

    @Test
    public void ramdomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
                assertEquals(L.size(),B.size());

            } else if (operationNumber == 1 && L.size()>0) {
                // size
                assertEquals(L.getLast(),B.getLast());

            }
            else if(operationNumber ==2 & L.size()>0){
                assertEquals(L.removeLast(),B.removeLast());
            }

        }
    }
}

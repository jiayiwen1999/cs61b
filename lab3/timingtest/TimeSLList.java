package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        AList<Integer> counts = new AList<>();
        int construct =100000;
        AList<Double> time = new AList<>();
        for(int i = 0; i<8;i++){
            counts.addLast(construct);
            SLList<Integer> list = repeatNTimes(construct);
            Stopwatch sw = new Stopwatch();
            list.getLast();
            double timeInSec = sw.elapsedTime();
            System.out.println(timeInSec);
            time.addLast(timeInSec);
            construct *= 2;
        }
        printTimingTable(counts,time,counts);
    }

    public static SLList<Integer> repeatNTimes(int n){
        SLList<Integer> newList = new SLList<>();
        for (int i =0; i< n ;i++){
            newList.addLast(1);

        }
        return newList;
    }

}

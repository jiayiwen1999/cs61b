package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
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
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        AList<Integer> counts = new AList<>();
        int construct =1000000;
        AList<Double> time = new AList<Double>();
        for(int i = 0; i<8;i++){
            counts.addLast(construct);
            Stopwatch sw = new Stopwatch();
            repeatNTimes(construct);
            double timeInSec = sw.elapsedTime();
            time.addLast(timeInSec);
            construct *= 2;
        }
        printTimingTable(counts,time,counts);
    }
    private static void repeatNTimes(int n){
        AList<Integer> newList = new AList<Integer>();
        for (int i =0; i< n ;i++){
            newList.addLast(1);
        }
    }
}

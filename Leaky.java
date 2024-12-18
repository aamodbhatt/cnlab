import java.util.Random;
import java.util.Scanner;

public class Leaky {
    public static int bsize = 0, packet, tgen, j = 1;
    public static String stop;
    public static final int bmax = 1024; // Maximum bucket size
    public static final int orate = 100; // Output rate
    public static final int delay = 1500; // Delay in milliseconds
    public static Random r = new Random(); // Random for packet size
    public static Random t = new Random(); // Random for time generation

    public class Generating extends Thread {
        public void run() {
            while (stop == null) {
                tgen = t.nextInt(3000); // Random time interval
                packet = r.nextInt(512); // Random packet size

                if (bsize + packet < bmax) {
                    bsize = bsize + packet;
                    System.out.printf("%13d%10d%15d%20d\n", j++, packet, bsize, bmax - bsize);
                } else {
                    System.out.println("Bucket Overflow, " + packet + " size of packet discarded");
                }

                try {
                    Thread.sleep(tgen);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class Leaking extends Thread {
        public void run() {
            while (true) {
                if (bsize > 0 && bsize - orate > 0) { // Output packet rate is 100 bytes
                    bsize = bsize - orate;
                    System.out.printf("%38d%20d%15d\n", bsize, (bmax - bsize), orate);
                } else {
                    System.out.printf("%38d%20d%15d\n", 0, bmax, bsize);
                    bsize = 0;
                    if (stop != null) {
                        return;
                    }
                }

                try {
                    Thread.sleep(delay);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Leaky le = new Leaky();
        Scanner in = new Scanner(System.in);

        Generating g = le.new Generating();
        Leaking l = le.new Leaking();

        System.out.println("Started");
        System.out.println("Output Rate is: " + orate + "\nAnd it is flowing at interval: " + ((float) delay / 1000) + " sec");
        System.out.println("Enter any key to stop input");
        System.out.printf("Packet Number | Input Packet | Bucket Filled | Remaining Space | Output Rate\n");
        System.out.println();

        g.start();
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        l.start();

        stop = in.next();
        in.close();
    }
}


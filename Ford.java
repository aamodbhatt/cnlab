import java.util.*;

public class Ford {
    private int D[];
    private int num_ver;
    public static final int MAX_VALUE = 999;

    public Ford(int num_ver) {
        this.num_ver = num_ver;
        D = new int[num_ver + 1];
    }

    public void BellmanFordEvaluation(int source, int A[][]) {
        for (int node = 1; node <= num_ver; node++) {
            D[node] = MAX_VALUE;
        }

        D[source] = 0;

        for (int node = 1; node <= num_ver - 1; node++) {
            for (int sn = 1; sn <= num_ver; sn++) {
                for (int dn = 1; dn <= num_ver; dn++) {
                    if (A[sn][dn] != MAX_VALUE) {
                        if (D[dn] > D[sn] + A[sn][dn]) {
                            D[dn] = D[sn] + A[sn][dn];
                        }
                    }
                }
            }
        }

        for (int sn = 1; sn <= num_ver; sn++) {
            for (int dn = 1; dn <= num_ver; dn++) {
                if (A[sn][dn] != MAX_VALUE) {
                    if (D[dn] > D[sn] + A[sn][dn]) {
                        System.out.println("The graph contains a negative edge cycle.");
                        return;
                    }
                }
            }
        }

        for (int vertex = 1; vertex <= num_ver; vertex++) {
            System.out.println("Distance from source " + source + " to vertex " + vertex + " is " + D[vertex]);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of vertices:");
        int num_ver = sc.nextInt();

        int A[][] = new int[num_ver + 1][num_ver + 1];
        System.out.println("Enter the adjacency matrix:");

        for (int sn = 1; sn <= num_ver; sn++) {
            for (int dn = 1; dn <= num_ver; dn++) {
                A[sn][dn] = sc.nextInt();
                if (sn == dn) {
                    A[sn][dn] = 0;
                } else if (A[sn][dn] == 0) {
                    A[sn][dn] = MAX_VALUE;
                }
            }
        }

        System.out.println("Enter the source vertex:");
        int source = sc.nextInt();

        Ford b = new Ford(num_ver);
        b.BellmanFordEvaluation(source, A);
        sc.close();
    }
}

// Java program for implementation of Push-relabel
// Modified from https://sites.google.com/site/indy256/algo/preflow
package javaport;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.LinkedList;

class MaxFlowCalculator2 {

    public int[][] graph;
    public int[][] f;
    public int V;
    public int totalFlow;

    public MaxFlowCalculator2(int[][] graph, int s, int t) {
        this.graph = graph;
        this.V = graph.length;

        this.totalFlow = maxFlow(s, t);
    }

    int flow(int s, int t) {
        return f[s][t];
    }

    public int maxFlow(int s, int t) {
        int n = graph.length;

        int[] h = new int[n];
        h[s] = n - 1;

        int[] maxh = new int[n];

        f = new int[n][n];
        int[] e = new int[n];

        for (int i = 0; i < n; ++i) {
            f[s][i] = graph[s][i];
            f[i][s] = -f[s][i];
            e[i] = graph[s][i];
        }

        for (int sz = 0;;) {
            if (sz == 0) {
                for (int i = 0; i < n; ++i) {
                    if (i != s && i != t && e[i] > 0) {
                        if (sz != 0 && h[i] > h[maxh[0]]) {
                            sz = 0;
                        }
                        maxh[sz++] = i;
                    }
                }
            }
            if (sz == 0) {
                break;
            }
            while (sz != 0) {
                int i = maxh[sz - 1];
                boolean pushed = false;
                for (int j = 0; j < n && e[i] != 0; ++j) {
                    if (h[i] == h[j] + 1 && graph[i][j] - f[i][j] > 0) {
                        int df = Math.min(graph[i][j] - f[i][j], e[i]);
                        f[i][j] += df;
                        f[j][i] -= df;
                        e[i] -= df;
                        e[j] += df;
                        if (e[i] == 0) {
                            --sz;
                        }
                        pushed = true;
                    }
                }
                if (!pushed) {
                    h[i] = Integer.MAX_VALUE;
                    for (int j = 0; j < n; ++j) {
                        if (h[i] > h[j] + 1 && graph[i][j] - f[i][j] > 0) {
                            h[i] = h[j] + 1;
                        }
                    }
                    if (h[i] > h[maxh[0]]) {
                        sz = 0;
                        break;
                    }
                }
            }
        }

        int flow = 0;
        for (int i = 0; i < n; i++) {
            flow += f[s][i];
        }

        return flow;
    }

}

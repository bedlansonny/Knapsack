import java.io.*;
import java.util.*;

public class Knapsack
{
    static int[] w; //item weights
    static int[] v; //item values
    static int[][] bv; //memoized best carried value for a given [item index][total knapsack capacity]

    public static void main(String args[]) throws IOException
    {
        Scanner in = new Scanner(new File("truck.dat"));

        int c = in.nextInt();
        for(int ci = 0; ci < c; ci++)
        {
            int t = in.nextInt(); //carrying capacity

            int n = in.nextInt(); //number of items
            w = new int[n];
            v = new int[n];
            for(int ni = 0; ni < n; ni++)
            {
                w[ni] = in.nextInt();
                v[ni] = in.nextInt();
            }

            bv = new int[n][t+1];
            for(int ni = 0; ni < n; ni++)
            {
                for(int ti = 1; ti < t+1; ti++)
                {
                    bv[ni][ti] = -1;
                }
            }

            //System.out.println(bestRM(n-1,t));
            System.out.println(bestDP());
        }


    }

    static int bestRM(int i, int t) //using recursion with memoization
    {
        if(bv[i][t] == -1)
        {
            if(t < w[i])
            {
                if(i == 0)
                    bv[i][t] = 0;
                else
                    bv[i][t] = bestRM(i-1, t);
            }
            else
            {
                if(i == 0)
                    bv[i][t] = bestRM(i,t - w[i]) + v[i];
                else
                    bv[i][t] = Math.max(bestRM(i,t - w[i]) + v[i], bestRM(i-1, t));
            }
        }
        return bv[i][t];
    }

    static int bestDP() //using dynamic programming
    {
        //first row
        for(int t = 1; t < bv[0].length; t++)
        {
            if(t < w[0])
                bv[0][t] = 0;
            else
                bv[0][t] = bv[0][t - w[0]] + v[0];
        }

        //other rows
        for(int i = 1; i < bv.length; i++)
        {
            for(int t = 1; t < bv[0].length; t++)
            {
                if(t < w[i])
                    bv[i][t] = bv[i-1][t];
                else
                    bv[i][t] = Math.max(bv[i][t-w[i]] + v[i], bv[i-1][t]);
            }
        }

        return bv[bv.length-1][bv[0].length-1];
    }
}

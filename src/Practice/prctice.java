package Practice;
import java.util.*;
import java.io.*;

public class prctice {
    public static void main(String[] args) {
        String a = "DIV 5 0/";
        StringTokenizer st = new StringTokenizer(a);
        int i=0;
        while (st.hasMoreTokens()) {
            i++;
            System.out.println(st.nextToken());

        }
        System.out.println("i : " + i);

    }
}

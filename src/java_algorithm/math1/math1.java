package java_algorithm.math1;
import java.util.*;
public class math1 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        long a,b,c;
        a = scan.nextInt();
        b = scan.nextInt();
        c = scan.nextInt();
        long i=1;
        if(c<=b)
            System.out.println("-1");
        else
        {

            System.out.println((a/(c-b))+1);
        }
    }
}

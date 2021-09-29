package java_algorithm.recursive.recursive1;
import java.util.Scanner;



public class factorial {
    public static int recur( int number)
    {
        if(number==0)
            return 1;
        return number*recur(number-1);

    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int num;
        num=input.nextInt();
        System.out.println( recur(num));


    }
}

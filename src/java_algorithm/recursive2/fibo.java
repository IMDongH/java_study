package java_algorithm.recursive2;
import java.util.Scanner;
public class fibo {

    public static int recur(int num)
    {
        if(num == 0)
            return 0;
        if(num ==1 )
            return 1;
        return recur(num-1) + recur(num-2);
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int num;
        num = input.nextInt();

        System.out.println(recur(num));

    }

}

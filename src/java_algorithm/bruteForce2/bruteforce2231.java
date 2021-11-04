package java_algorithm.bruteForce2;
import java.util.*;
public class bruteforce2231 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int num = 0;
        int min =0;
        int ten = 1;
        int num1 = 0;
        int num2 = 0;
        int number = 0;
        num = scan.nextInt();
        number = num;
        while (true) {
            num = num / 10;
            ten = ten * 10;
            if (num / 10 == 0)
                break;
        }
        if (number < 14) {

        } else {
            for (int i = 14; i < number; i++) {
                while (ten != 0) {

                }

            }

        }
        System.out.println(min);
    }
}
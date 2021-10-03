package java_algorithm.greedy1;
import java.util.*;
public class greedy11047 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int number=0;
        int sum =0;
        int min=0;
        number = scan.nextInt();
        sum=scan.nextInt();
        int coin[]= new int[number];

        for(int i=0; i<number; i++)
        {
            coin[i]=scan.nextInt();
        }


            for(int i=number-1; i>=0; i--)
            {
                if(sum/coin[i]>0)
                {
                    min = min + sum/coin[i];
                    sum = sum-(coin[i]*(sum/coin[i]));

                    if(sum==0)
                        break;
                }
            }

        System.out.println(min);
    }
}

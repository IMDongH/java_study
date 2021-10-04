package java_algorithm.bruteForce1;
import java.util.*;
public class bruteforce2798 {
    public static void main(String[] args) {


    Scanner input = new Scanner(System.in);

    int num=0;
    int max =0;
    int maxN=0;
    int sum=0;
    num = input.nextInt();
    max = input.nextInt();

    int []card = new int[num];
    for(int i=0; i<num; i++)
    {
        card[i]=input.nextInt();
    }
    for(int i=0; i<num-1; i++)
    {
        for(int j=0; j<num-i-1; j++)
        {
            if(card[j]<card[j+1]) {
                int temp;
                temp = card[j];
                card[j]= card[j+1];
                card[j+1]=temp;
            }
        }
    }

    for(int i=0; i<num-2; i++)
    {
        for(int j=i+1; j<num-1; j++)
        {
            for(int k=j+1; k<num; k++)
            {
                sum = card[i]+card[j]+card[k];
                if(sum<=max)
                {
                 if(sum>maxN)
                 {
                     maxN = sum;
                 }
                }
            }
        }
    }
        System.out.println(maxN);

}}

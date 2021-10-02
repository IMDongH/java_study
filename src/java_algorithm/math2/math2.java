package java_algorithm.math2;
import java.util.*;
public class math2 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int num,count=1,i=0;
        num = scan.nextInt();

        while(true)
        {
            if(num ==1)
            {
                break;
            }
            else
            {
                if((i-1)*6-1<=num&&num<=i*6+1)
                {

                    break;
                }
            }
            i++;
            count ++;
        }

        System.out.println(count);
    }
}

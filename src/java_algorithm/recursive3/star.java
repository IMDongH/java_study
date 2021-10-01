package java_algorithm.recursive3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
public class star {
    static char list[][];


    public static void recur(int row,int cul,int num, boolean blank)
    {
         if(blank)
         {
             for(int i=row; i<row+num; i++)
             {
                 for( int j=cul; j<cul+num; j++)
                 {
                     list[i][j]=' ';
                 }
             }
             return;
         }

         if(num==1)
         {
             list[row][cul]='*';
             return;
         }
         int size=num/3;
         int count=0;
         for(int i=row; i<row+num; i+=size)
         {
             for(int j=cul; j<cul+num; j+=size)
             {
                 count++;
                 if(count==5)
                 {
                     recur(i,j,size,true);
                 }
                 else
                 {
                     recur(i,j,size,false);
                 }
             }
         }
    }


    public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out));
        int num ;
        num=Integer.parseInt(br.readLine());
        list = new char[num][num];

        recur(0,0,num,false);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                sb.append(list[i][j]);
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }
}

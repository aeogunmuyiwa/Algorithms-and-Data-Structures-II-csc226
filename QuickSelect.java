/**
 *
 * @author Rahnuma Islam Nishat
 * September 19, 2018
 * CSC 226 - Fall 2018
 * Adebayo E. Ogunmuyiwa
 */
import java.util.*;
import java.io.*;

public class QuickSelect {


    public static int QuickSelect(int[] A, int k) {
        //Write your code here
        if(A.length == 1){
            return A[0];
        }
        if(A.length < k){
            return -1;
        }
        return select(A, 0 , A.length-1, k);

    }
    public static int select(int []A, int left,int right, int k){
        if ( left >= right){
            return A[left];
        }
        int random_pivot = pivot(left , right);
        int pivot_Index = partition(A, left, right,random_pivot);
        if( k <=pivot_Index ){
            return select(A,left,pivot_Index-1,k);
        }
        else if(k == pivot_Index + 1){
            return A[pivot_Index];
        }
        else{
            return select( A, pivot_Index+1,right,k);
        }
    }
    public static void swap(int []A, int left , int right){
        int temp = A[left];
        A[left] = A[right];
        A[right] = temp;
    }
    public static int pivot(int left, int right){
        int answer = left + (int)Math.floor(Math.random()*(right - left +1));
        System.out.println(answer);
        return answer;

    }
    public static int partition(int []A, int left, int right, int random_pivot){
        int pivot_Value = A[random_pivot] ;
        swap(A,random_pivot,right);
        int storeIndex = left;
        for(int i = left ; i < right ; i++){
            if(A[i] < pivot_Value ){
                swap(A,storeIndex,i);
                storeIndex++;
            }
        }
        swap(A,right,storeIndex);

        return storeIndex;
    }
    public static void main(String[] args) {
        Scanner s;
        int[] array;
        int k;
        if(args.length > 0) {
        try{
        s = new Scanner(new File(args[0]));
        int n = s.nextInt();
        array = new int[n];
        for(int i = 0; i < n; i++){
            array[i] = s.nextInt();
        }
        } catch(java.io.FileNotFoundException e) {
        System.out.printf("Unable to open %s\n",args[0]);
        return;
        }
        System.out.printf("Reading input values from %s.\n", args[0]);
        }
    else {
        s = new Scanner(System.in);
        System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
        int temp = s.nextInt();
        ArrayList<Integer> a = new ArrayList();
        while(temp >= 0) {
        a.add(temp);
        temp=s.nextInt();
        }
        array = new int[a.size()];
        for(int i = 0; i < a.size(); i++) {
        array[i]=a.get(i);
        }

        System.out.println("Enter k");
        }
        k = s.nextInt();
        System.out.println("The " + k + "th smallest number is the list is "
               + QuickSelect(array,k));
    }
}

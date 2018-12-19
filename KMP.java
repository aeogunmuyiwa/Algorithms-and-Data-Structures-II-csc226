/*
   (originally from R.I. Nishat - 08/02/2014)
   (revised by N. Mehta - 11/7/2018)
   Adebayo Ogunmuyiwa
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.*;


public class KMP{
    private String pattern;
    private int [][] dfa;
    private int length_pattern;
    private int radix;




    public KMP(String pattern){
      this.pattern = pattern;
      this.length_pattern = pattern.length();
      this.radix = 256;
      this.dfa = new int[radix][length_pattern];
      dfa[pattern.charAt(0)][0] = 1 ;
      kmp_process(pattern);

    }
    public void kmp_process(String pattern){
        for(int x = 0 , j = 1 ; j < length_pattern; j ++){
          for(int c = 0 ; c < radix ; c ++){
            dfa[c][j]= dfa[c][x];
          }
          dfa[pattern.charAt(j)][j] = j +1;
          x = dfa [pattern.charAt(j)][x];

      }
    }

    public int search(String txt){

     // simulate operation of DFA on text
        int m = pattern.length();
        int n = txt.length();
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) {
            j = dfa[txt.charAt(i)][j];
        }
        if (j == m) return i - m;    // found
        return n;

    }


    public static void main(String[] args) throws FileNotFoundException{
  Scanner s;
  if (args.length > 0){
      try{
    s = new Scanner(new File(args[0]));
      }
      catch(java.io.FileNotFoundException e){
    System.out.println("Unable to open "+args[0]+ ".");
    return;
      }
      System.out.println("Opened file "+args[0] + ".");
      String text = "";
      while(s.hasNext()){
    text += s.next() + " ";
      }

      for(int i = 1; i < args.length; i++){
    KMP k = new KMP(args[i]);
    int index = k.search(text);
    if(index >= text.length()){
        System.out.println(args[i] + " was not found.");
    }
    else System.out.println("The string \"" + args[i] + "\" was found at index " + index + ".");
      }
  }
  else{
      System.out.println("usage: java SubstringSearch <filename> <pattern_1> <pattern_2> ... <pattern_n>.");
  }
    }
}

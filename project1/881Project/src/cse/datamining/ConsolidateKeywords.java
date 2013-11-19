package cse.datamining;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class ConsolidateKeywords {

   private static final String USER_KW = "data/user_key_word.txt";
   private static final String COUNTS   = "data/user_key_word_counts.txt";
   
   public static void main ( String argv[] ) throws IOException {
      BufferedReader snsReader = new BufferedReader( new FileReader( USER_KW ) );
      FileWriter     writer    = new FileWriter( COUNTS );
      
      String line         = snsReader.readLine();
      
      System.out.printf( "Aggregating keywords '%s' -> '%s'\n", USER_KW, COUNTS );
      
      int usersProcessed = 0;
      while ( line != null ) {
         StringTokenizer tokenizer = new StringTokenizer( line ); 
         String currentUser  = tokenizer.nextToken();
         
         int numKeywords = new StringTokenizer( tokenizer.nextToken(), ";" ).countTokens();
         
         writer.write( String.format( "%s\t%d\n", currentUser, numKeywords ) );
         
         usersProcessed++;
         System.out.printf( "\rUsers processed: %d", usersProcessed );
         
         line = snsReader.readLine();
      }
      
      System.out.println( "\nDone" );
      
      snsReader.close();
      writer.close();
   }
   
}

package cse.datamining;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class ConsolidateTags {

   private static final String USER_PROF = "data/user_profile.txt";
   private static final String COUNTS   = "data/user_profile_tagcounts.txt";
   
   public static void main ( String argv[] ) throws IOException {
      BufferedReader reader = new BufferedReader( new FileReader( USER_PROF ) );
      FileWriter     writer    = new FileWriter( COUNTS );
      
      String line         = reader.readLine();
      
      System.out.printf( "Aggregating tags '%s' -> '%s'\n", USER_PROF, COUNTS );
      
      int usersProcessed = 0;
      while ( line != null ) {
         StringTokenizer tokenizer = new StringTokenizer( line ); 
         String currentUser  = tokenizer.nextToken();
         String year         = tokenizer.nextToken();
         String gender       = tokenizer.nextToken();
         String numTweets    = tokenizer.nextToken();
         
         int numTags = new StringTokenizer( tokenizer.nextToken(), ";" ).countTokens();
         
         writer.write( String.format( "%s\t%s\t%s\t%s\t%d\n", currentUser, year, gender, numTweets, numTags ) );
         
         usersProcessed++;
         System.out.printf( "\rUsers processed: %d", usersProcessed );
         
         line = reader.readLine();
      }
      
      System.out.println( "\nDone" );
      
      reader.close();
      writer.close();
   }
   
}

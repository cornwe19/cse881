package cse.datamining;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class ConsolidateFollowing {

   private static final String USER_SNS = "data/user_sns.txt";
   private static final String COUNTS   = "data/user_sns_counts.txt";
   
   public static void main ( String argv[] ) throws IOException {
      BufferedReader snsReader = new BufferedReader( new FileReader( USER_SNS ) );
      FileWriter     writer    = new FileWriter( COUNTS );
      
      String line         = snsReader.readLine();
      String currentUser  = new StringTokenizer( line ).nextToken();
      int    currentCount = 0;
      
      System.out.printf( "Aggregating followers '%s' -> '%s'\n", USER_SNS, COUNTS );
      
      int usersProcessed = 0;
      while ( line != null ) {
         StringTokenizer t = new StringTokenizer( line );
         
         String user = t.nextToken();
         if ( currentUser.equals( user ) ) {
            currentCount++;
         } else {
            writer.write( String.format( "%s\t%d\n", currentUser, currentCount ) );
            currentUser = user;
            currentCount = 0;
            
            usersProcessed++;
            System.out.printf( "\rUsers processed: %d", usersProcessed );
         }
         
         line = snsReader.readLine();
      }
      
      System.out.println( "\nDone" );
      
      snsReader.close();
      writer.close();
   }
   
}

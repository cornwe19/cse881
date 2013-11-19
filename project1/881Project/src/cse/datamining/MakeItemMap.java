package cse.datamining;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class MakeItemMap {

   public static void main( String[] argv ) throws IOException {
      BufferedReader reader = new BufferedReader( new FileReader( "data/item.txt" ) );
      BufferedReader keys   = new BufferedReader( new FileReader( "data/topKeywords.txt" ) );
      FileWriter     mapWriter = new FileWriter( "data/item_keyword_map.txt" );
      
      
      List<String> keywords = Arrays.asList( keys.readLine().split( "\t" ) );
      int linesProcessed = 0;
      
      System.out.println( "Saving out item maps" );
      
      String line = reader.readLine();
      while ( line != null ) {
         StringTokenizer t = new StringTokenizer( line );
         String item = t.nextToken();
         t.nextToken();
         
         int[] mapping = new int[100];
         Arrays.fill( mapping, -1 );
         StringTokenizer itemWords = new StringTokenizer( t.nextToken(), ";" );
         while ( itemWords.hasMoreTokens() ) {
            int place = keywords.indexOf( itemWords.nextToken() );
            if ( place >= 0 ) {
               mapping[place] = 1;
            }
         }
         
         // Print out item line
         mapWriter.write( item );
         mapWriter.append( '\t' );
         for ( int i = 0; i < 100; i++ ) {
            mapWriter.write( Integer.toString( mapping[i] ) );
            mapWriter.write( '\t' );   
         }
         mapWriter.write( '\n' );
         
         linesProcessed++;
         System.out.printf( "\rLines processed %d", linesProcessed );
         
         line = reader.readLine();
      }
      
      System.out.println( "\nDone" );
      
      reader.close();
      keys.close();
      mapWriter.close();
   }
   
   
}

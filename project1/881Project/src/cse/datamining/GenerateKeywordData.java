package cse.datamining;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public class GenerateKeywordData {

   private static final String ITEM_KEYWORDS = "data/item.txt";
   private static final HashMap<String,Integer> sWordMap = new HashMap<String,Integer>();
   
   public static void main( String[] argv ) throws IOException {
      BufferedReader reader = new BufferedReader( new FileReader( ITEM_KEYWORDS ) );
      
      System.out.println( "Scanning for keyword array size" );
      
      int linesComplete = 0;
      
      String line = reader.readLine();
      while( line != null ) {
         StringTokenizer t = new StringTokenizer( line );
         t.nextToken();
         t.nextToken();
         
         StringTokenizer keyTok = new StringTokenizer( t.nextToken(), ";" );
         String keywordId = keyTok.nextToken();
         while ( keyTok.hasMoreTokens() ) {
            Integer count = sWordMap.get( keywordId );
            if ( count != null ) {
               sWordMap.put( keywordId, count + 1 );
            } else {
               sWordMap.put( keywordId, 1 );
            }
            
            keywordId = keyTok.nextToken();
         }
         
         linesComplete++;
         System.out.printf( "\rProcessed %d lines", linesComplete );
         
         line = reader.readLine();
      }
      
      LinkedList<Entry<String,Integer>> top100 = new LinkedList<Entry<String,Integer>>();
      
      for ( Entry<String,Integer> entry : sWordMap.entrySet() ) {
         if ( top100.isEmpty() ) {
            top100.add( entry );
         } else if ( top100.size() < 100 ) {
            addAscending( top100, entry );
         } else if ( entry.getValue() > top100.get( 0 ).getValue() ) {
            addAscending( top100, entry );
            top100.removeFirst();
         }
      }
      
      FileWriter writer = new FileWriter( "data/topKeywords.txt" );
      
      for ( Entry<String,Integer> entry : top100 ) {
         writer.write( entry.getKey() );
         writer.append( "\t" );
      }
      writer.append( "\n" );
      
      System.out.printf( "\n%d keywords found\n", sWordMap.size() );
      
      reader.close();
      writer.close();
   }
 
   private static void addAscending( LinkedList<Entry<String,Integer>> list, Entry<String,Integer> entry ) {
      for ( int i = 0; i < list.size(); i++ ) {
         if ( entry.getValue() < list.get( i ).getValue() ) {
            list.add( i, entry );
            return;
         }
      }
      
      list.add( entry );
   }
   
}

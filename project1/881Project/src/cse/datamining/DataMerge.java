package cse.datamining;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class DataMerge {
   private static HashMap<Integer, ArrayList<String>> sTrainingData = new HashMap<Integer, ArrayList<String>>(); 
   
   public static void main( String[] argv ) throws IOException {
      if ( argv.length < 3 ) {
         System.err.printf( "Usage: %s TRAINING_FILE MERGE_FILE OUTPUT [OUT_HEADER]\n", DataMerge.class.getName() );
         return;
      }
      
      BufferedReader trainReader = new BufferedReader( new FileReader( argv[0] ) );
      FileWriter       writer = new FileWriter( argv[2] );
      String inline = trainReader.readLine();
      int lines = 0; 
      
      for ( int i = 0; i < 103; i++ ) {
         if ( i < 100 ) {
            writer.write( String.format( "%d\t", i ) );
         } else {
            switch ( i ) {
            case 100:
               writer.write( "ITEM\t" );
               break;
            case 101:
               writer.write( "RESULT\t" );
               break;
            case 102:
               writer.write( "TIMESTAMP\n" );
               break;
            }
         }
      }
      
      BufferedReader keyReader = new BufferedReader( new FileReader( "data/topKeywords.txt" ) );
      List<String> keywords = Arrays.asList( keyReader.readLine().split( "\t" ) );
      keyReader.close();
      
      HashMap<String,Integer[]> items = new HashMap<String,Integer[]>();
      BufferedReader itemReader = new BufferedReader( new FileReader( "data/item_keyword_map.txt" ) );
      String item = itemReader.readLine();
      while( item != null ) {
         Scanner itemScan = new Scanner( item );
         
         String id = itemScan.next();
         
         Integer[] ints = new Integer[100];
         for ( int i = 0; i < 100; i++ ) {
            ints[i] = itemScan.nextInt();
         }
         
         items.put( id, ints );
         item = itemReader.readLine();
      }
      itemReader.close();
      
      System.out.printf( "Scanning '%s'\n", argv[0] );
      while ( inline != null ) {         
         while ( inline != null ) {
            Scanner scanner = new Scanner( inline );
            
            int uid = scanner.nextInt();
            if ( sTrainingData.containsKey( uid ) ) {
               sTrainingData.get( uid ).add( scanner.nextLine() );
            } else {
               ArrayList<String> userRecords = new ArrayList<String>();
               userRecords.add( scanner.nextLine() );
               sTrainingData.put( uid, userRecords );
            }
            
            inline = trainReader.readLine();
            
            // Process it all, clear it and try again
            if ( sTrainingData.size() > 100000 ) {
               System.out.print( "\nChunk read, processing" );
               break;
            }
                       
            lines++;
            System.out.printf( "\r%d lines read", lines );
         }
         
         System.out.printf( "\nMerging with '%s' and saving to '%s'\n", argv[1], argv[2] );
         
         BufferedReader   mergeReader = new BufferedReader( new FileReader( argv[1] ) );
         
         if ( argv.length > 3 ) {
            writer.write( argv[3] + "\n" );
         }
         
         lines = 0;
         int saved = 0;
         String mline = mergeReader.readLine();
         while( mline != null ) {
            Scanner scanner = new Scanner( mline );
            
            ArrayList<String> recs = sTrainingData.get( scanner.nextInt() );
            
            if ( recs != null ) {
               
               float[] weights = new float[100]; 
               StringTokenizer tok = new StringTokenizer( scanner.next(), ":;" );
               while ( tok.hasMoreTokens() ) {
                  int place = keywords.indexOf( tok.nextToken() );
                  if ( place >= 0 ) {
                     weights[place] = Float.parseFloat( tok.nextToken() );
                  }
               }
               
               for ( String rec : recs ) {
                  StringTokenizer recTok = new StringTokenizer( rec );
                  Integer[] itemKeys = items.get( recTok.nextToken() );
                  for ( int i = 0; i < 100; i++ ) {
                     writer.write( Float.toString( weights[i] * itemKeys[i] ) );
                     
                     if ( i < 99 ) { // Skip trailing tab
                        writer.append( '\t' );
                     }
                  }
                  
                  writer.write( String.format( "%s\n", rec ) );
                  saved++;
               }
            }
            
            mline = mergeReader.readLine();
            lines++;
            
            System.out.printf( "\r%d lines read %d lines saved", lines, saved );
         }
         mergeReader.close();
      
         System.out.println( "\nChunk processed" );
         
         sTrainingData.clear();
      }
      
      System.out.println( "\nDone" );
      
      trainReader.close();
      writer.close();
   }
}

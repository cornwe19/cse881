package cse.datamining;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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
               for ( String rec : recs ) {
                  writer.write( String.format( "%s%s\n", mline, rec ) );
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

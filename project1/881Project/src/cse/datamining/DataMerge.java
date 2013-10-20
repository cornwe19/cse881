package cse.datamining;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class DataMerge {
   private static HashMap<Integer, String> sTrainingData = new HashMap<Integer, String>(); 
   
   public static void main( String[] argv ) throws IOException {
      if ( argv.length < 3 ) {
         System.err.printf( "Usage: %s TRAINING_FILE MERGE_FILE OUTPUT [OUT_HEADER]\n", DataMerge.class.getName() );
         return;
      }
      
      BufferedReader trainReader = new BufferedReader( new FileReader( argv[0] ) );
      
      System.out.printf( "Scanning '%s'\n", argv[0] );
      
      int lines = 0; 
      String line = trainReader.readLine();
      while ( line != null ) {
         Scanner scanner = new Scanner( line );
         
         sTrainingData.put( scanner.nextInt(), scanner.nextLine() );
         
         line = trainReader.readLine();
         
         lines++;
         System.out.printf( "\r%d lines read", lines );
      }
      
      trainReader.close();
      
      System.out.printf( "\nMerging with '%s' and saving to '%s'\n", argv[1], argv[2] );
      
      BufferedReader   profileReader = new BufferedReader( new FileReader( argv[1] ) );
      FileWriter       writer = new FileWriter( argv[2] );
      
      if ( argv.length > 3 ) {
         writer.write( argv[3] + "\n" );
      }
      
      lines = 0;
      int saved = 0;
      line = profileReader.readLine();
      while( line != null ) {
         Scanner scanner = new Scanner( line );
         
         String rec = sTrainingData.get( scanner.nextInt() );
         if ( rec != null ) {
            writer.write( String.format( "%s%s\n", line, rec ) );
            saved++;
         }
         
         line = profileReader.readLine();
         lines++;
         
         System.out.printf( "\r%d lines read %d lines saved", lines, saved );
      }
      
      System.out.println( "\nDone" );
      
      profileReader.close();
      writer.close();
   }
}
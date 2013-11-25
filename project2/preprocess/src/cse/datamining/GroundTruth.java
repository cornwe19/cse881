package cse.datamining;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GroundTruth {

   private final static String DISCONTINUED = "discontinued";
   private final static String ENTRIES = "Edges:";
   
   private final static String OUT_FMT      = "%d\n";
   private final static String COMPLETE_FMT = "\r%.0f%% complete";
   
   private final static String[] GROUPS = { "Baby Product", "Video Games", "Toy", "Video", "Book", "Software", "Sports", "CE", "DVD", "Music" };
   
   public static void main( String[] argv ) throws IOException {
      Scanner metaScanner = new Scanner( new File( argv[0] ) );
      
      while ( !metaScanner.hasNextInt() ) metaScanner.next(); // skip forward to first int
      
      int[] validProducts = new int[metaScanner.nextInt()];
      metaScanner.nextLine();
      metaScanner.nextLine();
      
      System.out.println( String.format( "Processing product meta information (%d items)", validProducts.length ) );
      for ( int i = 0; i < validProducts.length; i++ ) {
         // Skip id and ASIN fields
         metaScanner.nextLine();
         metaScanner.nextLine();
         
         if ( !metaScanner.nextLine().contains( DISCONTINUED ) ) {
            String groupLine = metaScanner.nextLine();
            for ( int g = 0; g < GROUPS.length; g++ ) {
               if ( groupLine.contains( GROUPS[g] ) ) {
                  validProducts[i] = g + 1;
                  break;
               }
            }
         } else {
            validProducts[i] = -1;
         }
         
         while( !metaScanner.nextLine().isEmpty() );
         
         System.out.print( String.format( COMPLETE_FMT, ( (float)i / (float)validProducts.length ) * 100f ) );
      }
      
      metaScanner.close();
      
      Scanner connectionScanner = new Scanner( new File( argv[1] ) );
      while( !connectionScanner.next().equals( ENTRIES ) ); // Skip ahead to the edges field
      
      int numEdges = connectionScanner.nextInt();
      while( !connectionScanner.hasNextInt() ) connectionScanner.next();
      int i = 0;
      
      FileWriter outWriter = new FileWriter( argv[2] );
      
      System.out.println( String.format( "\nProcessing linked nodes (%d edges)", numEdges ) );
      
      int currentFrom = -1;
      while ( connectionScanner.hasNextInt() ) {
         int from = connectionScanner.nextInt();
         int to   = connectionScanner.nextInt();
         
         if ( validProducts[from] > 0 && validProducts[to] > 0 && currentFrom != from ) {
            outWriter.write( String.format( OUT_FMT, validProducts[from] ) );
            currentFrom = from;
         }
         
         i++;
         System.out.print( String.format( COMPLETE_FMT, ( (float)i / (float)numEdges ) * 100f ) );
      }
      
      System.out.println( "\nDone." );
      
      outWriter.close();
   }
   
}

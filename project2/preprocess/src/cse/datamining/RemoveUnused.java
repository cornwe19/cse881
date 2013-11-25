package cse.datamining;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class RemoveUnused {

   private final static String DISCONTINUED = "discontinued";
   private final static String ENTRIES = "Edges:";
   
   private final static String OUT_FMT      = "%d\t%d\n";
   private final static String COMPLETE_FMT = "\r%.0f%% complete";
   
   public static void main( String[] argv ) throws IOException {
      Scanner metaScanner = new Scanner( new File( argv[0] ) );
      
      while ( !metaScanner.hasNextInt() ) metaScanner.next(); // skip forward to first int
      
      boolean[] validProducts = new boolean[metaScanner.nextInt()];
      metaScanner.nextLine();
      metaScanner.nextLine();
      
      System.out.println( String.format( "Processing product meta information (%d items)", validProducts.length ) );
      for ( int i = 0; i < validProducts.length; i++ ) {
         // Skip id and ASIN fields
         metaScanner.nextLine();
         metaScanner.nextLine();
         
         validProducts[i] = !metaScanner.nextLine().contains( DISCONTINUED );
         
         while( !metaScanner.nextLine().isEmpty() );
         
         System.out.print( String.format( COMPLETE_FMT, ( (float)i / (float)validProducts.length ) * 100f ) );
      }
      
      metaScanner.close();
      
      Scanner connectionScanner = new Scanner( new File( argv[1] ) );
      while( !connectionScanner.next().equals( ENTRIES ) ); // Skip ahead to the edges field
      
      int numEdges = connectionScanner.nextInt();
      while( !connectionScanner.hasNextInt() ) connectionScanner.next();
      float i = 0;
      
      FileWriter outWriter = new FileWriter( argv[2] );
      
      System.out.println( String.format( "\nProcessing linked nodes (%d edges)", numEdges ) );
      
      while ( connectionScanner.hasNextInt() ) {
         int from = connectionScanner.nextInt();
         int to   = connectionScanner.nextInt();
         
         if ( validProducts[from] && validProducts[to] ) {
            outWriter.write( String.format( OUT_FMT, from, to ) );
         }
         
         i++;
         System.out.print( String.format( COMPLETE_FMT, ( i / numEdges ) * 100f ) );
      }
      
      System.out.println( "\nDone." );
      
      outWriter.close();
   }
   
}

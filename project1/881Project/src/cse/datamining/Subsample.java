package cse.datamining;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Subsample {
   
   public static void main( String[] argv ) throws IOException {
      if ( argv.length < 3 ) {
         System.err.printf( "Usage: %s IN_FILE NUM_SAMPLES OUT_FILE", Subsample.class.getName() );
         return;
      }
      
      RandomAccessFile    file = new RandomAccessFile( new File( argv[0]), "r" );
      RandomRecordGrabber grabber = new RandomRecordGrabber( file );
      FileWriter          writer = new FileWriter( argv[2] );
      int                 numSamples = Integer.parseInt( argv[1] );
      
      System.out.printf( "Subsampling %d lines from '%s' -> '%s'\n", numSamples, argv[0], argv[2] );
      int samples = 0;
      while( samples < numSamples ) {
         for ( String sample : grabber.getRandomRecords() ) {
            writer.write( sample );
            writer.append( "\n" );
            
            samples++;
            if ( samples == numSamples ) {
               break;
            }
         }
         
         System.out.printf( "\r%d samples processed", samples );
      }
      
      System.out.println( "\nDone" );
      
      writer.close();
      file.close();
   }
}

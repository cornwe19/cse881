package cse.datamining;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomRecordGrabber {
   private static final int CHUNK_SIZE = 1024*5;
   
   private final RandomAccessFile mFile;
   private final byte[]           mScratch = new byte[CHUNK_SIZE];
   private final Random           mRandom  = new Random( System.currentTimeMillis() );
   
   public RandomRecordGrabber( RandomAccessFile file ) {
      mFile = file;
   }
   
   public List<String> getRandomRecords() throws IOException {
      long nextPos = (long) ( mRandom.nextDouble() * mFile.length() );
      nextPos = Math.max( 0, nextPos - CHUNK_SIZE );
      
      mFile.seek( nextPos );
      mFile.read( mScratch );
      
      ArrayList<String> records = new ArrayList<String>();
      
      int from = -1;
      for ( int i = 0; i < mScratch.length; i++ ) {
         if ( mScratch[i] == '\n' || mScratch[i] == '\r' ) {
            if ( from < 0 ) {
               from = i + 1;
            } else {
               records.add( new String( Arrays.copyOfRange( mScratch, from, i ) ) );
               
               from = i+1;
            }
         }
      }
      
      return records;
   }
}

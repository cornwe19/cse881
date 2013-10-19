package cse.datamining;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Random;

public class RandomRecordGrabber {
   private static final int CHUNK_SIZE = 1024;
   
   private final RandomAccessFile mFile;
   private final byte[]           mScratch = new byte[CHUNK_SIZE];
   private final Random           mRandom  = new Random( System.currentTimeMillis() );
   
   public RandomRecordGrabber( RandomAccessFile file ) {
      mFile = file;
   }
   
   public String nextRandomRecord() throws IOException {
      long nextPos = (long) ( mRandom.nextDouble() * mFile.length() );
      nextPos = Math.max( 0, nextPos - CHUNK_SIZE );
      
      mFile.seek( nextPos );
      if ( nextPos == 0 ) {
         return mFile.readLine();
      } else {
         mFile.read( mScratch );
         
         int from = -1;
         int to = -1;
         for ( int i = 0; i < mScratch.length; i++ ) {
            if ( mScratch[i] == '\n' || mScratch[i] == '\r' ) {
               if ( from < 0 ) {
                  from = i + 1;
               } else {
                  to = i;
                  break;
               }
            }
         }
         
         if ( to < 0 ) {
            mFile.seek( from );
            return mFile.readLine();
         } else {
            return new String( Arrays.copyOfRange( mScratch, from, to ) );
         }
      }
   }
}

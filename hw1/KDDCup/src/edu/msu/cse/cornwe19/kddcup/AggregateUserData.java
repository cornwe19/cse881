package edu.msu.cse.cornwe19.kddcup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Scanner;

public class AggregateUserData {

   public static void main( String[] argv ) {
      File userProfiles = new File( "data/user_profile.txt" );
      
      HashMap<Integer,Profile> profiles = new HashMap<Integer, Profile>();
      
      try {
         Scanner scanner = new Scanner( userProfiles );
         
         while ( scanner.hasNextInt() ) {
            Profile user = new Profile( scanner );
            profiles.put( user.id, user );
         }
         
         scanner.close();
      } catch ( FileNotFoundException e ) {
         System.err.println( String.format( "Failed - couldn't find %s", userProfiles.getPath() ) );
         return;
      }
      
      System.out.println( String.format( "Loaded %d profiles. " +
      		"Aggregating user following information", profiles.size() ) );
      
      File following = new File( "data/user_sns.txt" );
      
      try {
         Scanner scanner = new Scanner( following );
         
         while( scanner.hasNextInt() ) {
            Profile follower = profiles.get( scanner.nextInt() );
            if ( follower != null ) {
               follower.numberFollowed++;
            }
            
            Profile followee = profiles.get( scanner.nextInt() );
            if ( followee != null ) {
               followee.numberOfFollowers++;
            }
         }
         
         scanner.close();
      } catch ( FileNotFoundException e ) {
         System.err.println( String.format( "Failed - couldn't find %s", following.getPath() ) );
         return;
      }
      
      System.out.println( "Finished aggregating folllowing information, writing results" );
      
      try {
         PrintWriter writer = new PrintWriter( "data/user.txt", "ASCII" );
         
         for ( Profile profile : profiles.values() ) {
            if ( profile.isValid() ) {
               writer.println( profile.toString() );
            }
         }
         
         writer.close();
      } catch ( FileNotFoundException e ) {
         System.err.println( "There was a problem openeing data/user.txt for writing" );
      } catch ( UnsupportedEncodingException e ) {
         throw new IllegalArgumentException( e ); 
      }
   }
   
   private static class Profile {
      public final int id;
      public int numberFollowed = 0;
      public int numberOfFollowers = 0;
      public final int numberOfTweets;
      
      @Override
      public String toString() {
         return String.format( "%d, %d, %d, %d", id, numberOfFollowers, numberFollowed, numberOfTweets );
      }

      public Profile( Scanner scanner ) {
         id = scanner.nextInt();
         scanner.next(); // skip loading year of birth info
         scanner.next(); // skip loading gender info
         numberOfTweets = scanner.nextInt();
         scanner.next(); // skip loading tags
      }
      
      public boolean isValid() {
         return ( numberOfFollowers | numberFollowed | numberOfTweets ) != 0;
      }
  
   }
}

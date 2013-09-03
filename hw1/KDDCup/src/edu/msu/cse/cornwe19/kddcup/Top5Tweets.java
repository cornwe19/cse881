package edu.msu.cse.cornwe19.kddcup;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Top5Tweets {

   private final static int NUM_TOP_TWEETERS = 5;
   
   public static void main( String[] argv ) {
      final File userProfile = new File( "data/user_profile.txt" );
      final LinkedList<Profile> topTweeters = new LinkedList<Profile>();
      
      try {
         Scanner scanner = new Scanner( userProfile );
         
         while ( scanner.hasNextInt() ) {
            Profile profile = new Profile( scanner );
            
            if ( topTweeters.size() < NUM_TOP_TWEETERS ) {
               insertAscending( profile, topTweeters );
            } else {
               if ( topTweeters.get( 0 ).numberOfTweets < profile.numberOfTweets ) {
                  insertAscending( profile, topTweeters );
                  topTweeters.removeFirst();
               }
            }
         }
         
         printTopTweetersDescending( topTweeters );
      } catch ( FileNotFoundException e ) {
         System.err.println( String.format( "Failed - couldn't find %s", userProfile.getPath() ) );
      }
   }

   private static void printTopTweetersDescending( LinkedList<Profile> topTweeters ) {
      System.out.println( "Top Tweeters:" );
      for ( int i = topTweeters.size() - 1; i >= 0; i-- ) {
         System.out.println( String.format( "%d. %s", NUM_TOP_TWEETERS - i, topTweeters.get( i ) ) );
      }
   }

   private static void insertAscending( Profile newProfile, List<Profile> profiles ) {
      int insertAt = 0;
      for ( Profile profile : profiles ) {
         if ( newProfile.numberOfTweets < profile.numberOfTweets ) {
            break;
         }
         insertAt++;
      }
      
      profiles.add( insertAt, newProfile );
   }
   
   private static class Profile {
      public final int id;
      public final int numberOfTweets;
      
      @Override
      public String toString() {
         return String.format( "User %d - %d tweets", id, numberOfTweets );
      }

      public Profile( Scanner scanner ) {
         id = scanner.nextInt();
         scanner.next(); // skip loading year of birth info
         scanner.next(); // skip loading gender info
         numberOfTweets = scanner.nextInt();
         scanner.next(); // skip loading tags
      }
  
   }
   
}

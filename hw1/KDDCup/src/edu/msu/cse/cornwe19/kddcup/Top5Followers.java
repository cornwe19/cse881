package edu.msu.cse.cornwe19.kddcup;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

public class Top5Followers {

   private static final int NUM_TOP_FOLLOWEES = 5;

   public static void main( String argv[] ) {
      File following = new File( "data/user_sns.txt" );
      try {
         Scanner scanner = new Scanner( following );
         
         FollowerCounts counts = new FollowerCounts();
         
         while ( scanner.hasNext() ) {
            counts.addRelationship( scanner );
         }
         
         LinkedList<Followee> topFollowees = new LinkedList<Followee>();
         
         for ( Entry<Integer,Integer> followerCount : counts.entrySet() ) {
            if ( topFollowees.size() < NUM_TOP_FOLLOWEES ) {
               addAscending( new Followee( followerCount.getKey(), followerCount.getValue() ), topFollowees );
            } else {
               if ( followerCount.getValue() > topFollowees.getFirst().numFollowers ) {
                  addAscending( new Followee( followerCount.getKey(), followerCount.getValue() ), topFollowees );
                  topFollowees.removeFirst();
               }
            }
         }
         
         System.out.println( "Top Followees:" );
         for ( int i = topFollowees.size() - 1; i >= 0; i-- ) {
            System.out.println( String.format( "%d. %s", NUM_TOP_FOLLOWEES - i, topFollowees.get( i ) ) );
         }
      } catch ( FileNotFoundException e ) {
         System.err.println( String.format( "Failed to find file %s", following.getPath() ) );
      }
   }
   
   private static void addAscending( Followee newFollowee, List<Followee> topFollowees ) {
      int insertAt = 0;
      for ( Followee followee : topFollowees ) {
         if ( newFollowee.numFollowers < followee.numFollowers ) {
            break;
         }
         
         insertAt++;
      }
      
      topFollowees.add( insertAt, newFollowee );
   }
   
   private static class Followee {
      public final int id;
      public final int numFollowers;
      
      @Override
      public String toString() {
         return String.format( "User %d - %d followers", id, numFollowers );
      }

      public Followee( int id, int numFollowers ) {
         this.id = id;
         this.numFollowers = numFollowers;
      }
   }
   
   private static class FollowerCounts extends HashMap<Integer,Integer> {
      private static final long serialVersionUID = 308081944524833981L;

      public void addRelationship( Scanner scanner ) {
         scanner.next(); // Doesn't matter who the follower is.
         int followee = scanner.nextInt();
         
         if ( containsKey( followee ) ) {
            put( followee, get( followee ) + 1 );
         } else {
            put( followee, 1 );
         }
      }
   }
}

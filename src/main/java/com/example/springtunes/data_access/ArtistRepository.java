package com.example.springtunes.data_access;

import com.example.springtunes.models.Artist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArtistRepository {
    // Setting up the connection object we need.
    private String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    private Connection conn = null;

    public List<Artist> getAllArtists(){
        List<Artist> artists = new ArrayList<>();
        // ---
        try{
            // connect
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT ArtistId, Name FROM Artist");
            ResultSet set = prep.executeQuery();
            while(set.next()){
                artists.add( new Artist(
                        set.getString("ArtistId"),
                        set.getString("Name")
                ));
            }
            System.out.println("Get all went well!");

        }catch(Exception exception){
            System.out.println(exception.toString());
        }
        finally {
            try{
                conn.close();
            } catch (Exception exception){
                System.out.println(exception.toString());
            }
        }
        // ---
        return artists;
    }

    public List<Artist> getFiveRandomArtists() {
        List<Artist> allArtists = new ArrayList<>();
        List<Artist> fiveRandomArtists = new ArrayList<>();
        // ---
        try{
            // connect
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT ArtistId, Name FROM Artist");
            ResultSet set = prep.executeQuery();
            while(set.next()){
                allArtists.add( new Artist(
                        set.getString("ArtistId"),
                        set.getString("Name")
                ));
            }
            System.out.println("Get all went well!");

        }catch(Exception exception){
            System.out.println(exception.toString());
        }
        finally {
            try{
                conn.close();
                Random random = new Random();
                for (int i = 0; i < 5; i++) {
                    int randomIndex = random.nextInt(allArtists.size());
                    fiveRandomArtists.add(allArtists.get(randomIndex));
                }
            } catch (Exception exception){
                System.out.println(exception.toString());
            }
        }
        // ---
        return fiveRandomArtists;
    }

    public Artist getSpecificArtist(String id){
        Artist artist = null;
        // ---
        try{
            // connect
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT CustomerId, Company, Phone " +
                            "FROM customer WHERE CustomerId=?");
            prep.setString(1,id);
            ResultSet set = prep.executeQuery();
            while(set.next()){
                artist = new Artist(
                        set.getString("ArtistId"),
                        set.getString("Name")
                );
            }
            System.out.println("Get specific went well!");

        }catch(Exception exception){
            System.out.println(exception.toString());
        }
        finally {
            try{
                conn.close();
            } catch (Exception exception){
                System.out.println(exception.toString());
            }
        }
        // ---

        return artist;
    }
}

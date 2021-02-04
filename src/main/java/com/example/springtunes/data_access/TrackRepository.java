package com.example.springtunes.data_access;

import com.example.springtunes.models.Track;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TrackRepository {
    // Setting up the connection object we need.
    private String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    private Connection conn = null;

    public List<Track> getAllTracks(){
        List<Track> tracks = new ArrayList<>();
        // ---
        try{
            // connect
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT TrackId, Name, AlbumId, GenreId FROM Track");
            ResultSet set = prep.executeQuery();
            while(set.next()){
                tracks.add( new Track(
                        set.getString("TrackId"),
                        set.getString("Name"),
                        set.getString("AlbumId"),
                        set.getString("GenreId")
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
        return tracks;
    }

    public List<Track> getFiveRandomTracks() {
        List<Track> allTracks = new ArrayList<>();
        List<Track> fiveRandomTracks = new ArrayList<>();
        // ---
        try{
            // connect
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT TrackId, Name FROM Track");
            ResultSet set = prep.executeQuery();
            while(set.next()){
                allTracks.add( new Track(
                        set.getString("TrackId"),
                        set.getString("Name"),
                        set.getString("AlbumId"),
                        set.getString("GenreId")
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
                    int randomIndex = random.nextInt(allTracks.size());
                    fiveRandomTracks.add(allTracks.get(randomIndex));
                }
            } catch (Exception exception){
                System.out.println(exception.toString());
            }
        }
        // ---
        return fiveRandomTracks;
    }

    public Track getSpecificTrack(String id){
        Track track = null;
        // ---
        try{
            // connect
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT TrackId, Name, AlbumId, GenreId " +
                            "FROM Track WHERE TrackId=?");
            prep.setString(1,id);
            ResultSet set = prep.executeQuery();
            while(set.next()){
                track = new Track(
                        set.getString("TrackId"),
                        set.getString("Name"),
                        set.getString("AlbumId"),
                        set.getString("GenreId")
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

        return track;
    }
}
package com.example.springtunes.data_access;

import com.example.springtunes.models.Artist;
import com.example.springtunes.models.Genre;
import com.example.springtunes.models.Track;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TunesRepository {
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
                    conn.prepareStatement("SELECT TrackId, Name, AlbumId, GenreId FROM Track");
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

    public Track getTrackById(String id){
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

    public Track getTrackByName(String name){
        Track track = null;
        name = name.toUpperCase();
        // ---
        try{
            // connect
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT TrackId, Track.Name AS Name, Artist.Name AS Artist, Album.Title AS Album, GenreId " +
                            "FROM Track JOIN Album ON Track.AlbumId = Album.AlbumId JOIN Artist ON Album.ArtistId = Artist.ArtistId " +
                            " WHERE UPPER(Track.Name)=?");
            prep.setString(1, name);
            ResultSet set = prep.executeQuery();
            while(set.next()){
                track = new Track();
                track.setTrackId(set.getString("TrackId"));
                track.setName(set.getString("Name"));
                track.setArtistName(set.getString("Artist"));
                track.setAlbumName(set.getString("Album"));
                track.setGenreName(set.getString("GenreId"));
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


    public List<Genre> getAllGenres(){
        List<Genre> genres = new ArrayList<>();
        // ---
        try{
            // connect
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT GenreId, Name FROM Genre");
            ResultSet set = prep.executeQuery();
            while(set.next()){
                genres.add( new Genre(
                        set.getString("GenreId"),
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
        return genres;
    }

    public List<Genre> getFiveRandomGenres() {
        List<Genre> allGenres = new ArrayList<>();
        List<Genre> fiveRandomGenres = new ArrayList<>();
        // ---
        try{
            // connect
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT GenreId, Name FROM Genre");
            ResultSet set = prep.executeQuery();
            while(set.next()){
                allGenres.add( new Genre(
                        set.getString("GenreId"),
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
                    int randomIndex = random.nextInt(allGenres.size());
                    fiveRandomGenres.add(allGenres.get(randomIndex));
                }
            } catch (Exception exception){
                System.out.println(exception.toString());
            }
        }
        // ---
        return fiveRandomGenres;
    }
}

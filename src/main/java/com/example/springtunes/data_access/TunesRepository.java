package com.example.springtunes.data_access;

import com.example.springtunes.models.Artist;
import com.example.springtunes.models.Genre;
import com.example.springtunes.models.Track;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// This class serves as the encapsulation of database interactions for the data used in the Thymeleaf pages
// in order to to keep the logic out of the TunesController as much as possible.
public class TunesRepository {
    private final String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    private Connection conn = null;
    Logger logger = LoggerFactory.getLogger(TunesRepository.class);

    public List<Artist> getAllArtists(){
        List<Artist> artists = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT ArtistId, Name FROM Artist");
            ResultSet set = prep.executeQuery();
            while (set.next()) {
                artists.add( new Artist(
                        set.getString("ArtistId"),
                        set.getString("Name")
                ));
            }
            logger.info("Get all artists went well!");

        } catch (Exception exception) {
            logger.error(exception.toString());
        }
        finally {
            closeConnection();
        }
        return artists;
    }

    private void closeConnection() {
        try {
            conn.close();
        } catch (Exception exception) {
            logger.error(exception.toString());
        }
    }

    public List<Artist> getFiveRandomArtists() {
        List<Artist> allArtists = getAllArtists();
        List<Artist> fiveRandomArtists = new ArrayList<>();
        getFiveRandom(allArtists, fiveRandomArtists);
        return fiveRandomArtists;
    }

    // Used to populate the list of five random objects of any type (Artist, Track, Genre)
    private <T> void getFiveRandom(List<T> all, List<T> five) {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(all.size());
            five.add(all.get(randomIndex));
        }
    }

    public List<Track> getAllTracks(){
        List<Track> tracks = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT TrackId, Name, AlbumId, GenreId FROM Track");
            ResultSet set = prep.executeQuery();
            while (set.next()) {
                tracks.add( new Track(
                        set.getString("TrackId"),
                        set.getString("Name"),
                        set.getString("AlbumId"),
                        set.getString("GenreId")
                ));
            }
            logger.info("Get all tracks went well!");

        } catch (Exception exception) {
            System.out.println(exception.toString());
        }
        finally {
            closeConnection();
        }
        return tracks;
    }

    public List<Track> getFiveRandomTracks() {
        List<Track> allTracks = getAllTracks();
        List<Track> fiveRandomTracks = new ArrayList<>();
        getFiveRandom(allTracks, fiveRandomTracks);
        return fiveRandomTracks;
    }

    public List<Genre> getAllGenres(){
        List<Genre> genres = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT GenreId, Name FROM Genre");
            ResultSet set = prep.executeQuery();
            while (set.next()) {
                genres.add( new Genre(
                        set.getString("GenreId"),
                        set.getString("Name")
                ));
            }
            logger.info("Get all genres went well!");

        } catch (Exception exception) {
            logger.error(exception.toString());
        }
        finally {
            closeConnection();
        }
        return genres;
    }

    public List<Genre> getFiveRandomGenres() {
        List<Genre> allGenres = getAllGenres();
        List<Genre> fiveRandomGenres = new ArrayList<>();
        getFiveRandom(allGenres, fiveRandomGenres);
        return fiveRandomGenres;
    }

    public Track getTrackByName(String name){
        Track track = null;
        name = name.toUpperCase();
        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT TrackId, T.Name AS Name, Artist.Name AS Artist, " +
                            "Album.Title AS Album, G.Name AS GenreName FROM Genre G " +
                            "JOIN Track T ON G.GenreId = T.GenreId " +
                            "JOIN Album ON T.AlbumId = Album.AlbumId " +
                            "JOIN Artist ON Album.ArtistId = Artist.ArtistId WHERE UPPER(T.Name)=?");
            prep.setString(1, name);
            ResultSet set = prep.executeQuery();
            while (set.next()) {
                track = new Track();
                track.setTrackId(set.getString("TrackId"));
                track.setName(set.getString("Name"));
                track.setArtistName(set.getString("Artist"));
                track.setAlbumName(set.getString("Album"));
                track.setGenreName(set.getString("GenreName"));
            }
            logger.info("Get specific track went well!");

        } catch (Exception exception) {
            logger.error(exception.toString());
        }
        finally {
            closeConnection();
        }
        return track;
    }
}
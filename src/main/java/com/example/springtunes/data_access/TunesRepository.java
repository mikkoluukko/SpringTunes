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

// This class serves as the encapsulation of database interactions for the data used in the Thymeleaf pages
// in order to to keep the logic out of the TunesController as much as possible.
public class TunesRepository {
    private final String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    private Connection conn = null;
    Logger logger = LoggerFactory.getLogger(TunesRepository.class);

    private void closeConnection() {
        try {
            conn.close();
        } catch (Exception exception) {
            logger.error(exception.toString());
        }
    }

    public List<Artist> getFiveRandomArtists() {
        List<Artist> artists = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT ArtistId, Name FROM Artist ORDER BY RANDOM() LIMIT 5");
            ResultSet resultSet = prep.executeQuery();
            while (resultSet.next()) {
                artists.add( new Artist(
                        resultSet.getString("ArtistId"),
                        resultSet.getString("Name")
                ));
            }
            logger.info("Get random artists went well!");

        } catch (Exception exception) {
            logger.error(exception.toString());
        }
        finally {
            closeConnection();
        }
        return artists;
    }

    public List<Track> getFiveRandomTracks() {
        List<Track> tracks = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT Name FROM Track ORDER BY RANDOM() LIMIT 5");
            ResultSet resultSet = prep.executeQuery();
            while (resultSet.next()) {
                Track randomTrack = new Track();
                randomTrack.setName(resultSet.getString("Name"));
                tracks.add(randomTrack);
            }
            logger.info("Get random tracks went well!");

        } catch (Exception exception) {
            System.out.println(exception.toString());
        }
        finally {
            closeConnection();
        }
        return tracks;
    }

    public List<Genre> getFiveRandomGenres() {
        List<Genre> genres = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT GenreId, Name FROM Genre ORDER BY RANDOM() LIMIT 5");
            ResultSet resultSet = prep.executeQuery();
            while (resultSet.next()) {
                genres.add( new Genre(
                        resultSet.getString("GenreId"),
                        resultSet.getString("Name")
                ));
            }
            logger.info("Get random genres went well!");

        } catch (Exception exception) {
            logger.error(exception.toString());
        }
        finally {
            closeConnection();
        }
        return genres;
    }

    public Track getTrackByName(String name) {
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
            ResultSet resultSet = prep.executeQuery();
            while (resultSet.next()) {
                track = new Track();
                track.setTrackId(resultSet.getString("TrackId"));
                track.setName(resultSet.getString("Name"));
                track.setArtistName(resultSet.getString("Artist"));
                track.setAlbumName(resultSet.getString("Album"));
                track.setGenreName(resultSet.getString("GenreName"));
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
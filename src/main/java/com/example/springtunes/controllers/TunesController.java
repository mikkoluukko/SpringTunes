package com.example.springtunes.controllers;

import com.example.springtunes.data_access.TunesRepository;
import com.example.springtunes.models.Artist;
import com.example.springtunes.models.Genre;
import com.example.springtunes.models.Track;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class TunesController {
    TunesRepository tunesRepository = new TunesRepository();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getHomePage(Model model) {
        Track searchTrack = new Track();
        List<Artist> fiveRandomArtists = tunesRepository.getFiveRandomArtists();
        List<Track> fiveRandomTracks = tunesRepository.getFiveRandomTracks();
        List<Genre> fiveRandomGenres = tunesRepository.getFiveRandomGenres();
        model.addAttribute("searchTrack", searchTrack);
        model.addAttribute("artists", fiveRandomArtists);
        model.addAttribute("tracks", fiveRandomTracks);
        model.addAttribute("genres", fiveRandomGenres);
        return "index";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String getSearchResults(@ModelAttribute Track track, BindingResult error, Model model) {
        Track newTrack = tunesRepository.getTrackByName(track.getName());

        model.addAttribute("track", newTrack);
//        if(success){model.addAttribute("customer", new Track());}

        return "searchResults";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String getSearchResults(Model model) {
        return "searchResults";
    }
}

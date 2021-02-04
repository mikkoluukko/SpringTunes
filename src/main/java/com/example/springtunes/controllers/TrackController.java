package com.example.springtunes.controllers;

import com.example.springtunes.data_access.TrackRepository;
import com.example.springtunes.models.Artist;
import com.example.springtunes.models.Track;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public class TrackController {
    TrackRepository trackRepository = new TrackRepository();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getAllATracks(Model model){
        List<Track> fiveRandomTracks = trackRepository.getFiveRandomTracks();
        model.addAttribute("artists", fiveRandomTracks);
        return "index";
    }

    @RequestMapping(value = "/artists/{id}")
    public String getSpecificTrack(@PathVariable String id, Model model){
        model.addAttribute("artist", trackRepository.getSpecificTrack(id));
        return "search-result";
    }
}

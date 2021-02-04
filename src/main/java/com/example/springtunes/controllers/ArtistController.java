package com.example.springtunes.controllers;

import com.example.springtunes.data_access.ArtistRepository;
import com.example.springtunes.models.Artist;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ArtistController {

    ArtistRepository artistRepository = new ArtistRepository();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getAllArtists(Model model){
        List<Artist> fiveRandomArtists = artistRepository.getFiveRandomArtists();
        model.addAttribute("artists", fiveRandomArtists);
        return "index";
    }

//    @RequestMapping(value = "/artists/{id}")
//    public String getSpecificArtist(@PathVariable String id, Model model){
//        model.addAttribute("artist", artistRepository.getSpecificArtist(id));
//        return "view-artist";
//    }
}

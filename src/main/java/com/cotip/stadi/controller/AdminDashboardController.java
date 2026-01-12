package com.cotip.stadi.controller;

import com.cotip.stadi.entities.Music;
import com.cotip.stadi.service.MusicService;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {
	  private final MusicService musicService;

	    public AdminDashboardController(MusicService musicService) {
	        this.musicService = musicService;
	    }
	 @InitBinder
	    public void initBinder(WebDataBinder binder) {
	        binder.setDisallowedFields("songFile", "artistImage");
	    }


    @Autowired
    private MusicService songService;

    // Show Add Song form
    @GetMapping("/form_song")
    public String formSong(Model model) {
        model.addAttribute("song", new Music()); // empty song object for add
        return "admin/form_song";
    }

    // Show Edit Song form
    @GetMapping("/songs/edit/{id}")
    public String editSong(@PathVariable Long id, Model model) {
    	Music song = songService.findById(id);
        model.addAttribute("song", song);
        return "admin/form_song";
    }

    @PostMapping("/songs/save")
    public String saveSong(@ModelAttribute Music song,
                           @RequestParam("songFile") MultipartFile songFile,
                           @RequestParam("artistImage") MultipartFile artistImage,
                           Model model) {
        try {
            songService.save(song, songFile, artistImage);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Failed to save the song: " + e.getMessage());
            return "admin/form_song";
        }
        return "redirect:/admin/add_music";
    }
    
   

    @PostMapping("/songs/update")
    public String updateSong(@ModelAttribute Music song,
                             @RequestParam("songFile") MultipartFile songFile,
                             @RequestParam("artistImage") MultipartFile artistImage,
                             Model model) {
        try {
            songService.update(song, songFile, artistImage);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Failed to update the song: " + e.getMessage());
            return "admin/form_song";
        }
        return "redirect:/admin/add_music";
    }

 // Delete a song
    @GetMapping("/songs/delete/{id}")
    public String deleteSong(@PathVariable Long id, Model model) {
        try {
            songService.deleteById(id); // <-- this method should be in MusicService
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Failed to delete the song: " + e.getMessage());
            return "admin/add_music"; // keep user on the same page
        }
        return "redirect:/admin/add_music";
    }

    // Dashboard
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
    	model.addAttribute("totalSongs", musicService.countAll());
        model.addAttribute("gospelCount", musicService.countByCategory("GOSPEL"));
        model.addAttribute("bongoCount", musicService.countByCategory("BONGO_FLAVA"));
        model.addAttribute("hiphopCount", musicService.countByCategory("HIP_HOP"));
        model.addAttribute("audioCount", musicService.countBySongType("AUDIO"));
        model.addAttribute("videoCount", musicService.countBySongType("VIDEO"));
        
        // Notifications example (latest songs uploaded)
        List<Music> latestSongs = musicService.findLatest(5);
        model.addAttribute("latestSongs", latestSongs);

        // Messages placeholder (if you implement a MessageService)
        // model.addAttribute("newMessages", messageService.getLatestMessages(5));
        // model.addAttribute("newMessagesCount", messageService.countUnreadMessages());

        // Tasks placeholder (songs awaiting approval)
        // model.addAttribute("pendingTasks", musicService.findPendingApproval());
        // model.addAttribute("pendingTasksCount", pendingTasks.size());
        return "admin/dashboard";
    }

    // Music List
    @GetMapping("/add_music")
    public String addMusic(Model model) {
        model.addAttribute("songs", songService.findAll());
        return "admin/add_music";
    }
}

package com.cotip.stadi.controller;

import com.cotip.stadi.dto.EntertainmentDto.RelatedLink;
import com.cotip.stadi.entities.EntertainmentPost;
import com.cotip.stadi.entities.Music;
import com.cotip.stadi.service.EntertainmentPostService;
import com.cotip.stadi.service.MusicService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
public class HomeController {
	;

    private final MusicService musicService;
    private final EntertainmentPostService entertainmentPostService;

    // Inject both services via constructor
    public HomeController(MusicService musicService, EntertainmentPostService entertainmentPostService) {
        this.musicService = musicService;
        this.entertainmentPostService = entertainmentPostService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Music> allMusic = musicService.findAll(); // fetch all music
        model.addAttribute("musics", allMusic); // add to model
        return "index"; // Thymeleaf template name
    }

    @GetMapping("/download/{id}")
    public String downloadPage(@PathVariable("id") Long id, Model model) {
        Music music = musicService.findById(id);
        model.addAttribute("music", music);
     // All musics for featured posts
        List<Music> allMusics = musicService.findAll();
        model.addAttribute("allMusics", allMusics);

        // "You may also like" => same artist, exclude current song
        List<Music> artistSongs = musicService.findByArtistName(music.getArtistName())
                                             .stream()
                                             .filter(m -> !m.getId().equals(id))
                                             .toList();
        model.addAttribute("musics", artistSongs);
        return "download";
    }

    @GetMapping("/new_audio")
    public String allAudio(Model model) {
        // Fetch only audio tracks
        List<Music> audios = musicService.findBySongType("AUDIO");
        model.addAttribute("musics", audios); // must match the template's th:each
        // FEATURED POSTS (GLOBAL – HOME PAGE DATA)
        model.addAttribute("featuredMusics",
                musicService.findLatest(10)); // NO category filte
        return "new_audio"; // name of Thymeleaf template
    }

    @GetMapping("/gospel")
    public String gospel(Model model) {
        List<Music> gospelSongs = musicService.findByCategory("Gospel");
        model.addAttribute("musics", gospelSongs);
        // FEATURED POSTS (GLOBAL – HOME PAGE DATA)
        model.addAttribute("featuredMusics",
                musicService.findLatest(10)); // NO category filte
        return "gospel";
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        List<Music> results = musicService.searchMusic(query);
        model.addAttribute("musics", results);
        model.addAttribute("query", query);
        return "index"; // reuse the home page template
    }

    // ==============================
    // Entertainment Page for Client
    // ==============================
    @GetMapping("/entertainment")
    public String entertainmentPage(Model model) {
        // Fetch all entertainment posts from service
        List<EntertainmentPost> entertainmentPosts = entertainmentPostService.findAll();
        model.addAttribute("entertainments", entertainmentPosts); // name matches Thymeleaf template

     // MAIN CONTENT
        model.addAttribute("entertainments",
                entertainmentPostService.findAllOrderByDateDesc());

        // SIDEBAR FEATURED POSTS (GLOBAL)
        model.addAttribute("featuredEntertainments",
                entertainmentPostService.findLatest(10));
        return "entertainment"; // templates/entertainment.html
    }
    
    @GetMapping("/entertainment/{id}")
    public String entertainmentDetail(@PathVariable Long id, Model model) {

        EntertainmentPost post = entertainmentPostService.findById(id);
        model.addAttribute("post", post);

        List<RelatedLink> links = new ArrayList<>();

        if (post.getRelatedLinks() != null && !post.getRelatedLinks().isBlank()) {

            String[] items = post.getRelatedLinks().split(",");

            for (String item : items) {
                item = item.trim();

                if (item.isEmpty()) continue;

                // Format: Text:URL
                if (item.contains(":")) {
                    String[] parts = item.split(":", 2);
                    links.add(new RelatedLink(
                            parts[0].trim(),
                            parts[1].trim()
                    ));
                }
                // Only URL
                else if (item.startsWith("http")) {
                    links.add(new RelatedLink("Visit Link", item));
                }
            }
        }

        model.addAttribute("relatedLinks", links);

        return "entertainment_detail";
    }
    
    @GetMapping("/video")
    public String videoPage(Model model) {
        List<Music> videos = musicService.findBySongType("VIDEO");
        model.addAttribute("videos", videos);

        // Fetch all videos for featured sidebar
        List<Music> allVideos = musicService.findBySongType("VIDEO");
        model.addAttribute("allVideos", allVideos);
        return "video"; // Thymeleaf template
    }
    
    @GetMapping("/contact")
    public String contactPage(Model model) {
    	model.addAttribute("supportEmail", "support@shuttamusicz.com");
    	model.addAttribute("whatsAppNumber", "+255764181121");
    	model.addAttribute("facebookUrl", "https://www.facebook.com/shuttamusicz");
    	 // FEATURED POSTS (GLOBAL – HOME PAGE DATA)
        model.addAttribute("featuredMusics",
                musicService.findLatest(10)); // NO category filte
        return "contact"; // Thymeleaf template name: contact.html
    }
    
 // Display all songs for a given artist
    @GetMapping("/artist")
    public String artistSongs(@RequestParam("name") String artistName, Model model) {
        List<Music> songs = musicService.findByArtistName(artistName); // implement this in your service
        model.addAttribute("artistName", artistName);
        model.addAttribute("songs", songs);
        return "artist"; // Thymeleaf template
    }

}

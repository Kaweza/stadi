package com.cotip.stadi.controller;

import com.cotip.stadi.entities.EntertainmentPost;
import com.cotip.stadi.service.EntertainmentPostService;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class EntertainmentPostController {

    private final EntertainmentPostService service;

    public EntertainmentPostController(EntertainmentPostService service) {
        this.service = service;
    }

    // Show form for adding new post
    @GetMapping("/form_entertainment")
    public String addForm(@RequestParam(value = "id", required = false) Long id, Model model) {
        EntertainmentPost entertainment;
        if (id != null) {
            entertainment = service.findById(id); // fetch existing post for edit
            if (entertainment == null) {
                entertainment = new EntertainmentPost();
            }
        } else {
            entertainment = new EntertainmentPost();
        }
        model.addAttribute("entertainment", entertainment);
        return "admin/form_entertainment";
    }

    // Handle save (new or update)
    @PostMapping("/form_entertainment")
    public String save(
            @ModelAttribute("entertainment") EntertainmentPost entertainment,
            @RequestParam(value = "mediaFile", required = false) MultipartFile mediaFile,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ) throws IOException {
        service.save(entertainment, mediaFile, imageFile);
        return "redirect:/admin/add_entertainment";
    }

    // Display all entertainment posts
    @GetMapping("/add_entertainment")
    public String listEntertainment(Model model) {
        List<EntertainmentPost> entertainments = service.findAll();
        model.addAttribute("entertainments", entertainments);
        return "admin/add_entertainment";
    }

    // Delete a post
    @GetMapping("/entertainment/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/admin/add_entertainment";
    }

    // Edit a post (redirects to same form with pre-filled values)
    @GetMapping("/entertainment/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        EntertainmentPost entertainment = service.findById(id);
        if (entertainment == null) {
            entertainment = new EntertainmentPost();
        }
        model.addAttribute("entertainment", entertainment);
        return "admin/form_entertainment";
    }
}

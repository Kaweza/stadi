package com.cotip.stadi.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "entertainment_posts")
public class EntertainmentPost {
	public String getFormattedDatePosted() {
	    if (this.datePosted != null) {
	        return this.datePosted.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
	    }
	    return "N/A";
	}


    // ===============================
    // PRIMARY KEY
    // ===============================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ===============================
    // BASIC FIELDS
    // ===============================
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    // ===============================
    // FILE PATHS
    // ===============================
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "post_url")
    private String postUrl;

    // ===============================
    // DATE
    // ===============================
    @Column(name = "date_posted")
    private LocalDateTime datePosted;

    // ===============================
    
 // ===============================
 // OPTIONAL LINKS
 // ===============================
 @Column(name = "youtube_url")
 private String youtubeUrl;  // Optional YouTube video link

 @Column(name = "related_links", columnDefinition = "TEXT")
 private String relatedLinks;  // Comma-separated other links

    // LIFECYCLE CALLBACK
    // ===============================
    @PrePersist
    protected void onCreate() {
        if (this.category == null || this.category.isEmpty()) {
            this.category = "ENTERTAINMENT";
        }
        if (this.datePosted == null) {
            this.datePosted = LocalDateTime.now();
        }
    }

    // ===============================
    // CONSTRUCTORS
    // ===============================
    public EntertainmentPost() {
    }

    // ===============================
    // GETTERS & SETTERS
    // ===============================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }
    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getRelatedLinks() {
        return relatedLinks;
    }

    public void setRelatedLinks(String relatedLinks) {
        this.relatedLinks = relatedLinks;
    }

}

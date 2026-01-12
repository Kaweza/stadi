package com.cotip.stadi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


import com.cotip.stadi.entities.Music;

public interface MusicRepository extends JpaRepository<Music, Long> {
	 List<Music> findByCategoryIgnoreCase(String category);
	 
	 // Fetch all videos
	    List<Music> findBySongType(String songType);
	    List<Music> findByArtistNameIgnoreCase(String artistName);
	    Page<Music> findAllByOrderByIdDesc(Pageable pageable);
	    // Optional: fetch videos by category if needed
	    List<Music> findBySongTypeAndCategory(String songType, String category);
	 List<Music> findByTitleContainingIgnoreCaseOrArtistNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(
		        String title, String artistName, String category
		    );
	 
	 // ✅ ADD THESE COUNT METHODS
	    long countByCategory(String category);

	    long countBySongType(String songType);
	    
}


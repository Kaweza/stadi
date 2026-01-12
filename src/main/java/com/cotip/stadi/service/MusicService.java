package com.cotip.stadi.service;

import com.cotip.stadi.entities.Music;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MusicService {
	 long countBySongType(String songType);

    List<Music> findAll();

    Music findById(Long id);

    void save(Music music, MultipartFile songFile, MultipartFile artistImage) throws Exception;

    void update(Music music, MultipartFile songFile, MultipartFile artistImage) throws Exception;

    void deleteById(Long id) throws Exception;
    List<Music> findByCategory(String category);
    List<Music> searchMusic(String query);
    List<Music> findBySongType(String songType);
    List<Music> findByArtistName(String artistName);
    List<Music> findLatest(int limit);

    // ✅ ADD THESE (for dashboard)
    long countAll();

    long countByCategory(String category);

   
    
    
}

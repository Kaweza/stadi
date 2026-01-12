package com.cotip.stadi.service;

import com.cotip.stadi.entities.Music;
import com.cotip.stadi.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    private MusicRepository musicRepository;

    // Absolute base upload directory (outside Tomcat)
    private final Path SONG_UPLOAD_DIR =
            Paths.get(System.getProperty("user.dir"), "uploads", "songs");

    private final Path IMAGE_UPLOAD_DIR =
            Paths.get(System.getProperty("user.dir"), "uploads", "images");

    @Override
    public List<Music> findAll() {
        return musicRepository.findAll();
    }

    @Override
    public Music findById(Long id) {
        return musicRepository.findById(id).orElse(null);
    }
    
    @Override
    public List<Music> findBySongType(String songType) {
        return musicRepository.findBySongType(songType);
    }
    @Override
    public List<Music> findByArtistName(String artistName) {
        return musicRepository.findByArtistNameIgnoreCase(artistName);
    }
    @Override
    public List<Music> findLatest(int limit) {
        return musicRepository
                .findAllByOrderByIdDesc(PageRequest.of(0, limit))
                .getContent();
    }

 // ================= DASHBOARD COUNTS =================

    @Override
    public long countBySongType(String songType) {
        return musicRepository.countBySongType(songType);
    }

    @Override
    public long countAll() {
        return musicRepository.count();
    }

    @Override
    public long countByCategory(String category) {
        return musicRepository.countByCategory(category);
    }

   


    @Override
    public void save(Music music, MultipartFile songFile, MultipartFile artistImage) throws IOException {

        // ===== SONG FILE =====
        if (songFile != null && !songFile.isEmpty()) {

            Files.createDirectories(SONG_UPLOAD_DIR);

            String songFileName = StringUtils.cleanPath(songFile.getOriginalFilename())
                    .replaceAll("\\s+", "_");

            Path songPath = SONG_UPLOAD_DIR.resolve(songFileName);
            songFile.transferTo(songPath.toFile());

            music.setSongFile(songFileName);
        }

        // ===== ARTIST IMAGE =====
        if (artistImage != null && !artistImage.isEmpty()) {

            Files.createDirectories(IMAGE_UPLOAD_DIR);

            String imageFileName = StringUtils.cleanPath(artistImage.getOriginalFilename())
                    .replaceAll("\\s+", "_");

            Path imagePath = IMAGE_UPLOAD_DIR.resolve(imageFileName);
            artistImage.transferTo(imagePath.toFile());

            music.setArtistImage(imageFileName);
        }

        musicRepository.save(music);
    }

    @Override
    public void update(Music music, MultipartFile songFile, MultipartFile artistImage) throws IOException {

        Music existingMusic = musicRepository.findById(music.getId())
                .orElseThrow(() -> new RuntimeException("Music not found with ID: " + music.getId()));

        existingMusic.setTitle(music.getTitle());
        existingMusic.setArtistName(music.getArtistName());
        existingMusic.setSongType(music.getSongType());

        // ===== UPDATE SONG FILE =====
        if (songFile != null && !songFile.isEmpty()) {

            Files.createDirectories(SONG_UPLOAD_DIR);

            String songFileName = StringUtils.cleanPath(songFile.getOriginalFilename())
                    .replaceAll("\\s+", "_");

            Path songPath = SONG_UPLOAD_DIR.resolve(songFileName);
            songFile.transferTo(songPath.toFile());

            existingMusic.setSongFile(songFileName);
        }

        // ===== UPDATE IMAGE =====
        if (artistImage != null && !artistImage.isEmpty()) {

            Files.createDirectories(IMAGE_UPLOAD_DIR);

            String imageFileName = StringUtils.cleanPath(artistImage.getOriginalFilename())
                    .replaceAll("\\s+", "_");

            Path imagePath = IMAGE_UPLOAD_DIR.resolve(imageFileName);
            artistImage.transferTo(imagePath.toFile());

            existingMusic.setArtistImage(imageFileName);
        }

        musicRepository.save(existingMusic);
    }
    @Override
    public List<Music> findByCategory(String category) {
        return musicRepository.findByCategoryIgnoreCase(category);
    }
    @Override
    public void deleteById(Long id) throws Exception {
        Music song = musicRepository.findById(id)
                .orElseThrow(() -> new Exception("Song not found with id " + id));

        // Delete files from filesystem
        if (song.getSongFile() != null) {
            Path songPath = Paths.get( song.getSongFile());
            Files.deleteIfExists(songPath);
        }
        if (song.getArtistImage() != null) {
            Path imagePath = Paths.get( song.getArtistImage());
            Files.deleteIfExists(imagePath);
        }

        // Delete from database
        musicRepository.deleteById(id);
    }
    
    @Override
    public List<Music> searchMusic(String query) {
        // Use repository custom method
        return musicRepository.findByTitleContainingIgnoreCaseOrArtistNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(query, query, query);
    }


}

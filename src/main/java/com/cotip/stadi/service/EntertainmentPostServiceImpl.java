package com.cotip.stadi.service;

import com.cotip.stadi.entities.EntertainmentPost;
import com.cotip.stadi.repository.EntertainmentPostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class EntertainmentPostServiceImpl implements EntertainmentPostService {

    private final EntertainmentPostRepository repository;

    public EntertainmentPostServiceImpl(EntertainmentPostRepository repository) {
        this.repository = repository;
    }
 // Fetch all posts in ENTERTAINMENT category (main content)
    @Override
    public List<EntertainmentPost> findEntertainmentPosts() {
        return repository.findByCategoryOrderByDatePostedDesc("ENTERTAINMENT");
    }
    @Override
    public List<EntertainmentPost> findLatest(int limit) {
        return repository.findAllByOrderByDatePostedDesc(PageRequest.of(0, limit)).getContent();
    }


    // Fetch all posts ordered by date descending (global latest updates)
    @Override
    public List<EntertainmentPost> findAllOrderByDateDesc() {
        return repository.findAllByOrderByDatePostedDesc();
    }


    @Override
    public void save(EntertainmentPost post, MultipartFile mediaFile, MultipartFile imageFile) throws IOException {
       
     

            // ================= IMAGE =================
            if (imageFile != null && !imageFile.isEmpty()) {

                String uploadDir = System.getProperty("user.dir")
                        + File.separator + "uploads"
                        + File.separator + "images";

                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                String fileName = System.currentTimeMillis()
                        + "_" + imageFile.getOriginalFilename();

                File destination = new File(directory, fileName);
                imageFile.transferTo(destination);

                post.setImageUrl(fileName);
            }


        // Save to database
        repository.save(post);
    }

    @Override
    public EntertainmentPost findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<EntertainmentPost> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    
    

}

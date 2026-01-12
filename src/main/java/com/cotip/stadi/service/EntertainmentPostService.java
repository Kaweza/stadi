package com.cotip.stadi.service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.cotip.stadi.entities.EntertainmentPost;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EntertainmentPostService {

    List<EntertainmentPost> findAllOrderByDateDesc(); // All posts, home page
    List<EntertainmentPost> findLatest(int limit);     // Featured posts
    List<EntertainmentPost> findEntertainmentPosts(); // ENTERTAINMENT category only

    void save(EntertainmentPost post, MultipartFile mediaFile, MultipartFile imageFile) throws IOException;
    EntertainmentPost findById(Long id);
    List<EntertainmentPost> findAll();
    void deleteById(Long id);
}

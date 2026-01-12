package com.cotip.stadi.repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.cotip.stadi.entities.EntertainmentPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntertainmentPostRepository extends JpaRepository<EntertainmentPost, Long> {
	

    // 1️⃣ Fetch all Entertainment posts
    List<EntertainmentPost> findByCategoryOrderByDatePostedDesc(String category);

    // 2️⃣ Fetch all posts ordered by date
    List<EntertainmentPost> findAllByOrderByDatePostedDesc();

    // 3️⃣ Fetch latest N posts for featured (Pageable)
    Page<EntertainmentPost> findAllByOrderByDatePostedDesc(Pageable pageable);


}

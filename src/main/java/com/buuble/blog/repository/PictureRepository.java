package com.buuble.blog.repository;

import com.buuble.blog.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PictureRepository extends JpaRepository<Picture, Integer> {
    List<Picture> findByBlogId(Integer blogId);
    //  List<Picture> saveAll(List<Picture> pictures);
    //   void deleteInBatch(List<Picture> pictures);
}

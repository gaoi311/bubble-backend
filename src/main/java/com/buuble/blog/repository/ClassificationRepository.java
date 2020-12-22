package com.buuble.blog.repository;

import com.buuble.blog.entity.Classification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ClassificationRepository extends JpaRepository<Classification, Integer> {

    List<Classification> findByUserId(Integer userId);

    @Query(value = "select classificationName from Classification where classificationId=?1")
    String findClassificationNameByClassificationId(Integer classificationId);

    Classification save(Classification classification);

    @Transactional
    void deleteByClassificationId(Integer classificationId);

    Classification findByClassificationId(Integer classificationId);

    Classification findByUserIdAndClassificationName(Integer userId,String classificaion);
}

package com.main.slideservice.repositories;

import com.main.slideservice.domain.Slide;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlideRepository extends JpaRepository<Slide, Integer> {
    boolean existsByOrderNumber(Integer orderNumber);

    @Query("SELECT s FROM Slide s WHERE (:filterBy IS NULL OR " +
            " (:filterBy = 'orderNumber' AND s.orderNumber > 0) OR " +
            " (:filterBy = 'enabled' AND s.enabled = true) OR " +
            " (:filterBy = 'disabled' AND s.enabled = false ))")
    Page<Slide> findAllSlides(@Param("filterBy") String filterBy, PageRequest pageRequest);

    List<Slide> findAllByEnabledIsTrueOrderByOrderNumberAsc();
}

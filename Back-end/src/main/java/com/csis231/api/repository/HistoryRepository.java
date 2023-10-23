package com.csis231.api.repository;

/**
 *
 * @author abdelrahmankhodr
 */

import com.csis231.api.model.History;
import com.csis231.api.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    @Query("SELECT h FROM History h WHERE h.id = :id")
    List<History> findHistoryByUser(@Param("id") User id);
}




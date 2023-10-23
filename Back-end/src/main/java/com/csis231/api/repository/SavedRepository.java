
package com.csis231.api.repository;



import com.csis231.api.model.Saved;
import com.csis231.api.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedRepository extends JpaRepository<Saved, Long>{
    @Query("SELECT s FROM Saved s WHERE s.id = :id")
    List<Saved> findSavedByUser(@Param("id") User id);
}


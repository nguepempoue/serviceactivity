package com.b2i.activitiesorganisation.repository;

import com.b2i.activitiesorganisation.model.TransversalityLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransversalityLevelRepository extends JpaRepository<TransversalityLevel, Long> {

    @Query("select t from TransversalityLevel t where t.label like concat(:label,'%')")
    List<TransversalityLevel> findTransversalityLevelByLabel(String label);
}

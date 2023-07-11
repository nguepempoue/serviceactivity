package com.b2i.activitiesorganisation.repository;

import com.b2i.activitiesorganisation.model.Frequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FrequencyRepository extends JpaRepository<Frequency, Long> {

    @Query("select f from Frequency f where f.label like concat(:label,'%')")
    List<Frequency> findFrequencyByLabel(String label);
}

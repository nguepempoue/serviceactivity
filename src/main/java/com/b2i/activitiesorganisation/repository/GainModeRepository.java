package com.b2i.activitiesorganisation.repository;

import com.b2i.activitiesorganisation.model.GainMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GainModeRepository extends JpaRepository<GainMode, Long> {

    @Query("select g from GainMode g where g.label like concat(:label,'%')")
    List<GainMode> findGainModeByLabel(String label);
}

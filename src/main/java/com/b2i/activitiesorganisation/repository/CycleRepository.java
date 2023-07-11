package com.b2i.activitiesorganisation.repository;

import com.b2i.activitiesorganisation.model.Cycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CycleRepository extends JpaRepository<Cycle, Long> {

    @Query("select c from Cycle c where c.name like concat(:name,'%')")
    List<Cycle> findCycleByName(String name);
}

package com.b2i.activitiesorganisation.repository;

import com.b2i.activitiesorganisation.model.Tontine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TontineRepository extends JpaRepository<Tontine, Long> {

    @Query("select t from Tontine t where t.name like concat(:name,'%')")
    List<Tontine> findTontineByName(String name);
}

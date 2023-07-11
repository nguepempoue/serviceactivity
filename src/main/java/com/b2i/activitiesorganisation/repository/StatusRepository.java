package com.b2i.activitiesorganisation.repository;

import com.b2i.activitiesorganisation.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

    Optional<Status> findStatusByLabel(String label);
}

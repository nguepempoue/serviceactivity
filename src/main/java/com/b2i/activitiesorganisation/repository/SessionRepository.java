package com.b2i.activitiesorganisation.repository;

import com.b2i.activitiesorganisation.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
}

package com.b2i.activitiesorganisation.repository;

import com.b2i.activitiesorganisation.model.DraweeForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DraweeFormRepository extends JpaRepository<DraweeForm, Long> {
}

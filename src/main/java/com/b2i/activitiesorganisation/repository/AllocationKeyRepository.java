package com.b2i.activitiesorganisation.repository;

import com.b2i.activitiesorganisation.model.AllocationKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllocationKeyRepository extends JpaRepository<AllocationKey, Long> {

}

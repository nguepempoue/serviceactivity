package com.b2i.activitiesorganisation.repository;

import com.b2i.activitiesorganisation.model.TontineMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TontineMemberRepository extends JpaRepository<TontineMember, Long> {
}

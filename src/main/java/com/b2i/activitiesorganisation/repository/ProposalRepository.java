package com.b2i.activitiesorganisation.repository;

import com.b2i.activitiesorganisation.model.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {
}

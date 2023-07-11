package com.b2i.activitiesorganisation.repository;

import com.b2i.activitiesorganisation.model.ReceivingParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReceivingPartyRepository extends JpaRepository<ReceivingParty, Long> {

    Optional<ReceivingParty> findReceivingPartyByLabel(String label);
}

package com.b2i.activitiesorganisation.repository;

import com.b2i.activitiesorganisation.model.PassiveIncomeFundAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassiveIncomeFundAccountRepository extends JpaRepository<PassiveIncomeFundAccount, Long> {
}

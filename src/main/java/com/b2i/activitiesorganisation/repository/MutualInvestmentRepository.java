package com.b2i.activitiesorganisation.repository;

import com.b2i.activitiesorganisation.model.MutualInvestment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MutualInvestmentRepository extends JpaRepository<MutualInvestment, Long> {

    @Query("select m from MutualInvestment m join m.offers o where o.id=:idoffer")
    Optional<MutualInvestment> findMutualInvestmentByIdSubscriptionOffer(Long idoffer);

    @Query("select m from MutualInvestment m join m.refunds r where r.id=:idRefund")
    Optional<MutualInvestment> findMutualInvestmentByIdRefund(Long idRefund);

    @Query("select m from MutualInvestment m where m.name like concat(:name,'%')")
    List<MutualInvestment> findMutualInvestmentByName(String name);

    @Query("select m from MutualInvestment m join m.securityDeposits s where s.id=:idSecurityDeposit")
    Optional<MutualInvestment> findMutualInvestmentByIdSecurityDeposit(Long idSecurityDeposit);
}

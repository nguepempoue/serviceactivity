package com.b2i.activitiesorganisation.service.Schedule;

import com.b2i.activitiesorganisation.constant.PaymentEnum;
import com.b2i.activitiesorganisation.model.MutualInvestment;
import com.b2i.activitiesorganisation.model.Refund;
import com.b2i.activitiesorganisation.model.SecurityDeposit;
import com.b2i.activitiesorganisation.repository.MutualInvestmentRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    private final MutualInvestmentRepository mutualInvestmentRepository;

    public ScheduleService(MutualInvestmentRepository mutualInvestmentRepository) {
        this.mutualInvestmentRepository = mutualInvestmentRepository;
    }

    @Scheduled(cron = "00 00 00 * * *")
//    @Scheduled(cron = "*/2 * * * * *")
    public void changeMutualInvestmentIfExpired(){

        List<MutualInvestment> mutualInvestments = this.mutualInvestmentRepository.findAll();

        LocalDate actualDate = LocalDate.now();

        mutualInvestments.forEach(mutualInvestment -> {

            if(mutualInvestment.getRefunds().stream().max(Comparator.comparing(Refund::getId)).isPresent()){

                if(mutualInvestment.getRefundStatus() == PaymentEnum.UNPAID){

                    Refund refund = mutualInvestment.getRefunds().stream().max(Comparator.comparing(Refund::getId)).get();

                    if(refund.getRefundDate().isBefore(actualDate)){
//                    if(refund.getRefundDate().isEqual(actualDate)){

                        mutualInvestment.setRefundStatus(PaymentEnum.EXPIRED);

                        mutualInvestment.getRefunds().stream().filter(r -> r.getRefundStatus() == PaymentEnum.UNPAID).forEach(myRefund ->{

                            myRefund.setRefundStatus(PaymentEnum.EXPIRED);

                        });
                    }
                }else if(mutualInvestment.getRefundStatus() == PaymentEnum.RELAUNCHED){

                    Refund refund = mutualInvestment.getRefunds().stream().max(Comparator.comparing(Refund::getId)).get();

                    if(refund.getRefundDate().isBefore(actualDate)){
//                    if(refund.getRefundDate().isEqual(actualDate)){

                        mutualInvestment.setRefundStatus(PaymentEnum.BLOCKED);

                        long totalCaution = mutualInvestment.getSecurityDeposits().stream().mapToLong(SecurityDeposit::getAmount).sum();

                        mutualInvestment.getSecurityDeposits().forEach(securityDeposit -> {

                            securityDeposit.setAmountToPay(securityDeposit.getAmount() * mutualInvestment.getAmountToBeRefunded() / totalCaution);

                            securityDeposit.setRefundStatus(PaymentEnum.UNPAID);

                        });

                        mutualInvestment.getRefunds().stream().filter(r -> r.getRefundStatus() == PaymentEnum.UNPAID).forEach(myRefund ->{

                            myRefund.setRefundStatus(PaymentEnum.EXPIRED);

                        });
                    }
                }
            }

            this.mutualInvestmentRepository.save(mutualInvestment);
        });
    }
}

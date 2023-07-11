package com.b2i.activitiesorganisation.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MainBootstrap implements CommandLineRunner {

    @Autowired
    private FrequencyBootstrap frequencyBootstrap;

    @Autowired
    private TransversalityLevelBootstrap transversalityLevelBootstrap;

    @Autowired
    private GainModeBootstrap gainModeBootstrap;

    @Autowired
    private StatusBootstrap statusBootstrap;

    @Autowired
    private PenaltyTypeBootstrap penaltyTypeBootstrap;

    @Autowired
    private PaymentStatusBootstrap paymentStatusBootstrap;

    @Autowired
    private PaymentMethodBootstrap paymentMethodBootstrap;

    @Autowired
    private DraweeFormBootstrap draweeFormBootstrap;

    @Autowired
    private RefundTypeBootstrap refundTypeBootstrap;

    @Autowired
    private ProfitabilityTypeBootstrap profitabilityTypeBootstrap;

    @Autowired
    private ReceivingPartyBootstrap receivingPartyBootstrap;

    @Autowired
    private RiskProfileBootstrap riskProfileBootstrap;

    @Override
    public void run(String... args) throws Exception {

        frequencyBootstrap.seed();
        transversalityLevelBootstrap.seed();
        gainModeBootstrap.seed();
        statusBootstrap.seed();
        penaltyTypeBootstrap.seed();
        paymentStatusBootstrap.seed();
        paymentMethodBootstrap.seed();
        draweeFormBootstrap.seed();
        refundTypeBootstrap.seed();
        profitabilityTypeBootstrap.seed();
        receivingPartyBootstrap.seed();
        riskProfileBootstrap.seed();
    }
}

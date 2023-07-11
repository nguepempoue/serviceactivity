package com.b2i.activitiesorganisation.service.PassiveIncomeFundAccount;

import com.b2i.activitiesorganisation.model.PassiveIncomeFundAccount;
import com.b2i.activitiesorganisation.repository.PassiveIncomeFundAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class PassiveIncomeFundAccountServiceImplementation implements  PassiveIncomeFundAccountService{

    private final PassiveIncomeFundAccountRepository passiveIncomeFundAccountRepository;

    public PassiveIncomeFundAccountServiceImplementation(PassiveIncomeFundAccountRepository passiveIncomeFundAccountRepository) {
        this.passiveIncomeFundAccountRepository = passiveIncomeFundAccountRepository;
    }

    @Override
    public PassiveIncomeFundAccount createPassiveIncomeFundAccount(PassiveIncomeFundAccount passiveIncomeFundAccount) {
        return passiveIncomeFundAccountRepository.save(passiveIncomeFundAccount);
    }

    @Override
    public Long countAll() {

        // COUNT ALL
        return passiveIncomeFundAccountRepository.count();
    }
}

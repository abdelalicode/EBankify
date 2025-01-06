package com.youbanking.ebankify.loanApp;

import com.youbanking.ebankify.exception.NotEligibleException;
import com.youbanking.ebankify.exception.NotFoundException;
import com.youbanking.ebankify.user.User;
import com.youbanking.ebankify.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class LoanAppService {


    private final UserRepository userRepository;
    private LoanAppRepository loanAppRepository;

    public LoanAppService(LoanAppRepository loanAppRepository, UserRepository userRepository) {
        this.loanAppRepository = loanAppRepository;
        this.userRepository = userRepository;
    }


    public LoanApp applyForLoan(LoanApp loanApp, Long clientId) {
        User client = userRepository.findById(clientId).orElseThrow( () -> new NotFoundException("No user found"));


        if(client.getAge() < 18) {
            throw new NotEligibleException("Not eligible for loan: You must be 18 years old or higher");
        }
        if (client.getMemberSince().isAfter(LocalDateTime.now().minusMonths(6))) {
            throw new NotEligibleException("Not eligible for loan: Must be a member for at least 6 months.");
        }

        if(client.getAssets().isEmpty()) {
            throw new NotEligibleException("Not eligible for loan: No assets found");
        }

        loanApp.setClient(client);
        return loanAppRepository.save(loanApp);
    }
}

package coupon_system.services;

import coupon_system.entities.Income;
import coupon_system.repositories.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;

    @Autowired
    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public void storeIncome(Income income) {
        incomeRepository.save(income);
    }

    public Collection<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    public Collection<Income> getCompanyIncomes(long companyId) {
        return incomeRepository.findCompanyIncomes(companyId);
    }

    public Collection<Income> getCustomerIncomes(long customerId) {
        return incomeRepository.findCustomerIncomes(customerId);
    }

}

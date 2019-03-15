package coupon_system.services;

import coupon_system.enums.ClientType;
import coupon_system.repositories.CompanyRepository;
import coupon_system.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoginService {

    private CompanyRepository companyRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public LoginService(CompanyRepository companyRepository, CustomerRepository customerRepository) {
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
    }

    public ResponseEntity<?> login(User user) {

        switch (user.getClientType()) {
            case ADMIN:

            case COMPANY:

            case CUSTOMER:

            default:
                return new ResponseEntity<>("Login failed, please try again.", HttpStatus.BAD_REQUEST);
        }
    }
}

class User {
    private String name;
    private String password;
    private ClientType clientType;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public ClientType getClientType() {
        return clientType;
    }
}

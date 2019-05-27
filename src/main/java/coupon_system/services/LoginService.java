package coupon_system.services;

import coupon_system.exceptions.LoginFailedException;
import coupon_system.repositories.UserRepository;
import coupon_system.utilities.DailyExpirationTask;
import coupon_system.utilities.PasswordEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginService {

    private final UserRepository userRepository;
    private final DailyExpirationTask task;
    private boolean firstCleaning = true;

    @Autowired
    public LoginService(UserRepository userRepository,
                        DailyExpirationTask task) {
        this.userRepository = userRepository;
        this.task = task;
    }

    public long login(String username,
                      String password) throws LoginFailedException {

        if (firstCleaning) task.start();
        firstCleaning = false;

        /**
         * Encrypting password
         */
        String encryptedPassword = PasswordEncryption.getEncrypt(password);

        return userRepository.findByNameAndPassword(username, encryptedPassword)
                .orElseThrow(() -> new LoginFailedException("Authorization is failed, please try again.")).getId();
    }
}

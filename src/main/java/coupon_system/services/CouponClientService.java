package coupon_system.services;

import coupon_system.exceptions.LoginFailedException;
import org.springframework.stereotype.Service;

@Service
public abstract class CouponClientService {
    public abstract CouponClientService login(String username,
                                              String password) throws LoginFailedException;
}

package coupon_system.services;

import coupon_system.enums.ClientType;
import org.springframework.stereotype.Component;

@Component
public interface CouponClientService {
    CouponClientService login(String username, String password, ClientType clientType);
}

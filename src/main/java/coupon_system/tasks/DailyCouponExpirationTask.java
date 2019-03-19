package coupon_system.tasks;

import coupon_system.repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DailyCouponExpirationTask extends Thread {

    private final CouponRepository couponRepository;

    @Autowired
    public DailyCouponExpirationTask(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    private boolean active = true;

    @Override
    public void run() {
        while (active) {
            couponRepository.deleteExpiredCoupons();
            active = false;
        }
    }
}

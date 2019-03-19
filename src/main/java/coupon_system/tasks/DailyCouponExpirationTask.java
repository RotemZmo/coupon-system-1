package coupon_system.tasks;

import coupon_system.repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class DailyCouponExpirationTask extends Thread {

    private final CouponRepository couponRepository;

    @Autowired
    public DailyCouponExpirationTask(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public void run() {
        while (true) {
            try {
                couponRepository.deleteExpiredCoupons();
                TimeUnit.DAYS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

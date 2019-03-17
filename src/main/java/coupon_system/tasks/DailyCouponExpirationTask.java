package coupon_system.tasks;

import coupon_system.repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class DailyCouponExpirationTask implements Runnable {

    private CouponRepository couponRepository;

    @Autowired
    public DailyCouponExpirationTask(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void startTask() {
        this.active = true;
    }

    public void stopTask() {
        this.active = false;
    }

    @Override
    public void run() {
        while (active) {
            try {
                couponRepository.deleteExpiredCoupons();
                TimeUnit.DAYS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

package coupon_system.repositories;

import coupon_system.entities.Token;
import coupon_system.enums.ClientType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Token findByToken(String token);

    Token findByClientTypeAndToken(ClientType clientType, String token);

    @Modifying
    @Transactional
    void deleteAllByClientTypeAndUserId(ClientType clientType, long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Token t WHERE t.expDate < CURRENT_DATE")
    void deleteExpiredTokens();
}

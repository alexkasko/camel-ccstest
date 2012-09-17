package ru.pensionsoft.rostelecom.ccstest.pa;

import org.apache.camel.Handler;
import org.springframework.stereotype.Service;
import ru.pensionsoft.schemas.rostelecom.ccstest.CustomerCurrentBalanceRqType;

import java.math.BigDecimal;

/**
 * Stub accessor for external data source. May contain arbitrary logic.
 *
 * @author alexey
 * Date: 9/16/12
 */

@Service("externalSourceAccessor")
public class ExternalSourceAccessor {
    /**
     * @param body request body
     * @return constant value
     */
    @Handler
    public BigDecimal accessCustomerBalance(CustomerCurrentBalanceRqType body) {
        return new BigDecimal("42.142");
    }
}

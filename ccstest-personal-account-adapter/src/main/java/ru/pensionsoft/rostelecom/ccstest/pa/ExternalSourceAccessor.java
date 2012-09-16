package ru.pensionsoft.rostelecom.ccstest.pa;

import org.apache.camel.Handler;
import org.springframework.stereotype.Service;
import ru.pensionsoft.schemas.rostelecom.ccstest.CustomerCurrentBalanceRqType;

import java.math.BigDecimal;

/**
 * User: alexey
 * Date: 9/16/12
 */

@Service("externalSourceAccessor")
public class ExternalSourceAccessor {
    @Handler
    public BigDecimal accessCustomerBalance(CustomerCurrentBalanceRqType body) {
        return new BigDecimal("42.142");
    }
}

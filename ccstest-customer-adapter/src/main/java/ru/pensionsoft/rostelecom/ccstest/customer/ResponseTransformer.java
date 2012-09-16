package ru.pensionsoft.rostelecom.ccstest.customer;

import org.apache.camel.Handler;
import org.springframework.stereotype.Service;
import ru.pensionsoft.schemas.rostelecom.ccstest.CustomerCurrentBalanceRsType;

import java.math.BigDecimal;

/**
 * User: alexey
 * Date: 9/16/12
 */

@Service("responseTransformer")
public class ResponseTransformer {
    @Handler
    public CustomerCurrentBalanceRsType transform(BigDecimal resp) {
        return new CustomerCurrentBalanceRsType(resp);
    }
}

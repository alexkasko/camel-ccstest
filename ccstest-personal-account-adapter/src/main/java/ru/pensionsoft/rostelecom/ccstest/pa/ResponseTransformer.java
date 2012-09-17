package ru.pensionsoft.rostelecom.ccstest.pa;

import org.apache.camel.Handler;
import org.springframework.stereotype.Service;
import ru.pensionsoft.schemas.rostelecom.ccstest.CustomerCurrentBalanceRsType;

import java.math.BigDecimal;

/**
 * Data source response transformer
 *
 * @author alexey
 * Date: 9/16/12
 */

@Service("responseTransformer")
public class ResponseTransformer {
    /**
     * @param data data from source
     * @return JAXB response
     */
    @Handler
    public CustomerCurrentBalanceRsType transform(BigDecimal data) {
        return new CustomerCurrentBalanceRsType(data);
    }
}

package ru.pensionsoft.rostelecom.ccstest.customer;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;
import ru.pensionsoft.schemas.rostelecom.ccstest.CustomerCurrentBalanceRqType;
import ru.pensionsoft.schemas.rostelecom.ccstest.CustomerCurrentBalanceRsType;

import static ru.pensionsoft.rostelecom.ccstest.common.JaxbDataFormatFactory.jaxb;


/**
 * User: alexey
 * Date: 9/16/12
 */

@Service("customerRouteBuilder")
public class CustomerRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("jms:customerQueue")
                .unmarshal(jaxb(CustomerCurrentBalanceRqType.class))
                .beanRef("externalSourceAccessor")
                .beanRef("responseTransformer")
                .marshal(jaxb(CustomerCurrentBalanceRsType.class));
    }
}

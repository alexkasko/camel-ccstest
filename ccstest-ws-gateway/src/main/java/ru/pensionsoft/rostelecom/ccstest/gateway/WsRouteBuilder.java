package ru.pensionsoft.rostelecom.ccstest.gateway;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;
import ru.pensionsoft.rostelecom.ccstest.common.InputMessageContentListTransformer;
import ru.pensionsoft.rostelecom.ccstest.common.JsonHeaderAggregationStrategy;
import ru.pensionsoft.rostelecom.ccstest.common.OutputMessageContentListTransformer;
import ru.pensionsoft.schemas.rostelecom.ccstest.CustomerCurrentBalanceRqType;
import ru.pensionsoft.schemas.rostelecom.ccstest.CustomerCurrentBalanceRsType;

import static org.apache.camel.builder.script.ScriptBuilder.javaScript;
import static ru.pensionsoft.rostelecom.ccstest.common.JaxbDataFormatFactory.jaxb;

/**
 * User: alexey
 * Date: 9/14/12
 */

@Service("wsRouteBuilder")
public class WsRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("cxf:bean:customerCurrentBalanceEndpoint")
                .bean(new InputMessageContentListTransformer())
                .setHeader("ccstest_systemId", simple("${body.context.systemId}"))
                .setHeader("ccstest_userLogin", simple("${body.context.userLogin}"))
                .enrich("ejb:externalAuthorizationService#ru.pensionsoft.rostelecom.ccstest.ext.auth.ExternalAuthorizationService?method=roles(${header.ccstest_systemId},${header.ccstest_userLogin})",
                        new JsonHeaderAggregationStrategy("ccstest_roles"))
                // todo: corner cases
                .setHeader("ccstest_content", javaScript("request.body.customerEntityId ? 'customer' : 'personal_account'"))
                .marshal(jaxb(CustomerCurrentBalanceRqType.class))
                .choice()
                    .when(header("ccstest_content").isEqualTo("customer"))
                        .inOut("jms:customerQueue")
                        .unmarshal(jaxb(CustomerCurrentBalanceRsType.class))
                        .bean(new OutputMessageContentListTransformer())
                    .when(header("ccstest_content").isEqualTo("personal_account"))
                        .to("jms:personalAccountQueue")
                        .unmarshal(jaxb(CustomerCurrentBalanceRsType.class))
                        .bean(new OutputMessageContentListTransformer());
    }
}

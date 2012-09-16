package ru.pensionsoft.rostelecom.ccstest.ext.auth;

import javax.ejb.Stateless;
import java.util.Arrays;
import java.util.List;

/**
 * User: alexey
 * Date: 9/16/12
 */
@Stateless(mappedName = "externalAuthorizationService")
public class ExternalAuthorizationServiceTestImpl implements ExternalAuthorizationService {
    @Override
    public List<String> roles(String systemId, String userLogin) {
        return Arrays.asList("esb_admin", "esb_user");
    }
}

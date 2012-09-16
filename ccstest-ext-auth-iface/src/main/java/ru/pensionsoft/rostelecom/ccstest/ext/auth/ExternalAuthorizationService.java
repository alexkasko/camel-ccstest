package ru.pensionsoft.rostelecom.ccstest.ext.auth;

import javax.ejb.Remote;
import java.util.List;

/**
 * User: alexey
 * Date: 9/16/12
 */

@Remote
public interface ExternalAuthorizationService {
    List<String> roles(String systemId, String userLogin);
}

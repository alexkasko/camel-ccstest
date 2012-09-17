package ru.pensionsoft.rostelecom.ccstest.ext.auth;

import javax.ejb.Remote;
import java.util.List;

/**
 * Interface for possible existed external authorization service
 *
 * @author alexey
 * Date: 9/16/12
 */

@Remote
public interface ExternalAuthorizationService {
    /**
     * @param systemId external system id
     * @param userLogin user login
     * @return roles list
     */
    List<String> roles(String systemId, String userLogin);
}

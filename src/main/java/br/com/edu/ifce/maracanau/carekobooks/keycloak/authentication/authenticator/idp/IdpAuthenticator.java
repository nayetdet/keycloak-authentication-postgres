package br.com.edu.ifce.maracanau.carekobooks.keycloak.authentication.authenticator.idp;

import br.com.edu.ifce.maracanau.carekobooks.keycloak.authentication.mapper.UserMapper;
import br.com.edu.ifce.maracanau.carekobooks.keycloak.authentication.persistence.dao.UserDAOFactory;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.authenticators.broker.AbstractIdpAuthenticator;
import org.keycloak.authentication.authenticators.broker.util.SerializedBrokeredIdentityContext;
import org.keycloak.broker.provider.BrokeredIdentityContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

public class IdpAuthenticator extends AbstractIdpAuthenticator {

    private final UserDAOFactory userDAOFactory;

    public IdpAuthenticator(String jdbcUrl, String username, String password) {
        userDAOFactory = new UserDAOFactory(jdbcUrl, username, password);
    }

    @Override
    protected void authenticateImpl(AuthenticationFlowContext authenticationFlowContext, SerializedBrokeredIdentityContext serializedBrokeredIdentityContext, BrokeredIdentityContext brokeredIdentityContext) {
        var brokerEmail = brokeredIdentityContext.getEmail();
        if (brokerEmail == null || brokerEmail.isEmpty()) {
            authenticationFlowContext.failure(AuthenticationFlowError.IDENTITY_PROVIDER_ERROR);
            return;
        }

        var existingUser = authenticationFlowContext.getSession().users().getUserByEmail(authenticationFlowContext.getRealm(), brokerEmail);
        if (existingUser == null) {
            var newUser = authenticationFlowContext
                    .getSession()
                    .users()
                    .addUser(authenticationFlowContext.getRealm(), brokeredIdentityContext.getUsername());

            newUser.setEnabled(true);
            newUser.setEmail(brokerEmail);

            if (!userDAOFactory.getUserDAO().save(UserMapper.from(newUser))) {
                authenticationFlowContext.failure(AuthenticationFlowError.IDENTITY_PROVIDER_ERROR);
                return;
            }

            authenticationFlowContext.setUser(newUser);
            authenticationFlowContext.success();
            return;
        }

        authenticationFlowContext.setUser(existingUser);
        authenticationFlowContext.success();
    }

    @Override
    protected void actionImpl(AuthenticationFlowContext authenticationFlowContext, SerializedBrokeredIdentityContext serializedBrokeredIdentityContext, BrokeredIdentityContext brokeredIdentityContext) {
        // Intentionally left blank: no additional action required after authentication
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        return true;
    }

    @Override
    public void close() {
        userDAOFactory.close();
    }

}

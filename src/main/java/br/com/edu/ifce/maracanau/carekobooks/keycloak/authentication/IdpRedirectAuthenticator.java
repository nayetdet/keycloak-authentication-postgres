package br.com.edu.ifce.maracanau.carekobooks.keycloak.authentication;

import jakarta.ws.rs.core.Response;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.authenticators.broker.AbstractIdpAuthenticator;
import org.keycloak.authentication.authenticators.broker.util.SerializedBrokeredIdentityContext;
import org.keycloak.broker.provider.BrokeredIdentityContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import java.net.URI;

public class IdpRedirectAuthenticator extends AbstractIdpAuthenticator {

    @Override
    protected void authenticateImpl(AuthenticationFlowContext authenticationFlowContext, SerializedBrokeredIdentityContext serializedBrokeredIdentityContext, BrokeredIdentityContext brokeredIdentityContext) {
        var redirectUrl = authenticationFlowContext.getAuthenticatorConfig() != null && authenticationFlowContext.getAuthenticatorConfig().getConfig() != null
                ? authenticationFlowContext.getAuthenticatorConfig().getConfig().get("redirectUrl")
                : null;

        if (redirectUrl == null || redirectUrl.isEmpty()) {
            authenticationFlowContext.failure(AuthenticationFlowError.IDENTITY_PROVIDER_ERROR);
            return;
        }

        var brokerEmail = brokeredIdentityContext.getEmail();
        if (brokerEmail == null || brokerEmail.isEmpty()) {
            authenticationFlowContext.failure(AuthenticationFlowError.IDENTITY_PROVIDER_ERROR);
            return;
        }

        var existingUser = authenticationFlowContext.getSession().users().getUserByEmail(authenticationFlowContext.getRealm(), brokerEmail);
        if (existingUser == null) {
            var redirect = Response.seeOther(URI.create(redirectUrl)).build();
            authenticationFlowContext.challenge(redirect);
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

}

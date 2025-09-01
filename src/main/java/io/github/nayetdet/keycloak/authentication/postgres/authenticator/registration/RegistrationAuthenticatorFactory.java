package io.github.nayetdet.keycloak.authentication.postgres.authenticator.registration;

import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.List;

import static org.keycloak.models.AuthenticationExecutionModel.Requirement.DISABLED;
import static org.keycloak.models.AuthenticationExecutionModel.Requirement.REQUIRED;

public class RegistrationAuthenticatorFactory implements AuthenticatorFactory {

    private static final AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {DISABLED, REQUIRED};

    @Override
    public String getId() {
        return "register";
    }

    @Override
    public String getDisplayType() {
        return "Register User";
    }

    @Override
    public String getHelpText() {
        return "Creates the user in the local system if they do not exist";
    }

    @Override
    public String getReferenceCategory() {
        return "register";
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return List.of();
    }

    @Override
    public Authenticator create(KeycloakSession keycloakSession) {
        return new RegistrationAuthenticator(keycloakSession);
    }

    @Override
    public void init(Config.Scope scope) {
        // Not initialization required
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
        // No post-initialization required
    }

    @Override
    public void close() {
        // No cleanup required
    }

}

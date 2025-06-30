package br.com.edu.ifce.maracanau.carekobooks.keycloak.authentication;

import com.google.auto.service.AutoService;
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

@AutoService(AuthenticatorFactory.class)
public class IdpRedirectAuthenticatorFactory implements AuthenticatorFactory {

    private static final AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {DISABLED, REQUIRED};
    private static final IdpRedirectAuthenticator AUTHENTICATOR = new IdpRedirectAuthenticator();

    @Override
    public String getId() {
        return "idp-redirect";
    }

    @Override
    public String getDisplayType() {
        return "Conditional Identity Provider Redirect";
    }

    @Override
    public String getHelpText() {
        return "Redirects the user to an external URL if the user does not exist after logging in with an Identity Provider";
    }

    @Override
    public String getReferenceCategory() {
        return "idp-redirect";
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
        return List.of(new ProviderConfigProperty(
                "redirectUrl",
                "Redirect URL",
                "Target URL for redirect when the user does not exist locally",
                ProviderConfigProperty.STRING_TYPE,
                "")
        );
    }

    @Override
    public Authenticator create(KeycloakSession keycloakSession) {
        return AUTHENTICATOR;
    }

    @Override
    public void init(Config.Scope scope) {
        // No initialization required
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

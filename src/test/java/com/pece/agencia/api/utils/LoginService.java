package com.pece.agencia.api.utils;

import com.nimbusds.oauth2.sdk.*;
import com.nimbusds.oauth2.sdk.auth.ClientSecretBasic;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.Tokens;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class LoginService {
    @Value("${keycloak.base-url}")
    private String keycloakBaseUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.secret}")
    private String clientSecret;

    public String login(String username, String password) throws Exception {
        String tokenUrl = keycloakBaseUrl + "/realms/" + realm + "/protocol/openid-connect/token";
        URI tokenEndpoint = new URI(tokenUrl);

        ClientID clientID = new ClientID(clientId);
        Secret secret = new Secret(clientSecret);
        ClientSecretBasic clientAuth = new ClientSecretBasic(clientID, secret);

        ResourceOwnerPasswordCredentialsGrant passwordGrant = new ResourceOwnerPasswordCredentialsGrant(username, new Secret(password));
        TokenRequest request = new TokenRequest(tokenEndpoint, clientAuth, passwordGrant);

        HTTPRequest httpRequest = request.toHTTPRequest();
        HTTPResponse httpResponse = httpRequest.send();
        TokenResponse response = TokenResponse.parse(httpResponse);

        if (!response.indicatesSuccess()) {
            TokenErrorResponse error = response.toErrorResponse();
            throw new RuntimeException("Erro ao obter token: " + error.getErrorObject().getDescription());
        }
        AccessTokenResponse success = response.toSuccessResponse();
        Tokens tokens = success.getTokens();
        AccessToken accessToken = tokens.getAccessToken();
        return accessToken.getValue();
    }
}


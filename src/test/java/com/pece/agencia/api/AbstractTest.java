package com.pece.agencia.api;

import com.pece.agencia.api.utils.TokenForParameterResolver;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import eu.rekawek.toxiproxy.Proxy;
import eu.rekawek.toxiproxy.ToxiproxyClient;
import eu.rekawek.toxiproxy.model.Toxic;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.ToxiproxyContainer;
import org.wiremock.integrations.testcontainers.WireMockContainer;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;

import static java.util.Collections.emptyList;


@SpringBootTest
@ExtendWith(TokenForParameterResolver.class)
@AutoConfigureMockMvc
public abstract class AbstractTest {


    @ClassRule
    public static Network network = Network.newNetwork();


    private static String wireMockMapping(String mapping) {
        try {
            File file = new File("other/mock-server/" + mapping).getAbsoluteFile();
            return Files.readString(file.toPath());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static File wireMockExtension(String extension) {
        URL extensionUrl = AbstractTest.class.getResource("/" + extension);
        if (extensionUrl == null) {
            throw new RuntimeException("No file named " + extension + " found in classpath (make sure to run mvn clean test-compile)");
        }
        return new File(extensionUrl.getFile());
    }

    private static ToxiproxyContainer toxiproxy = new ToxiproxyContainer("ghcr.io/shopify/toxiproxy:2.5.0").withNetwork(network);

    private static GenericContainer<?> stripeMock = new GenericContainer<>("adrienverge/localstripe:1.15.9").withExposedPorts(8420)
                                                                                                                           .withNetwork(network)
                                                                                                                           .withNetworkAliases("stripe-mock");

    private static KeycloakContainer keycloakContainer = new KeycloakContainer("quay.io/keycloak/keycloak:26.3.3")
        .withRealmImportFile("pece-realm.json")
        .withRealmImportFile("pece-users-0.json")
        .withNetwork(network)
        .withNetworkAliases("keycloak");

    private static WireMockContainer wiremockContainer = new WireMockContainer("wiremock/wiremock:latest")
        .withMappingFromJSON("hotel", wireMockMapping("hotel.json"))
        .withMappingFromJSON("hotel-ng.create", wireMockMapping("hotel-ng-create.json"))
        .withMappingFromJSON("hotel-ng.get", wireMockMapping("hotel-ng-get.json"))
        .withMappingFromJSON("veiculo", wireMockMapping("locadora-veiculo.json"))
        .withMappingFromJSON("area", wireMockMapping("companhia-area.json"))
        .withExtensions(emptyList(), wireMockExtension("wiremock-state-extension-standalone.jar"))
        .withNetwork(network)
        .withNetworkAliases("mocks");

    protected static Proxy stripeToxyproxy;
    protected static Proxy keycloakToxyproxy;
    protected static Proxy plataformaHotelRegularToxyproxy;
    protected static Proxy plataformaHotelNgToxyproxy;
    protected static Proxy plataformaLocadoraVeiculoToxyproxy;
    protected static Proxy plataformaCompanhiaAereaToxyproxy;


    private static void startContainers() throws Exception {
        stripeMock.start();
        wiremockContainer.start();
        keycloakContainer.start();
        toxiproxy.start();
    }

    private static void startProxies() throws Exception {
        final ToxiproxyClient toxiproxyClient = new ToxiproxyClient(toxiproxy.getHost(), toxiproxy.getControlPort());
        stripeToxyproxy = toxiproxyClient.createProxy("stripe", listeningOn(8666), "stripe-mock:8420");
        keycloakToxyproxy = toxiproxyClient.createProxy("keycloak", listeningOn(8667), "keycloak:8080");
        plataformaHotelRegularToxyproxy = toxiproxyClient.createProxy("plataforma-hotel-regular", listeningOn(8668), "mocks:8080");
        plataformaLocadoraVeiculoToxyproxy = toxiproxyClient.createProxy("plataforma-locadora-veiculo", listeningOn(8669), "mocks:8080");
        plataformaCompanhiaAereaToxyproxy = toxiproxyClient.createProxy("plataforma-companhia-aerea", listeningOn(8670), "mocks:8080");
        plataformaHotelNgToxyproxy = toxiproxyClient.createProxy("plataforma-hotel-ngr", listeningOn(8671), "mocks:8080");

    }

    private static String proxiedEntryPointFor(int port) {
        return "http://" + toxiproxy.getHost() + ":" + toxiproxy.getMappedPort(port);
    }

    private static String listeningOn(int port) {
        return "0.0.0.0:" + port;
    }


    @DynamicPropertySource
    static void setupMocks(DynamicPropertyRegistry registry) throws Exception {
        startContainers();
        startProxies();

        registry.add("stripe.base-url", () -> proxiedEntryPointFor(8666));
        registry.add("keycloak.base-url", () -> proxiedEntryPointFor(8667));
        registry.add("plataforma.hotel.regular.url", () -> proxiedEntryPointFor(8668));
        registry.add("plataforma.locadora-veiculos.url", () -> proxiedEntryPointFor(8669));
        registry.add("plataforma.empresa-aerea.url", () -> proxiedEntryPointFor(8670));
        registry.add("plataforma.hotel.ng.url", () -> proxiedEntryPointFor(8671));
    }

    @Autowired
    protected MockMvc mockMvc;

    @AfterEach
    public void resetProxies() throws Exception {
        resetProxy(stripeToxyproxy);
        resetProxy(keycloakToxyproxy);
        resetProxy(plataformaHotelRegularToxyproxy);
        resetProxy(plataformaHotelNgToxyproxy);
        resetProxy(plataformaLocadoraVeiculoToxyproxy);
        resetProxy(plataformaCompanhiaAereaToxyproxy);
    }

    private void resetProxy(Proxy proxy) throws Exception {
        proxy.enable();
        for (Toxic toxic : proxy.toxics().getAll()) {
            toxic.remove();
        }
    }
}

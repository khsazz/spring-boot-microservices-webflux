package com.fewservices.vehicleaggregator;


import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.groups.Tuple.tuple;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class VehicleLocationIntegrationTest {

    @LocalServerPort
    private int port;


    @Autowired
    private WebTestClient webTestClient;

    private static WireMockServer wireMockBusServer;
    private static WireMockServer wireMockTrainServer;

    @BeforeAll
    static void setup() {
        wireMockBusServer = new WireMockServer(9561); // Bus Live Fare & Location Service mock server
        wireMockTrainServer = new WireMockServer(9562); // Train Live Location Service mock server
        wireMockBusServer.start();
        wireMockTrainServer.start();

        WireMock.configureFor("localhost", 9561);
        wireMockTrainServer.stubFor(get(urlPathEqualTo("/location-stream"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/event-stream")
                        .withBody(
                                "data: {\"id\":101,\"location\":{\"lat\":21,\"lng\":21}}\n\n" +
                                        "data: {\"id\":102,\"location\":{\"lat\":22,\"lng\":22}}\n\n")));

        WireMock.configureFor("localhost", 9562);
        wireMockBusServer.stubFor(get(urlPathEqualTo("/location-stream"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/event-stream")
                        .withBody(
                                "data: {\"id\":1,\"location\":{\"lat\":11,\"lng\":11}}\n\n" +
                                        "data: {\"id\":2,\"location\":{\"lat\":12,\"lng\":12}}\n\n")));

    }

    @AfterAll
    static void teardown() {
        wireMockBusServer.stop();
        wireMockTrainServer.stop();
    }

    @DynamicPropertySource
    static void registerWireMock(DynamicPropertyRegistry registry) {
        registry.add("train.location.service.url", () -> "http://localhost:9562");
        registry.add("bus.location.service.url", () -> "http://localhost:9561");
    }

    @Test
    void testGetVehicleLocationStream() {
        webTestClient.get().uri("/vehicle-location-stream")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
                .returnResult(LocationEvent.class)
                .getResponseBody()
                .take(4)
                .collectList()
                .doOnNext(events -> {
                    assertThat(events).hasSize(4);
                    assertThat(events).extracting(LocationEvent::getType,
                                    event -> (int) event.getLocation().getLat(),
                                    event -> (int) event.getLocation().getLng())
                            .containsExactlyInAnyOrder(
                                    tuple("BUS", 11, 11),
                                    tuple("BUS", 12, 12),
                                    tuple("TRAIN", 21, 21),
                                    tuple("TRAIN", 22, 22)
                            );
                })
                .block();
    }
}

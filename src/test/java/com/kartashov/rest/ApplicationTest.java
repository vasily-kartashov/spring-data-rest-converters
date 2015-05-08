package com.kartashov.rest;

import com.kartashov.rest.entities.ConnectionStatus;
import com.kartashov.rest.entities.Unit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class ApplicationTest {

    private final static Logger logger = LoggerFactory.getLogger(ApplicationTest.class);

    @Value("${local.server.port}")
    int port;

    RestTemplate restTemplate = new RestTemplate();

    public ApplicationTest() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        restTemplate.setRequestFactory(requestFactory);
    }

    @Test
    public void testUpdatingConnectionStatus() throws URISyntaxException {

        URI unitsUri = new URI("http://localhost:" + port + "/units");

        Unit unit = new Unit();
        URI unitUri = restTemplate.postForLocation(unitsUri, unit);

        logger.info("After insert: " + restTemplate.getForEntity(unitUri, Unit.class).getBody());

        HttpEntity<StatusUpdate> update = new HttpEntity<>(new StatusUpdate(ConnectionStatus.Status.Connected));
        restTemplate.exchange(unitUri, HttpMethod.PATCH, update, Void.class);

        logger.info("After first patch: " + restTemplate.getForEntity(unitUri, Unit.class).getBody());

        restTemplate.exchange(unitUri, HttpMethod.PATCH, update, Void.class);

        logger.info("After second patch: " + restTemplate.getForEntity(unitUri, Unit.class).getBody());
    }

    public static class StatusUpdate {

        private final ConnectionStatus connectionStatus;

        public StatusUpdate(ConnectionStatus.Status status) {
            connectionStatus = new ConnectionStatus(status);
        }

        public ConnectionStatus getConnectionStatus() {
            return connectionStatus;
        }
    }
}

package com.lunch.client;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class DataClient {

    private final Client client;

    @Inject
    public DataClient(Client client) {
        this.client = client;
    }

    public Response getData(String endpoint) {
        return client.target(endpoint)
                .request(MediaType.APPLICATION_JSON_TYPE).get();
    }

}

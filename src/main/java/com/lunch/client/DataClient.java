package com.lunch.client;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Client to make external api calls
 */
public class DataClient {

    private final Client client;

    @Inject
    public DataClient(Client client) {
        this.client = client;
    }

    /**
     *
     * @param endpoint the url to make the api call to
     * @return response of the api call in json format
     */
    public Response getData(String endpoint) {
        return client.target(endpoint)
                .request(MediaType.APPLICATION_JSON_TYPE).get();
    }

}

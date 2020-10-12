package com.lunch.provider;

import com.lunch.client.DataClient;
import com.lunch.config.AppConfig;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@Slf4j
public class DataProvider {

    private final AppConfig appConfig;
    private final DataClient client;

    @Inject
    public DataProvider(AppConfig appConfig, DataClient client) {
        this.appConfig = appConfig;
        this.client = client;
    }

    public ProviderResponse getIngredients() {
        return generateProviderResponse(appConfig.getIngredientsUrl());
    }

    public ProviderResponse getRecipes() {
        return generateProviderResponse(appConfig.getRecipesUrl());
    }

    private ProviderResponse generateProviderResponse(String endpoint) {
        try {
            Response response = client.getData(endpoint);
            return generateProviderResponse(response);
        } catch (ProcessingException pex) {
            log.error("Exception calling endpoint {}", endpoint, pex);
            throw new WebApplicationException(pex, Response.Status.BAD_GATEWAY);
        }
    }

    private ProviderResponse generateProviderResponse(Response response) {
        Response.Status status = Response.Status.fromStatusCode(response.getStatus());
        String body = response.readEntity(String.class);
        if (!status.getFamily().equals(Response.Status.Family.SUCCESSFUL)) {
            log.error("Error handling api call, response status: " + status + ", response detail: " + body);
            throw new WebApplicationException("Endpoint response invalid or unavailable", Response.Status.BAD_GATEWAY);
        }
        return new ProviderResponse(true, body);
    }

}

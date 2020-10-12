package com.lunch.provider;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProviderResponse {

    private final boolean success;
    private final String message;

}

package ru.anrivlev.short_link.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class FullUrlService {
    public String getPage(
            String fullUrl
    ) {
        return RestClient
                .create()
                .get()
                .uri(fullUrl)
                .retrieve()
                .body(String.class);
    }
}

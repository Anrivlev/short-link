package ru.anrivlev.short_link.rest;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.anrivlev.short_link.entities.ShortLink;
import ru.anrivlev.short_link.service.ShortLinkService;
import ru.anrivlev.short_link.service.ShortUrlGenerator;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ShortLinkRestController {
    @Autowired
    ShortLinkService shortLinkService;

    @GetMapping(name = "/short-link/")
    public ResponseEntity<String> getNewShortLink(
            @RequestParam(name = "uuid") String uuid,
            @RequestParam(name = "url") String fullUrl,
            @RequestParam(name = "usages") Integer usages,
            @RequestParam(name = "hours") Integer hours
    ) {
        Duration duration = Duration.ofHours(hours);
        ShortLink shortLink = shortLinkService.getNewShortLink(uuid, fullUrl, usages, duration);
        return ResponseEntity.ok(shortLink.getShortUrl());
    }

    @GetMapping(name = "/{shortUrl}")
    public ResponseEntity<String> useShortLink(
            @RequestParam(name = "uuid") String uuid,
            @PathVariable(name = "shortUrl") String shortUrl
    ) {

        return null;
    }
}

package ru.anrivlev.short_link.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.anrivlev.short_link.entities.ShortLink;
import ru.anrivlev.short_link.service.ShortLinkService;

import java.time.Duration;

@RestController
public class ShortLinkRestController {
    @Autowired
    ShortLinkService shortLinkService;

    @GetMapping(value = "/get/")
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

    @GetMapping(value = "/use/{shortUrl}")
    public ResponseEntity<String> useShortLink(
            @RequestParam(name = "uuid") String uuid,
            @PathVariable(name = "shortUrl") String shortUrl
    ) {
        String page = shortLinkService.getPageWithShortLink(uuid, shortUrl);
        if (page == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(page);
    }

    @DeleteMapping(value = "/{shortUrl}")
    public ResponseEntity<Boolean> deleteShortLink(
            @RequestParam(name = "uuid") String uuid,
            @PathVariable(name = "shortUrl") String shortUrl
    ) {
        boolean isDeleted = shortLinkService.markShortUrlAsDeleted(uuid, shortUrl);
        return ResponseEntity.ok(isDeleted);
    }
}

package ru.anrivlev.short_link.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.anrivlev.short_link.model.ShortLink;
import ru.anrivlev.short_link.service.ShortLinkService;

import java.util.Optional;

@RestController
public class ShortLinkRestController {
    @Autowired
    ShortLinkService shortLinkService;

    @GetMapping(value = "/short-link/{id}")
    public ResponseEntity<ShortLink> getShortLink(
            @PathVariable(value = "id") Long id
    ) {
        Optional<ShortLink> shortLink = shortLinkService.getShortLink(id);
        return shortLink.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .notFound()
                        .build());
    }

    @PostMapping(value = "/short-link/")
    public ResponseEntity<Long> saveShortLink(
            @RequestBody ShortLink shortLink
    ) {
        ShortLink savedShortLink = shortLinkService.saveShortLink(shortLink);
        return ResponseEntity.ok(savedShortLink.getId());
    }

    @DeleteMapping(value = "/short-link/{id}")
    public ResponseEntity<Boolean> deleteShortLink(
            @PathVariable(value = "id") Long id
    ) {
        Boolean isDeleted = shortLinkService.deleteShortLink(id);
        if (!isDeleted) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(true);
    }
}

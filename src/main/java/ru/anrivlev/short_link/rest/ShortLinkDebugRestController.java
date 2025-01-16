package ru.anrivlev.short_link.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.anrivlev.short_link.entities.ShortLink;
import ru.anrivlev.short_link.service.ShortLinkService;

import java.util.Optional;

public class ShortLinkDebugRestController {
    @Autowired
    ShortLinkService shortLinkService;

    @GetMapping(value = "/debug/short-link/{id}")
    public ResponseEntity<ShortLink> getShortLink(
            @PathVariable(value = "id") Long id
    ) {
        Optional<ShortLink> shortLink = shortLinkService.getShortLink(id);
        return shortLink.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .notFound()
                        .build());
    }

    @PostMapping(value = "/debug/short-link/")
    public ResponseEntity<Integer> saveShortLink(
            @RequestBody ShortLink shortLink
    ) {
        ShortLink savedShortLink = shortLinkService.saveShortLink(shortLink);
        return ResponseEntity.ok(savedShortLink.getId());
    }

    @DeleteMapping(value = "/debug/short-link/{id}")
    public ResponseEntity<Boolean> deleteShortLink(
            @PathVariable(value = "id") Long id
    ) {
        boolean isDeleted = shortLinkService.deleteShortLink(id);
        if (!isDeleted) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(true);
    }
}

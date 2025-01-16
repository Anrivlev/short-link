package ru.anrivlev.short_link.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UUIDRestController {
    @GetMapping(value = "/uuid")
    public ResponseEntity<String> getNewUuid() {
        return ResponseEntity.ok(UUID.randomUUID().toString());
    }
}

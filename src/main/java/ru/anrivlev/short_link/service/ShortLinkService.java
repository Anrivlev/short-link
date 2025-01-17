package ru.anrivlev.short_link.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.anrivlev.short_link.entities.ShortLink;
import ru.anrivlev.short_link.repo.ShortLinkRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ShortLinkService {
    @Autowired
    private ShortLinkRepository shortLinkRepository;

    @Autowired
    ShortUrlGenerator shortUrlGenerator;

    @Autowired
    FullUrlService fullUrlService;

    @Value("${short-link.hours}")
    private Integer shortLinkMaxHours;

    public Optional<ShortLink> getShortLink(long id) {
        return shortLinkRepository.findById(id);
    }

    public ShortLink saveShortLink(ShortLink shortLink) {
        return shortLinkRepository.save(shortLink);
    }

    public boolean markShortUrlAsDeleted(long id) {
        Optional<ShortLink> shortLink = shortLinkRepository.findById(id);
        if (shortLink.isEmpty()) return false;
        shortLinkRepository.deleteById(id);
        return true;
    }

    public List<ShortLink> getShortLinks(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return shortLinkRepository.findAll(
                pageable
        ).stream().toList();
    }

    public ShortLink getNewShortLink(
            String uuid,
            String fullUrl,
            Integer maxUsages,
            Duration duration
    ) {
        Duration maxDuration = Duration.ofHours(shortLinkMaxHours);
        Duration actualDuration = duration.compareTo(maxDuration) < 0 ? duration : maxDuration;
        ShortLink shortLink = new ShortLink();
        shortLink.setFullUrl(fullUrl);
        shortLink.setDeleted(false);
        shortLink.setUsages(0);
        shortLink.setMaxUsages(maxUsages);
        shortLink.setUserId(uuid);
        LocalDateTime creationDate = LocalDateTime.now();
        shortLink.setCreationDate(creationDate);
        shortLink.setExpirationDate(creationDate.plus(actualDuration));
        ShortLink savedShortLink = shortLinkRepository.save(shortLink);
        savedShortLink.setShortUrl(shortUrlGenerator.getShortUrl(savedShortLink.getId()));
        return shortLinkRepository.save(savedShortLink);
    }

    public String getPageWithShortLink(
            String uuid,
            String shortUrl
    ) {
        int id = shortUrlGenerator.getId(shortUrl);
        Optional<ShortLink> optionalShortLink = getShortLink(id);
        if (optionalShortLink.isEmpty()) return null;
        ShortLink shortLink = optionalShortLink.get();
        if (!Objects.equals(shortLink.getUserId(), uuid)) return null;
        if (shortLink.isDeleted()) return null;
        if (shortLink.getExpirationDate().isBefore(LocalDateTime.now())) return null;
        if (shortLink.getUsages() == shortLink.getMaxUsages()) return null;
        shortLink.setUsages(shortLink.getUsages() + 1);
        saveShortLink(shortLink);
        return fullUrlService.getPage(shortLink.getFullUrl());
    }

    public Boolean markShortUrlAsDeleted(
            String uuid, String shortUrl
    ) {
        int id = shortUrlGenerator.getId(shortUrl);
        Optional<ShortLink> optionalShortLink = getShortLink(id);
        if (optionalShortLink.isEmpty()) return false;
        ShortLink shortLink = optionalShortLink.get();
        if (!Objects.equals(shortLink.getUserId(), uuid)) return false;
        shortLink.setDeleted(true);
        saveShortLink(shortLink);
        return true;
    }
}

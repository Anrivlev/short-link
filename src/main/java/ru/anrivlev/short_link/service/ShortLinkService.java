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
import java.util.Optional;

@Service
public class ShortLinkService {
    @Autowired
    private ShortLinkRepository shortLinkRepository;

    @Autowired
    ShortUrlGenerator shortUrlGenerator;

    @Value("short-link.hours")
    Integer shortLinkMaxHours;

    public Optional<ShortLink> getShortLink(long id) {
        return shortLinkRepository.findById(id);
    }

    public ShortLink saveShortLink(ShortLink shortLink) {
        return shortLinkRepository.save(shortLink);
    }

    public boolean deleteShortLink(long id) {
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
}

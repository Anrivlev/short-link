package ru.anrivlev.short_link.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.anrivlev.short_link.model.ShortLink;
import ru.anrivlev.short_link.repo.ShortLinkRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ShortLinkService {
    @Autowired
    private ShortLinkRepository shortLinkRepository;

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
}

package ru.anrivlev.short_link.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anrivlev.short_link.model.ShortLink;

@Repository
public interface ShortLinkRepository extends JpaRepository<ShortLink, Long> {
}

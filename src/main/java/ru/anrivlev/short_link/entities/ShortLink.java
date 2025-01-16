package ru.anrivlev.short_link.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name="short_links")
public class ShortLink {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "fullUrl")
    private String fullUrl;

    @Column(name = "shortUrl")
    private String shortUrl;

    @Column(name = "userId")
    private String userId;

    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    @Column(name = "expirationDate")
    private LocalDateTime expirationDate;

    @Column(name = "isDeleted")
    private boolean isDeleted;

    @Column(name = "maxUsages")
    private int maxUsages;

    @Column(name = "usages")
    private int usages;

    public ShortLink() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getMaxUsages() {
        return maxUsages;
    }

    public void setMaxUsages(int maxUsages) {
        this.maxUsages = maxUsages;
    }

    public int getUsages() {
        return usages;
    }

    public void setUsages(int usages) {
        this.usages = usages;
    }
}

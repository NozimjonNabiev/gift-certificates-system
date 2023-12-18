package com.epam.esm.repository.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.filter.search.SearchFilter;
import com.epam.esm.entity.filter.sort.SortFilter;
import com.epam.esm.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository extends BaseRepository<GiftCertificate> {
    @Override
    List<GiftCertificate> findAll();

    @Override
    Optional<GiftCertificate> findById(Long id);

    List<GiftCertificate> findByTag(Tag tag);

    List<GiftCertificate> findBySearchFilter(SearchFilter searchFilter);

    List<GiftCertificate> findBySortFilter(SortFilter sortFilter);

    @Override
    Long insert(GiftCertificate certificate);

    @Override
    void update(GiftCertificate certificate);

    void insertTags(GiftCertificate certificate);

    @Override
    void delete(Long id);
}

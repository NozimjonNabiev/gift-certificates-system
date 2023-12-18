package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.filter.SearchFilterDTO;
import com.epam.esm.dto.filter.SortFilterDTO;
import com.epam.esm.exception.DataModificationException;
import com.epam.esm.exception.NotFoundException;

import java.util.List;

public interface GiftCertificateService {
    List<GiftCertificateDTO> findAll() throws NotFoundException;

    GiftCertificateDTO findById(Long id) throws NotFoundException;

    List<GiftCertificateDTO> findByTag(TagDTO tag) throws NotFoundException;

    List<GiftCertificateDTO> findBySearchFilter(SearchFilterDTO searchFilter) throws NotFoundException;

    List<GiftCertificateDTO> findBySortFilter(SortFilterDTO sortFilter) throws NotFoundException;

    void create(GiftCertificateDTO certificate) throws DataModificationException;

    void update(GiftCertificateDTO certificate) throws NotFoundException, DataModificationException;

    void delete(Long id) throws NotFoundException, DataModificationException;
}

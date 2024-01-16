package com.epam.esm.service;

import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.util.mapper.TagMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.util.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void findAllByPageShouldThrowDataAccessExceptionIfExceptionWasThrown() {
        doThrow(new DataAccessException("") {})
                .when(tagRepository).findById(anyLong());

        assertThrows(DataAccessException.class,
                () -> tagService.findById(anyLong()));
    }

    @Test
    void findAllByPageShouldReturnEmptyListIfNoTagWasFound() {
        when(tagRepository.findAllByPage(any()))
                .thenReturn(List.of());

        assertTrue(tagService.findAllByPage(0, 0).isEmpty());
    }

    @Test
    void findAllByPageShouldReturnCorrectListIfAnyTagWasFound() {
        when(tagRepository.findAllByPage(any()))
                .thenReturn(List.of(getTag()));
        when(tagMapper.toTagDTO(any()))
                .thenReturn(getTagDTO());

        assertEquals(List.of(getTagDTO()), tagService.findAllByPage(0, 0));
    }

    @Test
    void findByIdShouldThrowDataAccessExceptionIfExceptionWasThrown() {
        doThrow(new DataAccessException("") {})
                .when(tagRepository).findById(anyLong());

        assertThrows(DataAccessException.class,
                () -> tagService.findById(anyLong()));
    }

    @Test
    void findByIdShouldThrowEntityNotFoundExceptionIfNoTagWasFound() {
        doThrow(new EntityNotFoundException(""))
                .when(tagRepository).findById(anyLong());

        assertThrows(EntityNotFoundException.class,
                () -> tagService.findById(anyLong()));
    }

    @Test
    void findByIdShouldReturnCorrectTagIfTagWasFound() {
        when(tagRepository.findById(anyLong()))
                .thenReturn(getTag());
        when(tagMapper.toTagDTO(any()))
                .thenReturn(getTagDTO());

        assertEquals(getTagDTO(), tagService.findById(anyLong()));
    }

    @Test
    void createShouldThrowDataAccessExceptionIfExceptionWasThrown() {
        when(tagMapper.toTag(any()))
                .thenReturn(getTag());
        when(tagRepository.findByName(anyString()))
                .thenReturn(Optional.empty());
        doThrow(new DataAccessException("") {})
                .when(tagRepository).save(any());

        assertThrows(DataAccessException.class,
                () -> tagService.create(getTagDTO()));
    }

    @Test
    void createShouldThrowEntityNotFoundExceptionIfTagAlreadyExists() {
        when(tagMapper.toTag(getTagDTO()))
                .thenReturn(getTag());
        when(tagRepository.findByName(anyString()))
                .thenReturn(Optional.ofNullable(getTag()));

        assertThrows(EntityAlreadyExistsException.class,
                () -> tagService.create(getTagDTO()));
    }

    @Test
    void createShouldReturnCorrectTagIfTagDoesNotExist() {
        when(tagMapper.toTag(any()))
                .thenReturn(getTag());
        when(tagMapper.toTagDTO(any()))
                .thenReturn(getTagDTO());
        when(tagRepository.findByName(anyString()))
                .thenReturn(Optional.empty());

        assertEquals(getTagDTO(), tagService.create(getTagDTO()));
    }

    @Test
    void deleteByPageShouldThrowDataAccessExceptionIfExceptionWasThrown() {
        doThrow(new DataAccessException("") {})
                .when(tagRepository).delete(any());

        assertThrows(DataAccessException.class,
                () -> tagService.deleteById(anyLong()));
    }

    @Test
    void deleteByPageShouldThrowEntityNotFoundExceptionIfNoTagWasFound() {
        doThrow(new EntityNotFoundException(""))
                .when(tagRepository).findById(anyLong());

        assertThrows(EntityNotFoundException.class,
                () -> tagService.deleteById(anyLong()));
    }

    @Test
    void deleteByPageShouldDoNothingIfTagIfTagWasFound() {
        when(tagRepository.findById(anyLong()))
                .thenReturn(getTag());

        assertDoesNotThrow(() -> tagService.deleteById(anyLong()));
    }
}

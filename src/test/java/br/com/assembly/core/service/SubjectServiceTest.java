package br.com.assembly.core.service;

import br.com.assembly.TestUtil;
import br.com.assembly.api.exception.NotFoundException;
import br.com.assembly.converter.impl.PageableConverterImpl;
import br.com.assembly.converter.impl.SessionConverterImpl;
import br.com.assembly.converter.impl.SubjectConverterImpl;
import br.com.assembly.core.domain.Subject;
import br.com.assembly.core.exception.MessageError;
import br.com.assembly.core.service.impl.SubjectServiceImpl;
import br.com.assembly.storage.postegres.entity.SubjectEntity;
import br.com.assembly.storage.postegres.repository.SubjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(classes = {SubjectServiceImpl.class, SubjectConverterImpl.class, SessionConverterImpl.class, PageableConverterImpl.class})
@ActiveProfiles("test")
class SubjectServiceTest extends TestUtil {

    @MockBean
    private SubjectRepository subjectRepositoy;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private VoteService voteService;

    @Autowired
    private SubjectService subjectService;


    @Test
    public void test_Should_Request_GetAll_With_Success(){
        when(subjectRepositoy.findAll(any(Pageable.class))).thenReturn(toPageSubjectEntity());

        Page<Subject> result = subjectService.findAll(toPageableDomain());

        assertNotNull(result);
        assertTrue(result.hasContent());

        Subject expected = toSubject();
        Subject subject = result.getContent().get(0);
        assertEquals(expected.getQuestion(), subject.getQuestion());
        assertEquals(expected.getAnswers(), subject.getAnswers());
        assertEquals(expected.getId(), subject.getId());

        verify(subjectRepositoy, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void test_Should_Request_GetById_With_Success(){
        when(subjectRepositoy.findById(any(Long.class))).thenReturn(Optional.ofNullable(toSubjectEntity()));

        Subject result = subjectService.findById(toSubject().getId());

        Subject expected = toSubject();
        assertEquals(expected.getQuestion(), result.getQuestion());
        assertEquals(expected.getAnswers(), result.getAnswers());
        assertEquals(expected.getId(), result.getId());

        verify(subjectRepositoy, times(1)).findById(any(Long.class));
    }

    @Test
    public void test_Should_Request_Create_With_Success(){
        when(subjectRepositoy.save(any(SubjectEntity.class))).thenReturn(toSubjectEntity());

        Subject result = subjectService.save(toSubject());

        Subject expected = toSubject();
        assertEquals(expected.getQuestion(), result.getQuestion());
        assertEquals(expected.getAnswers(), result.getAnswers());
        assertEquals(expected.getId(), result.getId());

        verify(subjectRepositoy, times(1)).save(any(SubjectEntity.class));
    }

    @Test
    public void test_Should_Request_GetAll_When_NoExists(){
        when(subjectRepositoy.findAll(any(Pageable.class))).thenReturn(Page.empty());

        assertThrows(
                NotFoundException.class,
                () -> subjectService.findAll(toPageableDomain()),
                MessageError.GETTING_ALL_CONTEXT_NOT_EXISTS(SubjectService.CONTEXT));

        verify(subjectRepositoy, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void test_Should_Request_GetAll_When_Empty(){

        when(subjectRepositoy.findAll(any(Pageable.class))).thenReturn(null);

        assertThrows(
                NotFoundException.class,
                () -> subjectService.findAll(toPageableDomain()),
                MessageError.GETTING_ALL_CONTEXT_NOT_EXISTS(SubjectService.CONTEXT));

        verify(subjectRepositoy, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void test_Should_Request_GetById_When_NoExists(){
        when(subjectRepositoy.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> subjectService.findById(toSubject().getId()),
                MessageError.GETTING_ALL_CONTEXT_NOT_EXISTS(SubjectService.CONTEXT));

        verify(subjectRepositoy, times(1)).findById(any(Long.class));
    }
    
}

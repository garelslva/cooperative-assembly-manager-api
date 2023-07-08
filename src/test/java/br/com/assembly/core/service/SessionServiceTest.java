package br.com.assembly.core.service;

import br.com.assembly.TestUtil;
import br.com.assembly.api.exception.NotFoundException;
import br.com.assembly.converter.impl.PageableConverterImpl;
import br.com.assembly.converter.impl.SessionConverterImpl;
import br.com.assembly.core.domain.Session;
import br.com.assembly.core.exception.MessageError;
import br.com.assembly.core.service.impl.SessionServiceImpl;
import br.com.assembly.storage.postegres.entity.SessionEntity;
import br.com.assembly.storage.postegres.repository.SessionRepository;
import br.com.assembly.storage.redis.RedisRepository;
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
@ImportAutoConfiguration(classes = {SessionServiceImpl.class, SessionConverterImpl.class, PageableConverterImpl.class})
@ActiveProfiles("test")
class SessionServiceTest extends TestUtil {

    @MockBean
    private SessionRepository sessionRepositoy;

    @MockBean
    private SubjectService subjectService;

    @MockBean
    private RedisRepository redisRepository;
    
    @Autowired
    private SessionService sessionService;

    @Test
    public void test_Should_Request_GetAll_With_Success(){

        when(sessionRepositoy.findAll(any(Pageable.class))).thenReturn(toPageSessionEntity());

        Page<Session> result = sessionService.findAll(toPageableDomain());

        assertNotNull(result);
        assertTrue(result.hasContent());

        Session expected = toSession();
        Session session = result.getContent().get(0);
        assertEquals(expected.getSubjectId(), session.getSubjectId());
        assertEquals(expected.getLimitTimeInSeconds(), session.getLimitTimeInSeconds());
        assertEquals(expected.isActivateSession(), session.isActivateSession());
        assertEquals(expected.getId(), session.getId());

        verify(sessionRepositoy, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void test_Should_Request_GetById_With_Success(){
        when(sessionRepositoy.findById(any(Long.class))).thenReturn(Optional.ofNullable(toSessionEntity()));

        Session result = sessionService.findById(toSession().getId());

        Session expected = toSession();
        assertEquals(expected.getSubjectId(), result.getSubjectId());
        assertEquals(expected.getLimitTimeInSeconds(), result.getLimitTimeInSeconds());
        assertEquals(expected.isActivateSession(), result.isActivateSession());
        assertEquals(expected.getId(), result.getId());

        verify(sessionRepositoy, times(1)).findById(any(Long.class));
    }

    @Test
    public void test_Should_Request_Create_With_Success(){
        when(sessionRepositoy.save(any(SessionEntity.class))).thenReturn(toSessionEntity());

        Session result = sessionService.openSession(toSession());

        Session expected = toSession();
        assertEquals(expected.getSubjectId(), result.getSubjectId());
        assertEquals(expected.getLimitTimeInSeconds(), result.getLimitTimeInSeconds());
        assertEquals(expected.isActivateSession(), result.isActivateSession());
        assertEquals(expected.getId(), result.getId());

        verify(sessionRepositoy, times(1)).save(any(SessionEntity.class));
    }

    @Test
    public void test_Should_Request_GetAll_When_NoExists(){

        when(sessionRepositoy.findAll(any(Pageable.class))).thenReturn(Page.empty());

        assertThrows(
                NotFoundException.class,
                () -> sessionService.findAll(toPageableDomain()),
                MessageError.GETTING_ALL_CONTEXT_NOT_EXISTS(SessionService.CONTEXT));

        verify(sessionRepositoy, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void test_Should_Request_GetAll_When_Empty(){

        when(sessionRepositoy.findAll(any(Pageable.class))).thenReturn(null);

        assertThrows(
                NotFoundException.class,
                () -> sessionService.findAll(toPageableDomain()),
                MessageError.GETTING_ALL_CONTEXT_NOT_EXISTS(SessionService.CONTEXT));

        verify(sessionRepositoy, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void test_Should_Request_GetById_When_NoExists(){
        when(sessionRepositoy.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> sessionService.findById(toSession().getId()),
                MessageError.GETTING_ALL_CONTEXT_NOT_EXISTS(SessionService.CONTEXT));

        verify(sessionRepositoy, times(1)).findById(any(Long.class));
    }
}
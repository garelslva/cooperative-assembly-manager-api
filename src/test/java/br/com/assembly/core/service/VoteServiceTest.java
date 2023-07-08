package br.com.assembly.core.service;

import br.com.assembly.TestUtil;
import br.com.assembly.adapter.CpfVerification;
import br.com.assembly.api.exception.NotFoundException;
import br.com.assembly.converter.impl.PageableConverterImpl;
import br.com.assembly.converter.impl.VoteConverterImpl;
import br.com.assembly.core.domain.Vote;
import br.com.assembly.core.exception.MessageError;
import br.com.assembly.core.service.impl.VoteServiceImpl;
import br.com.assembly.storage.postegres.entity.VoteEntity;
import br.com.assembly.storage.postegres.repository.VoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@ImportAutoConfiguration(classes = {VoteServiceImpl.class, PageableConverterImpl.class, VoteConverterImpl.class})
class VoteServiceTest extends TestUtil {

    @MockBean
    private VoteRepository voteRepositoy;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private AssociateService associateService;

    @MockBean
    private SubjectService subjectService;

    @MockBean
    private CpfVerification cpfVerification;
    
    @Autowired
    private VoteService voteService;
    
    @Test
    public void test_Should_Request_GetAll_With_Success(){

        when(voteRepositoy.findAll(any(Pageable.class))).thenReturn(toPageVoteEntity());

        Page<Vote> result = voteService.findAll(toPageableDomain());

        assertNotNull(result);
        assertTrue(result.hasContent());

        Vote expected = toVote();
        Vote vote = result.getContent().get(0);
        assertEquals(expected.getSubjectId(), vote.getSubjectId());
        assertEquals(expected.getAssociateId(), vote.getAssociateId());
        assertEquals(expected.getSessionId(), vote.getSessionId());
        assertEquals(expected.getValue(), vote.getValue());
        assertEquals(expected.getId(), vote.getId());

        verify(voteRepositoy, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void test_Should_Request_GetById_With_Success(){
        when(voteRepositoy.findById(any(Long.class))).thenReturn(Optional.ofNullable(toVoteEntity()));

        Vote result = voteService.findById(toVote().getId());

        Vote expected = toVote();
        assertEquals(expected.getSubjectId(), result.getSubjectId());
        assertEquals(expected.getAssociateId(), result.getAssociateId());
        assertEquals(expected.getSessionId(), result.getSessionId());
        assertEquals(expected.getValue(), result.getValue());
        assertEquals(expected.getId(), result.getId());

        verify(voteRepositoy, times(1)).findById(any(Long.class));
    }

    @Test
    public void test_Should_Request_Create_With_Success(){
        when(voteRepositoy.save(any(VoteEntity.class))).thenReturn(toVoteEntity());
        when(sessionService.findFirstAllBySubjectIdOrderByIdDesc(any(Long.class))).thenReturn(toSession());
        when(subjectService.findById(any(Long.class))).thenReturn(toSubject());
        when(associateService.findById(any(Long.class))).thenReturn(toAssociate());

        Vote result = voteService.send(toVote());

        Vote expected = toVote();
        assertEquals(expected.getSubjectId(), result.getSubjectId());
        assertEquals(expected.getAssociateId(), result.getAssociateId());
        assertEquals(expected.getSessionId(), result.getSessionId());
        assertEquals(expected.getValue(), result.getValue());
        assertEquals(expected.getId(), result.getId());

        verify(voteRepositoy, times(1)).save(any(VoteEntity.class));
        verify(sessionService, times(1)).findFirstAllBySubjectIdOrderByIdDesc(any(Long.class));
        verify(subjectService, times(1)).findById(any(Long.class));
        verify(associateService, times(1)).findById(any(Long.class));
    }

    @Test
    public void test_Should_Request_GetAll_When_NoExists(){

        when(voteRepositoy.findAll(any(Pageable.class))).thenReturn(Page.empty());

        assertThrows(
                NotFoundException.class,
                () -> voteService.findAll(toPageableDomain()),
                MessageError.GETTING_ALL_CONTEXT_NOT_EXISTS(VoteService.CONTEXT));

        verify(voteRepositoy, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void test_Should_Request_GetAll_When_Empty(){

        when(voteRepositoy.findAll(any(Pageable.class))).thenReturn(null);

        assertThrows(
                NotFoundException.class,
                () -> voteService.findAll(toPageableDomain()),
                MessageError.GETTING_ALL_CONTEXT_NOT_EXISTS(VoteService.CONTEXT));

        verify(voteRepositoy, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void test_Should_Request_GetById_When_NoExists(){
        when(voteRepositoy.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> voteService.findById(toVote().getId()),
                MessageError.GETTING_ALL_CONTEXT_NOT_EXISTS(VoteService.CONTEXT));

        verify(voteRepositoy, times(1)).findById(any(Long.class));
    }
}
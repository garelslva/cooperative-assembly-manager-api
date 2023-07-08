package br.com.assembly.core.service;

import br.com.assembly.TestUtil;
import br.com.assembly.api.exception.NotFoundException;
import br.com.assembly.converter.impl.AssociateConverterImpl;
import br.com.assembly.converter.impl.PageableConverterImpl;
import br.com.assembly.core.domain.Associate;
import br.com.assembly.core.exception.MessageError;
import br.com.assembly.core.service.impl.AssociateServiceImpl;
import br.com.assembly.storage.postegres.entity.AssociateEntity;
import br.com.assembly.storage.postegres.repository.AssociateRepository;
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
@ImportAutoConfiguration(classes = {AssociateServiceImpl.class, AssociateConverterImpl.class, PageableConverterImpl.class})
@ActiveProfiles("test")
class AssociateServiceTest extends TestUtil {

    @MockBean
    private AssociateRepository associateRepositoy;

    @Autowired
    private AssociateServiceImpl associateService;

    @Test
    public void test_Should_Request_GetAll_With_Success(){

        when(associateRepositoy.findAll(any(Pageable.class))).thenReturn(toPageAssociateEntity());

        Page<Associate> result = associateService.findAll(toPageableDomain());

        assertNotNull(result);
        assertTrue(result.hasContent());

        Associate expected = toAssociate();
        Associate associate = result.getContent().get(0);
        assertEquals(expected.getName(), associate.getName());
        assertEquals(expected.getCpf(), associate.getCpf());
        assertEquals(expected.getId(), associate.getId());

        verify(associateRepositoy, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void test_Should_Request_GetById_With_Success(){
        when(associateRepositoy.findById(any(Long.class))).thenReturn(Optional.ofNullable(toAssociateEntity()));

        Associate result = associateService.findById(toAssociate().getId());

        Associate expected = toAssociate();
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getCpf(), result.getCpf());
        assertEquals(expected.getId(), result.getId());

        verify(associateRepositoy, times(1)).findById(any(Long.class));
    }

    @Test
    public void test_Should_Request_Create_With_Success(){
        when(associateRepositoy.save(any(AssociateEntity.class))).thenReturn(toAssociateEntity());

        Associate result = associateService.save(toAssociate());

        Associate expected = toAssociate();
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getCpf(), result.getCpf());
        assertEquals(expected.getId(), result.getId());

        verify(associateRepositoy, times(1)).save(any(AssociateEntity.class));
    }

    @Test
    public void test_Should_Request_GetAll_When_NoExists(){

        when(associateRepositoy.findAll(any(Pageable.class))).thenReturn(Page.empty());

        assertThrows(
                NotFoundException.class,
                () -> associateService.findAll(toPageableDomain()),
                MessageError.GETTING_ALL_CONTEXT_NOT_EXISTS(AssociateService.CONTEXT));

        verify(associateRepositoy, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void test_Should_Request_GetAll_When_Empty(){

        when(associateRepositoy.findAll(any(Pageable.class))).thenReturn(null);

        assertThrows(
                NotFoundException.class,
                () -> associateService.findAll(toPageableDomain()),
                MessageError.GETTING_ALL_CONTEXT_NOT_EXISTS(AssociateService.CONTEXT));

        verify(associateRepositoy, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void test_Should_Request_GetById_When_NoExists(){
        when(associateRepositoy.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> associateService.findById(toAssociate().getId()),
                MessageError.GETTING_ALL_CONTEXT_NOT_EXISTS(AssociateService.CONTEXT));

        verify(associateRepositoy, times(1)).findById(any(Long.class));
    }
}
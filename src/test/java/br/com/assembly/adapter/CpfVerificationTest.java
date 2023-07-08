package br.com.assembly.adapter;

import br.com.assembly.TestUtil;
import br.com.assembly.adapter.client.CpfVerificationClient;
import br.com.assembly.adapter.client.exception.FeignErrorException;
import br.com.assembly.adapter.dto.CpfVerificationDTO;
import br.com.assembly.adapter.dto.enums.StatusType;
import br.com.assembly.api.exception.BusinessException;
import br.com.assembly.api.exception.UnableToVoteException;
import br.com.assembly.core.exception.MessageError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(classes = {CpfVerificationImpl.class})
@ActiveProfiles("test")
class CpfVerificationTest extends TestUtil {

    @Autowired
    private CpfVerification cpfVerification;

    @MockBean
    private CpfVerificationClient cpfVerificationClient;

    @Test
    public void test_Should_get_verificationCpf_with_Success(){

        when(this.cpfVerificationClient.active(anyString())).thenReturn(ResponseEntity.ok(toCpfVerificationDTO(StatusType.ABLE_TO_VOTE)));

        this.cpfVerification.get("1234567891234");
        
        verify(cpfVerificationClient, times(1)).active(anyString());
        Mockito.reset(cpfVerificationClient);
    }

    @Test
    public void test_Should_get_verificationCpf_when_UnableToVote(){

        when(this.cpfVerificationClient.active(anyString())).thenReturn(ResponseEntity.ok(toCpfVerificationDTO(StatusType.UNABLE_TO_VOTE)));

        UnableToVoteException exception = assertThrows(
                UnableToVoteException.class,
                () -> this.cpfVerification.get("1234567891234"),
                UnableToVoteException.ERROR_MESSAGE)
        ;

        assertNotNull(exception);
        assertEquals(UnableToVoteException.ERROR_CODE, exception.getEntity().getErrorCode());
        assertEquals(UnableToVoteException.ERROR_TYPE, exception.getEntity().getErrorType());
        assertEquals(UnableToVoteException.ERROR_MESSAGE, exception.getEntity().getMessages());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), exception.getEntity().getHttpStatus());

        verify(cpfVerificationClient, times(1)).active(anyString());
        Mockito.reset(cpfVerificationClient);
    }

    @Test
    public void test_Should_get_verificationCpf_when_InvalidaCpf(){

        when(this.cpfVerificationClient.active(anyString())).thenReturn(ResponseEntity.status(404).body(CpfVerificationDTO.builder().build()));

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> this.cpfVerification.get("1234567891234"),
                MessageError.INVALID_CPF)
        ;

        assertNotNull(exception);
        assertEquals(BusinessException.ERROR_CODE, exception.getEntity().getErrorCode());
        assertEquals(BusinessException.ERROR_TYPE, exception.getEntity().getErrorType());
        assertEquals(MessageError.INVALID_CPF, exception.getEntity().getMessages());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), exception.getEntity().getHttpStatus());

        verify(cpfVerificationClient, times(1)).active(anyString());
        Mockito.reset(cpfVerificationClient);
    }

    public void test_Should_get_verificationCpf_when_NotAvailableService(){
        when(this.cpfVerificationClient.active(anyString())).thenReturn((ResponseEntity<CpfVerificationDTO>) ResponseEntity.badRequest() );

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> this.cpfVerification.get("1234567891234"),
                MessageError.INVALID_CPF)
                ;

        assertNotNull(exception);
        assertEquals(BusinessException.ERROR_CODE, exception.getEntity().getErrorCode());
        assertEquals(BusinessException.ERROR_TYPE, exception.getEntity().getErrorType());
        assertEquals(MessageError.INVALID_CPF, exception.getEntity().getMessages());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), exception.getEntity().getHttpStatus());

        verify(cpfVerificationClient, times(1)).active(anyString());
        Mockito.reset(cpfVerificationClient);
    }

    public void test_Should_get_verificationCpf_when_GeneralFail(){
        when(this.cpfVerificationClient.active(anyString())).thenThrow(new FeignErrorException("fail", 404, "http://url"));

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> this.cpfVerification.get("1234567891234"),
                MessageError.CPF_VERIFICATION_UNAVAILABLE)
        ;

        assertNotNull(exception);
        assertEquals(BusinessException.ERROR_CODE, exception.getEntity().getErrorCode());
        assertEquals(BusinessException.ERROR_TYPE, exception.getEntity().getErrorType());
        assertEquals(MessageError.CPF_VERIFICATION_UNAVAILABLE, exception.getEntity().getMessages());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), exception.getEntity().getHttpStatus());

        verify(cpfVerificationClient, times(1)).active(anyString());
        Mockito.reset(cpfVerificationClient);
    }
}
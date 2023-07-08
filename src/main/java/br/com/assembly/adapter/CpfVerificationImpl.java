package br.com.assembly.adapter;

import br.com.assembly.adapter.client.CpfVerificationClient;
import br.com.assembly.adapter.dto.CpfVerificationDTO;
import br.com.assembly.adapter.dto.enums.StatusType;
import br.com.assembly.api.exception.BusinessException;
import br.com.assembly.api.exception.UnableToVoteException;
import br.com.assembly.core.exception.MessageError;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CpfVerificationImpl implements CpfVerification{

    @Autowired
    private CpfVerificationClient cpfVerificationClient;

    @Override
    public void get(String cpf) {
        log.info("Start CPF verification.");
        try {
            ResponseEntity<CpfVerificationDTO> response = cpfVerificationClient.active(cpf);
            if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                log.error(MessageError.INVALID_CPF);
                throw new BusinessException(MessageError.INVALID_CPF);
            }
            if (!response.getStatusCode().is2xxSuccessful()){
                log.error(MessageError.NOT_AVAILABLE);
                throw new BusinessException(MessageError.NOT_AVAILABLE);
            }
            if(response.getBody().getStatus().equals(StatusType.UNABLE_TO_VOTE)){
                throw new UnableToVoteException();
            }
        }catch (FeignException e){
            log.error(String.format("%s - %S", MessageError.CPF_VERIFICATION_UNAVAILABLE, e.getMessage()));
            throw new BusinessException(MessageError.CPF_VERIFICATION_UNAVAILABLE);
        }
    }

}

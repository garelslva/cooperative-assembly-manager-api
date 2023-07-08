package br.com.assembly.adapter.client;

import br.com.assembly.adapter.client.config.CpfVerificationConfig;
import br.com.assembly.adapter.dto.CpfVerificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "CpfVerification", url = "${cpf.verification.url}", configuration = CpfVerificationConfig.class)
public interface CpfVerificationClient {

    @GetMapping("/users/{cpf}")
    ResponseEntity<CpfVerificationDTO> active(@PathVariable("cpf") String cpf);
}

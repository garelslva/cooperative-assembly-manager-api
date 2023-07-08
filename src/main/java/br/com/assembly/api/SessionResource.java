package br.com.assembly.api;

import br.com.assembly.api.dto.PageDTO;
import br.com.assembly.api.dto.PageableDTO;
import br.com.assembly.api.dto.SessionDTO;
import br.com.assembly.api.dto.enums.OrderBySort;
import br.com.assembly.api.exception.dto.ResponsePayloadError;
import br.com.assembly.converter.PageConverter;
import br.com.assembly.converter.PageableConverter;
import br.com.assembly.converter.SessionConverter;
import br.com.assembly.core.exception.MessageError;
import br.com.assembly.core.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Tags(value = {
        @Tag( name = "Session Api", description = "Api to session management")
})
@Slf4j
@RestController
@RequestMapping("/session")
public class SessionResource {

    @Autowired
    private PageConverter pageConverter;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SessionConverter sessionConverter;

    @Autowired
    private PageableConverter pageableConverter;

    @Operation(summary = "Find all session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = MessageError.SUCCESS, content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PageDTO.class))}),
            @ApiResponse(responseCode = "400", description = MessageError.ERROR_BAD_REQUEST, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))}),
            @ApiResponse(responseCode = "404", description = MessageError.NOT_FOUND, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))}),
            @ApiResponse(responseCode = "500", description = MessageError.UNEXPECTED_ERRO, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))})})
    @Parameter(name ="page", in = ParameterIn.QUERY, schema = @Schema(description = "PAGE", type = "Long", implementation = Long.class), required = false)
    @Parameter(name ="offset", in = ParameterIn.QUERY, schema = @Schema(description = "OFFSET", type = "Long", implementation = Long.class), required = false)
    @Parameter(name ="order", in = ParameterIn.QUERY, schema = @Schema(description = "ORDER", type = "String", implementation = OrderBySort.class), required = false)
    @GetMapping
    public ResponseEntity<PageDTO<SessionDTO>> getAll(@Valid @Parameter(hidden = true) final PageableDTO pageable) {
        log.info("Start listener all session");
        return ResponseEntity.ok(pageConverter.toPageDto(this.sessionConverter.toDto(
                sessionService.findAll(this.pageableConverter.toDomain(pageable)))));
    }

    @Operation(summary = "Find all session active")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = MessageError.SUCCESS, content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PageDTO.class))}),
            @ApiResponse(responseCode = "400", description = MessageError.ERROR_BAD_REQUEST, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))}),
            @ApiResponse(responseCode = "404", description = MessageError.NOT_FOUND, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))}),
            @ApiResponse(responseCode = "500", description = MessageError.UNEXPECTED_ERRO, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))})})
    @Parameter(name ="page", in = ParameterIn.QUERY, schema = @Schema(description = "PAGE", type = "Long", implementation = Long.class), required = false)
    @Parameter(name ="offset", in = ParameterIn.QUERY, schema = @Schema(description = "OFFSET", type = "Long", implementation = Long.class), required = false)
    @Parameter(name ="order", in = ParameterIn.QUERY, schema = @Schema(description = "ORDER", type = "String", implementation = OrderBySort.class), required = false)
    @GetMapping("/actives")
    public ResponseEntity<PageDTO<SessionDTO>> getAllActive(@Valid @Parameter(hidden = true) final PageableDTO pageable) {
        log.info("Start listener all session active");
        return ResponseEntity.ok(pageConverter.toPageDto(this.sessionConverter.toDto(
                sessionService.findAllActive(this.pageableConverter.toDomain(pageable)))));
    }

    @Operation(summary = "Find all session (Pauta) by subjectId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = MessageError.SUCCESS, content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PageDTO.class))}),
            @ApiResponse(responseCode = "400", description = MessageError.ERROR_BAD_REQUEST, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))}),
            @ApiResponse(responseCode = "404", description = MessageError.NOT_FOUND, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))}),
            @ApiResponse(responseCode = "500", description = MessageError.UNEXPECTED_ERRO, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))})})
    @Parameter(name ="subjectId", in = ParameterIn.PATH, schema = @Schema(description = "id", type = "Long", implementation = Long.class), required = true)
    @GetMapping("/{subjectId}/subject/sessions")
    public ResponseEntity<List<SessionDTO>> getAllBySubjectId(@Valid @NotNull @PathVariable("subjectId") final Long id) {
        log.info("Start listener session to subjectId: {}", id);
        return ResponseEntity.ok(this.sessionConverter.toDto(
                sessionService.findAllBySubjectId(id)));
    }

    @Operation(summary = "Find all session records by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = MessageError.SUCCESS, content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SessionDTO.class))}),
            @ApiResponse(responseCode = "400", description = MessageError.ERROR_BAD_REQUEST, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))}),
            @ApiResponse(responseCode = "404", description = MessageError.NOT_FOUND, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))}),
            @ApiResponse(responseCode = "500", description = MessageError.UNEXPECTED_ERRO, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))})})
    @Parameter(name ="id", in = ParameterIn.PATH, schema = @Schema(description = "id", type = "Long", implementation = Long.class), required = true)
    @GetMapping("/{id}")
    public ResponseEntity<SessionDTO> getById(@Valid @NotNull @PathVariable("id") final Long id) {
        log.info("Start listener session to id: {}", id);
        return ResponseEntity.ok(this.sessionConverter.toDto(
                sessionService.findById(id)));
    }

    @Operation(summary = "Open one session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = MessageError.SUCCESS, content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SessionDTO.class))}),
            @ApiResponse(responseCode = "400", description = MessageError.ERROR_BAD_REQUEST, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))}),
            @ApiResponse(responseCode = "500", description = MessageError.UNEXPECTED_ERRO, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))})})
    @PostMapping
    public ResponseEntity<SessionDTO> openSession(@Valid @NotEmpty @RequestBody final SessionDTO sessionDTO) {
        log.info("Start open session");
        return ResponseEntity.status(HttpStatus.CREATED).body(this.sessionConverter.toDto(
                sessionService.openSession(this.sessionConverter.toDomain(sessionDTO))));
    }
}

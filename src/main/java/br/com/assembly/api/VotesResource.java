package br.com.assembly.api;

import br.com.assembly.api.dto.PageDTO;
import br.com.assembly.api.dto.PageableDTO;
import br.com.assembly.api.dto.VoteDTO;
import br.com.assembly.api.dto.enums.OrderBySort;
import br.com.assembly.api.exception.dto.ResponsePayloadError;
import br.com.assembly.converter.PageConverter;
import br.com.assembly.converter.PageableConverter;
import br.com.assembly.converter.VoteConverter;
import br.com.assembly.core.exception.MessageError;
import br.com.assembly.core.service.VoteService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Tags(value = {
        @Tag( name = "Vote Api", description = "Api to vote management")
})
@Slf4j
@RestController
@RequestMapping("/vote")
public class VotesResource {

    @Autowired
    private PageConverter pageConverter;

    @Autowired
    private VoteService voteService;

    @Autowired
    private VoteConverter voteConverter;

    @Autowired
    private PageableConverter pageableConverter;

    @Operation(summary = "Find all votes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = MessageError.SUCCESS, content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PageDTO.class))}),
            @ApiResponse(responseCode = "400", description = MessageError.ERROR_BAD_REQUEST, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))}),
            @ApiResponse(responseCode = "404", description = MessageError.NOT_FOUND, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))}),
            @ApiResponse(responseCode = "500", description = MessageError.UNEXPECTED_ERRO, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))})})
    @Parameter(name ="page", in = ParameterIn.QUERY, schema = @Schema(description = "PAGE", type = "Long", implementation = Long.class), required = false)
    @Parameter(name ="offset", in = ParameterIn.QUERY, schema = @Schema(description = "OFFSET", type = "Long", implementation = Long.class), required = false)
    @Parameter(name ="order", in = ParameterIn.QUERY, schema = @Schema(description = "ORDER", type = "String", implementation = OrderBySort.class), required = false)
    @GetMapping
    public ResponseEntity<PageDTO<VoteDTO>> getAll(@Valid @Parameter(hidden = true) final PageableDTO pageable) {
        log.info("Start listener all votes");
        return ResponseEntity.ok(pageConverter.toPageDto(this.voteConverter.toDto(
                voteService.findAll(this.pageableConverter.toDomain(pageable)))));
    }

    @Operation(summary = "Find votes records by subjectId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = MessageError.SUCCESS, content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VoteDTO.class))}),
            @ApiResponse(responseCode = "400", description = MessageError.ERROR_BAD_REQUEST, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))}),
            @ApiResponse(responseCode = "404", description = MessageError.NOT_FOUND, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))}),
            @ApiResponse(responseCode = "500", description = MessageError.UNEXPECTED_ERRO, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))})})
    @Parameter(name ="subjectId", in = ParameterIn.PATH, schema = @Schema(description = "subjectId", type = "Long", implementation = Long.class), required = true)
    @GetMapping("/{subjectId}")
    public ResponseEntity<VoteDTO> getBySubjectId(@Valid @NotNull @PathVariable("subjectId") final Long id) {
        log.info("Start listener votes to id: {}", id);
        return ResponseEntity.ok(this.voteConverter.toDto(
                voteService.findById(id)));
    }

    @Operation(summary = "Send votes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = MessageError.SUCCESS, content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = VoteDTO.class))}),
            @ApiResponse(responseCode = "400", description = MessageError.ERROR_BAD_REQUEST, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))}),
            @ApiResponse(responseCode = "500", description = MessageError.UNEXPECTED_ERRO, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))})})
    @PostMapping
    public ResponseEntity<VoteDTO> send(@Valid @RequestBody final VoteDTO sendVoteDTO) {
        log.info("Start send vote");
        return ResponseEntity.status(HttpStatus.CREATED).body(this.voteConverter.toDto(
                voteService.send(this.voteConverter.toDomain(sendVoteDTO))));
    }
}

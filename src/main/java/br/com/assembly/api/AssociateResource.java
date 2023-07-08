package br.com.assembly.api;

import br.com.assembly.api.dto.AssociateDTO;
import br.com.assembly.api.dto.PageDTO;
import br.com.assembly.api.dto.PageableDTO;
import br.com.assembly.api.dto.enums.OrderBySort;
import br.com.assembly.api.exception.dto.ResponsePayloadError;
import br.com.assembly.converter.AssociateConverter;
import br.com.assembly.converter.PageConverter;
import br.com.assembly.converter.PageableConverter;
import br.com.assembly.core.exception.MessageError;
import br.com.assembly.core.service.AssociateService;
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

@Tags(value = {
        @Tag( name = "Associate Api", description = "Api to associate management")
})
@Slf4j
@RestController
@RequestMapping("/associate")
public class AssociateResource {

    @Autowired
    private PageConverter pageConverter;

    @Autowired
    private AssociateService associateService;

    @Autowired
    private AssociateConverter associateConverter;

    @Autowired
    private PageableConverter pageableConverter;

    @Operation(summary = "Find all associates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = MessageError.SUCCESS, content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PageDTO.class))}),
            @ApiResponse(responseCode = "400", description = MessageError.ERROR_BAD_REQUEST, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))}),
            @ApiResponse(responseCode = "404", description = MessageError.NOT_FOUND, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))}),
            @ApiResponse(responseCode = "500", description = MessageError.UNEXPECTED_ERRO, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))})})
    @Parameter(name ="page", in = ParameterIn.QUERY, schema = @Schema(description = "PAGE", type = "Long", implementation = Long.class), required = false)
    @Parameter(name ="offset", in = ParameterIn.QUERY, schema = @Schema(description = "OFFSET", type = "Long", implementation = Long.class), required = false)
    @Parameter(name ="order", in = ParameterIn.QUERY, schema = @Schema(description = "ORDER", type = "String", implementation = OrderBySort.class), required = false)
    @GetMapping
    public ResponseEntity<PageDTO<AssociateDTO>> getAll(@Valid @Parameter(hidden = true) final PageableDTO pageable) {
        log.info("Start listener all associates");
        return ResponseEntity.ok(pageConverter.toPageDto(this.associateConverter.toDto(
                associateService.findAll(this.pageableConverter.toDomain(pageable)))));
    }

    @Operation(summary = "Find all associates records by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = MessageError.SUCCESS, content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AssociateDTO.class))}),
            @ApiResponse(responseCode = "400", description = MessageError.ERROR_BAD_REQUEST, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))}),
            @ApiResponse(responseCode = "404", description = MessageError.NOT_FOUND, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))}),
            @ApiResponse(responseCode = "500", description = MessageError.UNEXPECTED_ERRO, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))})})
    @Parameter(name ="id", in = ParameterIn.PATH, schema = @Schema(description = "id", type = "Long", implementation = Long.class), required = true)
    @GetMapping("/{id}")
    public ResponseEntity<AssociateDTO> getById(@Valid @NotNull @PathVariable("id") final Long id) {
        log.info("Start listener associate to id: {}", id);
        return ResponseEntity.ok(this.associateConverter.toDto(
                associateService.findById(id)));
    }

    @Operation(summary = "Save record one associate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = MessageError.SUCCESS, content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AssociateDTO.class))}),
            @ApiResponse(responseCode = "400", description = MessageError.ERROR_BAD_REQUEST, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))}),
            @ApiResponse(responseCode = "500", description = MessageError.UNEXPECTED_ERRO, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))})})
    @PostMapping
    public ResponseEntity<AssociateDTO> save(@Valid @NotEmpty @RequestBody final AssociateDTO associateDTO) {
        log.info("Start save associate");
        return ResponseEntity.status(HttpStatus.CREATED).body(this.associateConverter.toDto(
                associateService.save(this.associateConverter.toDomain(associateDTO))));
    }

    @Operation(summary = "Delete associate records by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = MessageError.SUCCESS, content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = MessageError.ERROR_BAD_REQUEST, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))}),
            @ApiResponse(responseCode = "404", description = MessageError.NOT_FOUND, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))}),
            @ApiResponse(responseCode = "500", description = MessageError.UNEXPECTED_ERRO, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponsePayloadError.class))})})
    @Parameter(name ="id", in = ParameterIn.PATH, schema = @Schema(description = "id", type = "Long", implementation = Long.class), required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@Valid @NotNull @PathVariable("id") final Long id) {
        log.info("Start delete associate to id: {}", id);
        associateService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

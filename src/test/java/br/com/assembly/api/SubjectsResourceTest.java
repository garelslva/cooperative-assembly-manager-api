package br.com.assembly.api;

import br.com.assembly.TestUtil;
import br.com.assembly.api.exception.NotFoundException;
import br.com.assembly.converter.impl.PageConverterImpl;
import br.com.assembly.converter.impl.PageableConverterImpl;
import br.com.assembly.converter.impl.SessionConverterImpl;
import br.com.assembly.converter.impl.SubjectConverterImpl;
import br.com.assembly.core.domain.PageableDomain;
import br.com.assembly.core.domain.Subject;
import br.com.assembly.core.exception.MessageError;
import br.com.assembly.core.service.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SubjectResource.class)
@ImportAutoConfiguration(classes = {SubjectConverterImpl.class, SessionConverterImpl.class, PageableConverterImpl.class, PageConverterImpl.class})
@ActiveProfiles("test")
class SubjectsResourceTest extends TestUtil {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private SubjectService subjectService;

    private MockMvc mvc;

    private StringBuilder uri = new StringBuilder("/subject");

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test_Should_Request_GetAll_With_Success() throws Exception {

        Subject subject = toSubject();
        when(subjectService.findAll(any(PageableDomain.class))).thenReturn(toPageSubject());

        mvc.perform(MockMvcRequestBuilders
            .get(uri.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpectAll(status().is(HttpStatus.OK.value()))
        .andExpect(content().string(containsString(subject.getQuestion())))
        .andExpect(content().string(containsString(subject.getId().toString())));

        verify(subjectService, times(1)).findAll(any(PageableDomain.class));
        Mockito.reset(subjectService);
    }

    @Test
    public void test_Should_Request_GetAll_When_Pageable_With_Success() throws Exception {

        Subject subject = toSubject();
        when(subjectService.findAll(any(PageableDomain.class))).thenReturn(toPageSubject());

        mvc.perform(MockMvcRequestBuilders
            .get(uri.append("?page=1&offset=50&order=ASC").toString())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpectAll(status().is(HttpStatus.OK.value()))
        .andExpect(content().string(containsString(subject.getQuestion())))
        .andExpect(content().string(containsString(subject.getId().toString())));

        verify(subjectService, times(1)).findAll(any(PageableDomain.class));
        Mockito.reset(subjectService);
    }

    @Test
    public void test_Should_Request_GetAll_When_BadRequest() throws Exception {

        mvc.perform(MockMvcRequestBuilders
            .get(uri.append("?page=A&offset=B&order=10").toString())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpectAll(status().is(HttpStatus.BAD_REQUEST.value()))
        .andExpect(content().string(containsString(MessageError.INVALID_FIELDS_TYPE)))
        .andExpect(content().string(containsString(MessageError.VALIDATION_FIELDS)))
        .andExpect(content().string(containsString("[\"page\",\"offset\",\"order\"]")));

        verify(subjectService, times(0)).findAll(any(PageableDomain.class));
        Mockito.reset(subjectService);
    }

    @Test
    public void test_Should_Request_GetAll_When_NotFound() throws Exception {

        when(subjectService.findAll(any(PageableDomain.class))).thenThrow(new NotFoundException());

        mvc.perform(MockMvcRequestBuilders
            .get(uri.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpectAll(status().is(HttpStatus.NOT_FOUND.value()))
        .andExpect(content().string(containsString(NotFoundException.ERROR_CODE)))
        .andExpect(content().string(containsString(NotFoundException.ERROR_TYPE)))
        .andExpect(content().string(containsString(NotFoundException.ERROR_MESSAGE)));

        verify(subjectService, times(1)).findAll(any(PageableDomain.class));
        Mockito.reset(subjectService);
    }

    @Test
    public void test_Should_Request_GetById_With_Success() throws Exception {

        Subject subject = toSubject();
        when(subjectService.findById(any(Long.class))).thenReturn(toSubject());
        mvc.perform(MockMvcRequestBuilders
            .get(uri.append("/1").toString())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpectAll(status().is(HttpStatus.OK.value()))
        .andExpect(content().string(containsString(subject.getQuestion())))
        .andExpect(content().string(containsString(subject.getId().toString())));

        verify(subjectService, times(1)).findById(any(Long.class));
        Mockito.reset(subjectService);
    }

    @Test
    public void test_Should_Request_GetById_When_BadRequest() throws Exception {

        mvc.perform(MockMvcRequestBuilders
            .get(uri.append("/A").toString())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpectAll(status().is(HttpStatus.BAD_REQUEST.value()))
        .andExpect(content().string(containsString(MessageError.INVALID_FIELD_TYPE)))
        .andExpect(content().string(containsString(MessageError.VALIDATION_FIELDS)))
        .andExpect(content().string(containsString("Parameters: 'id' ")));

        verify(subjectService, times(0)).findAll(any(PageableDomain.class));
        Mockito.reset(subjectService);
    }

    @Test
    public void test_Should_Request_GetById_When_NotFound() throws Exception {

        when(subjectService.findById(any(Long.class))).thenThrow(new NotFoundException());

        mvc.perform(MockMvcRequestBuilders
            .get(uri.append("/1").toString())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpectAll(status().is(HttpStatus.NOT_FOUND.value()))
        .andExpect(content().string(containsString(NotFoundException.ERROR_CODE)))
        .andExpect(content().string(containsString(NotFoundException.ERROR_TYPE)))
        .andExpect(content().string(containsString(NotFoundException.ERROR_MESSAGE)));

        verify(subjectService, times(1)).findById(any(Long.class));
        Mockito.reset(subjectService);
    }

    @Test
    public void test_Should_Request_Create_With_Success() throws Exception {

        Subject subject = toSubject();
        when(subjectService.save(any(Subject.class))).thenReturn(toSubject());

        mvc.perform(MockMvcRequestBuilders
                        .post(uri.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBodySubject(toSubject()))
                )
                .andDo(print())
                .andExpectAll(status().is(HttpStatus.CREATED.value()))
                .andExpect(content().string(containsString(subject.getQuestion())))
                .andExpect(content().string(containsString(subject.getId().toString())));

        verify(subjectService, times(1)).save(any(Subject.class));
        Mockito.reset(subjectService);
    }

    @Test
    public void test_Should_Request_Create_When_BadRequest() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .post(uri.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(content().string(containsString(MessageError.INVALID_REQUEST_TYPE)))
                .andExpect(content().string(containsString(MessageError.VALIDATION_FIELDS)))
                .andExpect(content().string(containsString(MessageError.REQUIRED_REQUEST_BODY_IS_MISSING)));

        verify(subjectService, times(0)).findAll(any(PageableDomain.class));
        Mockito.reset(subjectService);
    }
}
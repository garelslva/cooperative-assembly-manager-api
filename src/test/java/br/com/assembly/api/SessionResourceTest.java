package br.com.assembly.api;

import br.com.assembly.TestUtil;
import br.com.assembly.api.dto.SessionDTO;
import br.com.assembly.api.exception.NotFoundException;
import br.com.assembly.converter.impl.PageConverterImpl;
import br.com.assembly.converter.impl.PageableConverterImpl;
import br.com.assembly.converter.impl.SessionConverterImpl;
import br.com.assembly.core.domain.PageableDomain;
import br.com.assembly.core.domain.Session;
import br.com.assembly.core.exception.MessageError;
import br.com.assembly.core.service.SessionService;
import br.com.assembly.core.service.impl.SessionServiceImpl;
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

@WebMvcTest(controllers = SessionResource.class)
@ImportAutoConfiguration(classes = {SessionConverterImpl.class, PageableConverterImpl.class, PageConverterImpl.class})
@ActiveProfiles("test")
class SessionResourceTest extends TestUtil {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private SessionService sessiontService;

    private MockMvc mvc;

    private StringBuilder uri = new StringBuilder("/session");

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test_Should_Request_GetAll_With_Success() throws Exception {

        Session session = toSession();
        when(sessiontService.findAll(any(PageableDomain.class))).thenReturn(toPageSession());

        mvc.perform(MockMvcRequestBuilders
                        .get(uri.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(status().is(HttpStatus.OK.value()))
                .andExpect(content().string(containsString(session.getSubjectId().toString())))
                .andExpect(content().string(containsString(session.getLimitTimeInSeconds().toString())))
                .andExpect(content().string(containsString(session.getId().toString())));

        verify(sessiontService, times(1)).findAll(any(PageableDomain.class));
        Mockito.reset(sessiontService);
    }

    @Test
    public void test_Should_Request_GetAll_When_Pageable_With_Success() throws Exception {

        Session session = toSession();
        when(sessiontService.findAll(any(PageableDomain.class))).thenReturn(toPageSession());

        mvc.perform(MockMvcRequestBuilders
                        .get(uri.append("?page=1&offset=50&order=ASC").toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(status().is(HttpStatus.OK.value()))
                .andExpect(content().string(containsString(session.getSubjectId().toString())))
                .andExpect(content().string(containsString(session.getLimitTimeInSeconds().toString())))
                .andExpect(content().string(containsString(session.getId().toString())));

        verify(sessiontService, times(1)).findAll(any(PageableDomain.class));
        Mockito.reset(sessiontService);
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

        verify(sessiontService, times(0)).findAll(any(PageableDomain.class));
        Mockito.reset(sessiontService);
    }

    @Test
    public void test_Should_Request_GetAll_When_NotFound() throws Exception {

        when(sessiontService.findAll(any(PageableDomain.class))).thenThrow(new NotFoundException());

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

        verify(sessiontService, times(1)).findAll(any(PageableDomain.class));
        Mockito.reset(sessiontService);
    }

    @Test
    public void test_Should_Request_GetById_With_Success() throws Exception {

        Session session = toSession();
        when(sessiontService.findById(any(Long.class))).thenReturn(toSession());

        mvc.perform(MockMvcRequestBuilders
                        .get(uri.append("/1").toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(status().is(HttpStatus.OK.value()))
                .andExpect(content().string(containsString(session.getSubjectId().toString())))
                .andExpect(content().string(containsString(session.getLimitTimeInSeconds().toString())))
                .andExpect(content().string(containsString(session.getId().toString())));

        verify(sessiontService, times(1)).findById(any(Long.class));
        Mockito.reset(sessiontService);
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

        verify(sessiontService, times(0)).findAll(any(PageableDomain.class));
        Mockito.reset(sessiontService);
    }

    @Test
    public void test_Should_Request_GetById_When_NotFound() throws Exception {

        when(sessiontService.findById(any(Long.class))).thenThrow(new NotFoundException());

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

        verify(sessiontService, times(1)).findById(any(Long.class));
        Mockito.reset(sessiontService);
    }

    @Test
    public void test_Should_Request_GetAllBySubjectId_With_Success() throws Exception {

        Session session = toSession();
        when(sessiontService.findAllBySubjectId(any(Long.class))).thenReturn(toPageSession().getContent());

        mvc.perform(MockMvcRequestBuilders
                        .get(uri.append("/1/subject/sessions").toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(status().is(HttpStatus.OK.value()))
                .andExpect(content().string(containsString(session.getSubjectId().toString())))
                .andExpect(content().string(containsString(session.getLimitTimeInSeconds().toString())))
                .andExpect(content().string(containsString(session.getId().toString())));

        verify(sessiontService, times(1)).findAllBySubjectId(any(Long.class));
        Mockito.reset(sessiontService);
    }

    @Test
    public void test_Should_Request_GetAllBySubjectId_When_Pageable_With_Success() throws Exception {

        Session session = toSession();
        when(sessiontService.findAllBySubjectId(any(Long.class))).thenReturn(toPageSession().getContent());

        mvc.perform(MockMvcRequestBuilders
                        .get(uri.append("/1/subject/sessions").toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(status().is(HttpStatus.OK.value()))
                .andExpect(content().string(containsString(session.getSubjectId().toString())))
                .andExpect(content().string(containsString(session.getLimitTimeInSeconds().toString())))
                .andExpect(content().string(containsString(session.getId().toString())));

        verify(sessiontService, times(1)).findAllBySubjectId(any(Long.class));
        Mockito.reset(sessiontService);
    }

    @Test
    public void test_Should_Request_GetAllBySubjectId_When_Pageable_With_Bad_Request() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .get(uri.append("/A/subject/sessions").toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(content().string(containsString(MessageError.INVALID_FIELD_TYPE)))
                .andExpect(content().string(containsString(MessageError.VALIDATION_FIELDS)))
                .andExpect(content().string(containsString("Parameters: 'subjectId' ")));

        verify(sessiontService, times(0)).findAllBySubjectId(any(Long.class));
        Mockito.reset(sessiontService);
    }

    @Test
    public void test_Should_Request_GetAllBySubjectId_When_BadRequest() throws Exception {

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

        verify(sessiontService, times(0)).findAll(any(PageableDomain.class));
        Mockito.reset(sessiontService);
    }

    @Test
    public void test_Should_Request_OpenSession_When_OpenSession_on_Redis_With_Success() throws Exception {

        Session session = toSession();
        SessionDTO sessionDto = toSessionDto();
        sessionDto.setCreated(null);
        when(sessiontService.openSession(any(Session.class))).thenReturn(toSession());

        mvc.perform(MockMvcRequestBuilders
                        .post(uri.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBodySubject(sessionDto))
                )
                .andDo(print())
                .andExpectAll(status().is(HttpStatus.CREATED.value()))
                .andExpect(content().string(containsString(session.getSubjectId().toString())))
                .andExpect(content().string(containsString(session.getLimitTimeInSeconds().toString())))
                .andExpect(content().string(containsString(session.getId().toString())));

        verify(sessiontService, times(1)).openSession(any(Session.class));
        Mockito.reset(sessiontService);
    }

    @Test
    public void test_Should_Request_OpenSession_When_No_has_OpenSession_on_Redis_With_Success() throws Exception {

        Session session = toSession();
        SessionDTO sessionDto = toSessionDto();
        sessionDto.setCreated(null);
        when(sessiontService.openSession(any(Session.class))).thenReturn(toSession());

        mvc.perform(MockMvcRequestBuilders
                        .post(uri.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonBodySubject(sessionDto))
                )
                .andDo(print())
                .andExpectAll(status().is(HttpStatus.CREATED.value()))
                .andExpect(content().string(containsString(session.getSubjectId().toString())))
                .andExpect(content().string(containsString(session.getLimitTimeInSeconds().toString())))
                .andExpect(content().string(containsString(session.getId().toString())));

        verify(sessiontService, times(1)).openSession(any(Session.class));
        Mockito.reset(sessiontService);
    }

    @Test
    public void test_Should_Request_OpenSession_When_BadRequest() throws Exception {

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

        verify(sessiontService, times(0)).findAll(any(PageableDomain.class));
        Mockito.reset(sessiontService);
    }

}
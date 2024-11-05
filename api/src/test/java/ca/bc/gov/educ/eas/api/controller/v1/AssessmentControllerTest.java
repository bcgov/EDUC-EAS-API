package ca.bc.gov.educ.eas.api.controller.v1;

import ca.bc.gov.educ.eas.api.BaseEasAPITest;
import ca.bc.gov.educ.eas.api.constants.v1.AssessmentTypeCodes;
import ca.bc.gov.educ.eas.api.constants.v1.URL;
import ca.bc.gov.educ.eas.api.mappers.v1.AssessmentMapper;
import ca.bc.gov.educ.eas.api.model.v1.AssessmentEntity;
import ca.bc.gov.educ.eas.api.model.v1.SessionEntity;
import ca.bc.gov.educ.eas.api.properties.ApplicationProperties;
import ca.bc.gov.educ.eas.api.repository.v1.AssessmentRepository;
import ca.bc.gov.educ.eas.api.repository.v1.AssessmentTypeCodeRepository;
import ca.bc.gov.educ.eas.api.repository.v1.SessionRepository;
import ca.bc.gov.educ.eas.api.struct.v1.Assessment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AssessmentControllerTest extends BaseEasAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    AssessmentRepository assessmentRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    AssessmentTypeCodeRepository assessmentTypeCodeRepository;

    private static final AssessmentMapper mapper = AssessmentMapper.mapper;

    @AfterEach
    public void after() {
        this.assessmentRepository.deleteAll();
        this.sessionRepository.deleteAll();
        this.assessmentTypeCodeRepository.deleteAll();
    }

    @Test
    void testUpdateAssessment_GivenInvalidSessionID_ShouldReturn400() throws Exception {
        final GrantedAuthority grantedAuthority = () -> "SCOPE_WRITE_EAS_SESSIONS";
        final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);

        Assessment assessment = createMockAssessment();
        assessment.setSessionID(UUID.randomUUID().toString());

        this.mockMvc.perform(
                        put(URL.ASSESSMENTS_URL + "/" + assessment.getAssessmentID())
                                .contentType(APPLICATION_JSON)
                                .content(asJsonString(assessment))
                                .with(mockAuthority))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.subErrors[?(@.field == 'sessionID')]").exists());
    }

    @Test
    void testUpdateAssessment_GivenInvalidAssessmentTypeCode_ShouldReturn400() throws Exception {
        final GrantedAuthority grantedAuthority = () -> "SCOPE_WRITE_EAS_SESSIONS";
        final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);

        SessionEntity session = sessionRepository.save(createMockSessionEntity());
        Assessment assessment = createMockAssessment();
        assessment.setSessionID(session.getSessionID().toString());
        assessment.setAssessmentTypeCode("INVALID");

        this.mockMvc.perform(
                        put(URL.ASSESSMENTS_URL + "/" + assessment.getAssessmentID())
                                .contentType(APPLICATION_JSON)
                                .content(asJsonString(assessment))
                                .with(mockAuthority))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.subErrors[?(@.field == 'assessmentTypeCode')]").exists());
    }

    @Test
    void testUpdateAssessment_GivenBadID_ShouldReturn400() throws Exception {
        final GrantedAuthority grantedAuthority = () -> "SCOPE_WRITE_EAS_SESSIONS";
        final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);

        Assessment assessment = createMockAssessment();
        assessment.setAssessmentID(String.valueOf(UUID.randomUUID()));

        this.mockMvc.perform(
                        put(URL.ASSESSMENTS_URL + "/" + UUID.randomUUID())
                                .contentType(APPLICATION_JSON)
                                .content(asJsonString(assessment))
                                .with(mockAuthority))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.subErrors[?(@.field == 'assessmentID')]").exists());
    }

    @Test
    void testUpdateAssessment_GivenValidPayload_ShouldReturnAssessment() throws Exception {
        final GrantedAuthority grantedAuthority = () -> "SCOPE_WRITE_EAS_SESSIONS";
        final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);

        assessmentTypeCodeRepository.save(createMockAssessmentTypeCodeEntity(AssessmentTypeCodes.LTF12.getCode()));

        SessionEntity session = sessionRepository.save(createMockSessionEntity());
        AssessmentEntity assessmentEntity = assessmentRepository.save(createMockAssessmentEntity(session, AssessmentTypeCodes.LTF12.getCode()));
        Assessment assessment = mapper.toStructure(assessmentEntity);
        assessment.setCreateDate(null);
        assessment.setUpdateDate(null);

        this.mockMvc.perform(
                        put(URL.ASSESSMENTS_URL + "/" + assessment.getAssessmentID())
                                .contentType(APPLICATION_JSON)
                                .content(asJsonString(assessment))
                                .with(mockAuthority))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.assessmentID", equalTo(assessment.getAssessmentID())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.updateUser", equalTo(ApplicationProperties.EAS_API)));
    }

    @Test
    void testDeleteAssessment_GivenInvalidID_ShouldReturn404() throws Exception {
        final GrantedAuthority grantedAuthority = () -> "SCOPE_WRITE_EAS_SESSIONS";
        final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);

        this.mockMvc.perform(
                        delete(URL.ASSESSMENTS_URL + "/" + UUID.randomUUID())
                                .with(mockAuthority))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteAssessment_GivenValidID_ShouldReturn204() throws Exception {
        final GrantedAuthority grantedAuthority = () -> "SCOPE_WRITE_EAS_SESSIONS";
        final SecurityMockMvcRequestPostProcessors.OidcLoginRequestPostProcessor mockAuthority = oidcLogin().authorities(grantedAuthority);

        SessionEntity session = sessionRepository.save(createMockSessionEntity());
        AssessmentEntity assessmentEntity = assessmentRepository.save(createMockAssessmentEntity(session, AssessmentTypeCodes.LTF12.getCode()));

        this.mockMvc.perform(
                        delete(URL.ASSESSMENTS_URL + "/" + assessmentEntity.getAssessmentID())
                                .with(mockAuthority))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


}
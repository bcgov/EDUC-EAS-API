package ca.bc.gov.educ.eas.api.service.v1;

import ca.bc.gov.educ.eas.api.BaseEasAPITest;
import ca.bc.gov.educ.eas.api.constants.EventOutcome;
import ca.bc.gov.educ.eas.api.constants.EventType;
import ca.bc.gov.educ.eas.api.constants.SagaEnum;
import ca.bc.gov.educ.eas.api.constants.v1.AssessmentStudentStatusCodes;
import ca.bc.gov.educ.eas.api.constants.v1.AssessmentTypeCodes;
import ca.bc.gov.educ.eas.api.mappers.v1.AssessmentStudentMapper;
import ca.bc.gov.educ.eas.api.model.v1.AssessmentEntity;
import ca.bc.gov.educ.eas.api.model.v1.AssessmentStudentEntity;
import ca.bc.gov.educ.eas.api.model.v1.SessionEntity;
import ca.bc.gov.educ.eas.api.repository.v1.*;
import ca.bc.gov.educ.eas.api.service.v1.events.RegistrationEventHandlerService;
import ca.bc.gov.educ.eas.api.struct.Event;
import ca.bc.gov.educ.eas.api.struct.v1.AssessmentStudent;
import ca.bc.gov.educ.eas.api.util.JsonUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class RegistrationEventHandlerServiceTest extends BaseEasAPITest {

    private static final AssessmentStudentMapper mapper = AssessmentStudentMapper.mapper;

    @Autowired
    AssessmentStudentService assessmentStudentService;
    @Autowired
    RegistrationEventHandlerService eventHandlerServiceUnderTest;
    @Autowired
    AssessmentStudentRepository assessmentStudentRepository;
    @Autowired
    AssessmentStudentHistoryRepository assessmentStudentHistoryRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    AssessmentRepository assessmentRepository;
    @Autowired
    SagaRepository sagaRepository;
    @Autowired
    SagaEventRepository sagaEventRepository;

    @AfterEach
    public void after() {
        assessmentStudentRepository.deleteAll();
        assessmentStudentHistoryRepository.deleteAll();
        assessmentRepository.deleteAll();
        sessionRepository.deleteAll();
        sagaEventRepository.deleteAll();
        sagaRepository.deleteAll();
    }

    @Test
    void testHandleEvent_givenEventType_GET_OPEN_ASSESSMENT_Loaded_Publish() throws IOException {
        SessionEntity session = sessionRepository.save(createMockSessionEntity());
        AssessmentEntity assessment = assessmentRepository.save(createMockAssessmentEntity(session, AssessmentTypeCodes.LTF12.getCode()));

        AssessmentStudent student1 = createMockStudent();
        student1.setAssessmentID(assessment.getAssessmentID().toString());
        AssessmentStudentEntity studentEntity1 = mapper.toModel(student1);
        studentEntity1.setAssessmentStudentStatusCode(AssessmentStudentStatusCodes.LOADED.getCode());
        AssessmentStudentEntity assessmentStudentEntity = assessmentStudentService.createStudent(studentEntity1);

        final Event event = Event.builder().eventType(EventType.PUBLISH_STUDENT_REGISTRATION_EVENT).eventOutcome(EventOutcome.STUDENT_REGISTRATION_EVENT_READ).eventPayload(JsonUtil.getJsonStringFromObject(mapper.toStructure(assessmentStudentEntity))).build();
        eventHandlerServiceUnderTest.handleEvent(event);
        var sagas = sagaRepository.findByAssessmentStudentIDAndSagaName(assessmentStudentEntity.getAssessmentStudentID(), SagaEnum.PUBLISH_STUDENT_REGISTRATION.name());
        assertThat(sagas).isPresent();
    }

    @Test
    void testHandleEvent_givenEventType_INVALID_Loaded_Publish() throws IOException {
        SessionEntity session = sessionRepository.save(createMockSessionEntity());
        AssessmentEntity assessment = assessmentRepository.save(createMockAssessmentEntity(session, AssessmentTypeCodes.LTF12.getCode()));

        AssessmentStudent student1 = createMockStudent();
        student1.setAssessmentID(assessment.getAssessmentID().toString());
        AssessmentStudentEntity studentEntity1 = mapper.toModel(student1);
        studentEntity1.setAssessmentStudentStatusCode(AssessmentStudentStatusCodes.LOADED.getCode());
        AssessmentStudentEntity assessmentStudentEntity = assessmentStudentService.createStudent(studentEntity1);
        final Event event = Event.builder().eventType(EventType.GET_OPEN_ASSESSMENT_SESSIONS).eventOutcome(EventOutcome.STUDENT_REGISTRATION_EVENT_READ).eventPayload(JsonUtil.getJsonStringFromObject(mapper.toStructure(assessmentStudentEntity))).build();
        eventHandlerServiceUnderTest.handleEvent(event);
        var sagas = sagaRepository.findByAssessmentStudentIDAndSagaName(assessmentStudentEntity.getAssessmentStudentID(), SagaEnum.PUBLISH_STUDENT_REGISTRATION.name());
        assertThat(sagas).isEmpty();
    }


}
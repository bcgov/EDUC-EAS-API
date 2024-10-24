package ca.bc.gov.educ.eas.api.repository.v1;


import ca.bc.gov.educ.eas.api.model.v1.AssessmentStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssessmentStudentRepository extends JpaRepository<AssessmentStudentEntity, UUID>, JpaSpecificationExecutor<AssessmentStudentEntity> {
    Optional<AssessmentStudentEntity> findByAssessmentEntity_AssessmentIDAndStudentID(UUID assessmentID, UUID studentID);
}

package ca.bc.gov.educ.eas.api.repository.v1;

import ca.bc.gov.educ.eas.api.model.v1.AssessmentTypeCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssessmentTypeCodeRepository extends JpaRepository<AssessmentTypeCodeEntity, String> {
    Optional<AssessmentTypeCodeEntity> findByAssessmentTypeCode(String assessmentTypeCode);

}

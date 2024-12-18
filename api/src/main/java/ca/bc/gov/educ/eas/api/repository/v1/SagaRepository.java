package ca.bc.gov.educ.eas.api.repository.v1;


import ca.bc.gov.educ.eas.api.model.v1.EasSagaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The interface Saga repository.
 */
@Repository
public interface SagaRepository extends JpaRepository<EasSagaEntity, UUID>, JpaSpecificationExecutor<EasSagaEntity> {

  long countAllByStatusIn(List<String> statuses);

  Optional<EasSagaEntity> findByAssessmentStudentIDAndSagaNameAndStatusNot(UUID assessmentStudentID, String sagaName, String status);

  Optional<EasSagaEntity> findByAssessmentStudentIDAndSagaName(UUID assessmentStudentID, String sagaName);

  @Transactional
  @Modifying
  @Query("delete from EasSagaEntity where createDate <= :createDate")
  void deleteByCreateDateBefore(LocalDateTime createDate);
}

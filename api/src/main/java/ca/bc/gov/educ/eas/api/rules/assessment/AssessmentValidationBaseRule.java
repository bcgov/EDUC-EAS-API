package ca.bc.gov.educ.eas.api.rules.assessment;

import ca.bc.gov.educ.eas.api.rules.Rule;
import ca.bc.gov.educ.eas.api.rules.StudentValidationIssueSeverityCode;
import ca.bc.gov.educ.eas.api.struct.v1.AssessmentStudentValidationIssue;
import ca.bc.gov.educ.eas.api.struct.v1.StudentRuleData;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface AssessmentValidationBaseRule extends Rule<StudentRuleData, AssessmentStudentValidationIssue> {

    default AssessmentStudentValidationIssue createValidationIssue(StudentValidationIssueSeverityCode severityCode, AssessmentStudentValidationFieldCode fieldCode, AssessmentStudentValidationIssueTypeCode typeCode){
        AssessmentStudentValidationIssue assessmentStudentValidationIssue = new AssessmentStudentValidationIssue();
        assessmentStudentValidationIssue.setValidationIssueSeverityCode(severityCode.toString());
        assessmentStudentValidationIssue.setValidationIssueCode(typeCode.getCode());
        assessmentStudentValidationIssue.setValidationIssueFieldCode(fieldCode.getCode());
        assessmentStudentValidationIssue.setAssessmentStudentValidationMessage(typeCode.getMessage());
        assessmentStudentValidationIssue.setAssessmentStudentValidationLabel(typeCode.getLabel());
        return assessmentStudentValidationIssue;
    }

    default boolean isValidationDependencyResolved(String fieldName, List<AssessmentStudentValidationIssue> validationErrorsMap) {
        Optional<AssessmentValidationRulesDependencyMatrix> errorCodesToCheck = AssessmentValidationRulesDependencyMatrix.findByValue(fieldName);
        if(errorCodesToCheck.isPresent()) {
            String[] errorCodes = errorCodesToCheck.get().getBaseRuleErrorCode();
            return validationErrorsMap.stream().noneMatch(val -> Arrays.stream(errorCodes).anyMatch(val.getValidationIssueCode()::contentEquals));
        }
        return false;
    }
}

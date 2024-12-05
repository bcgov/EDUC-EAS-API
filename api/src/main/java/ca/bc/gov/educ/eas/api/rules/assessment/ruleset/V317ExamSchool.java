package ca.bc.gov.educ.eas.api.rules.assessment.ruleset;

import ca.bc.gov.educ.eas.api.rules.StudentValidationIssueSeverityCode;
import ca.bc.gov.educ.eas.api.rules.assessment.AssessmentStudentValidationFieldCode;
import ca.bc.gov.educ.eas.api.rules.assessment.AssessmentStudentValidationIssueTypeCode;
import ca.bc.gov.educ.eas.api.rules.assessment.AssessmentValidationBaseRule;
import ca.bc.gov.educ.eas.api.struct.v1.AssessmentStudentValidationIssue;
import ca.bc.gov.educ.eas.api.struct.v1.StudentRuleData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ca.bc.gov.educ.eas.api.rules.utils.RuleUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *  | ID   | Severity | Rule                                                                  | Dependent On |
 *  |------|----------|-----------------------------------------------------------------------|--------------|
 *  | V317 | ERROR    |  Invalid assessment centre provided.                                  |--------------|
 *
 */
@Component
@Slf4j
@Order(240)
public class V317ExamSchool implements AssessmentValidationBaseRule {
    private final RuleUtil ruleUtil;

    public V317ExamSchool(RuleUtil ruleUtil) {
        this.ruleUtil = ruleUtil;
    }

    @Override
    public boolean shouldExecute(StudentRuleData studentRuleData, List<AssessmentStudentValidationIssue> validationErrorsMap) {
        log.debug("In shouldExecute of V317: for assessment {} and assessment student PEN :: {}", studentRuleData.getAssessmentStudentEntity().getAssessmentEntity().getAssessmentID() ,
                studentRuleData.getAssessmentStudentEntity().getPen());

        var shouldExecute = true;

        log.debug("In shouldExecute of V317: Condition returned - {} for assessment student PEN :: {}" ,
                shouldExecute,
                studentRuleData.getAssessmentStudentEntity().getPen());

        return  shouldExecute;
    }

    @Override
    public List<AssessmentStudentValidationIssue> executeValidation(StudentRuleData studentRuleData) {
        var student = studentRuleData.getAssessmentStudentEntity();
        log.debug("In executeValidation of V317 for assessment student PEN :: {}", student.getPen());
        final List<AssessmentStudentValidationIssue> errors = new ArrayList<>();

        if (!ruleUtil.validateAssessmentCenterIsValid(String.valueOf(student.getAssessmentCenterID()))){
            log.debug("V317: Invalid assessment centre provided with schoolID :: {}", student.getSchoolID());
            errors.add(createValidationIssue(StudentValidationIssueSeverityCode.ERROR, AssessmentStudentValidationFieldCode.EXAM_SCHOOL, AssessmentStudentValidationIssueTypeCode.EXAM_SCHOOL_INVALID));
        }
        return errors;
    }

}
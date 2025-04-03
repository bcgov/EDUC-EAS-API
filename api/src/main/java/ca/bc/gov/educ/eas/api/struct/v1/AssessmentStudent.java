package ca.bc.gov.educ.eas.api.struct.v1;

import ca.bc.gov.educ.eas.api.struct.OnUpdate;
import ca.bc.gov.educ.eas.api.validator.constraint.IsAllowedValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssessmentStudent extends BaseRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(groups = OnUpdate.class, message = "assessmentStudentID cannot be null ")
    private String assessmentStudentID;

    @NotBlank(message = "assessmentID cannot be null")
    private String assessmentID;

    @NotBlank(message = "districtID cannot be null")
    private String districtID;

    @NotBlank(message = "schoolID cannot be null")
    private String schoolID;

    private String assessmentCenterID;

    @NotBlank(groups = OnUpdate.class, message = "studentID cannot be null")
    private String studentID;

    @NotBlank(message = "givenName cannot be null")
    private String givenName;

    @NotBlank(message = "surName cannot be null")
    private String surname;

    @NotBlank(message = "pen cannot be null")
    @Size(max = 9)
    private String pen;

    @Size(max = 12)
    private String localID;

    private String isElectronicExam;

    @Size(max = 1)
    private String proficiencyScore;

    @Size(max = 1)
    @IsAllowedValue(enumName = "ProvincialSpecialCaseCodes", message = "Invalid provincial special case code.")
    private String provincialSpecialCaseCode;

    @Size(max = 1)
    @IsAllowedValue(enumName = "CourseStatusCodes", message = "Invalid course status code.")
    private String courseStatusCode;

    private List<AssessmentStudentValidationIssue> assessmentStudentValidationIssues;

    private String numberOfAttempts;
}

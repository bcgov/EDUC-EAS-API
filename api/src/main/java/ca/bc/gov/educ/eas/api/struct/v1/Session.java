package ca.bc.gov.educ.eas.api.struct.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for Session entity.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Session extends BaseRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  @ReadOnlyProperty
  private String sessionID;

  @ReadOnlyProperty
  private String schoolYear;

  @ReadOnlyProperty
  private String courseMonth;

  @ReadOnlyProperty
  private String courseYear;

  @NotNull(message = "activeFromDate cannot be null")
  private String activeFromDate;

  @NotNull(message = "activeUntilDate cannot be null")
  private String activeUntilDate;

  private List<Assessment> assessments;
}

package ca.bc.gov.educ.eas.api.struct.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

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
  private Integer courseSession;

  @ReadOnlyProperty
  private Integer courseMonth;

  @ReadOnlyProperty
  private Integer courseYear;

  @ReadOnlyProperty
  private String status;

  @NotNull(message = "activeFromDate cannot be null")
  private LocalDateTime activeFromDate;

  @NotNull(message = "activeUntilDate cannot be null")
  private LocalDateTime activeUntilDate;
}

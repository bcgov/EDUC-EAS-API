package ca.bc.gov.educ.eas.api.struct;

import ca.bc.gov.educ.eas.api.constants.EventOutcome;
import ca.bc.gov.educ.eas.api.constants.EventType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * The type Event.
 */
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
  /**
   * The Event type.
   */
  private EventType eventType;
  /**
   * The Event outcome.
   */
  private EventOutcome eventOutcome;
  /**
   * The Saga id.
   */
  private UUID sagaId;
  /**
   * The Reply to.
   */
  private String replyTo;
  /**
   * The Event payload.
   */
  private String eventPayload; // json string
}

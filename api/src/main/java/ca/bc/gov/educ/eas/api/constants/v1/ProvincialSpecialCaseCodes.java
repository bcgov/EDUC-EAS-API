package ca.bc.gov.educ.eas.api.constants.v1;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Getter
public enum ProvincialSpecialCaseCodes {
  EXEMPTION("E"),
  DEFERRAL("D");

  private final String code;
  ProvincialSpecialCaseCodes(String code) {
    this.code = code;
  }

  public static Optional<ProvincialSpecialCaseCodes> findByValue(String value) {
    return Arrays.stream(values()).filter(e -> Objects.equals(e.code, value)).findFirst();
  }
}

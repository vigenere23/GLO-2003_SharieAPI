package com.github.glo2003.helpers;

import static com.google.common.truth.Truth.assertThat;

import java.time.Instant;
import java.time.format.DateTimeParseException;

import org.junit.Before;
import org.junit.Test;

public class DateTimeHelperTest {

  Instant now;

  String dateRegex = "[0-2]([0-9]){3}-[0-1][0-9]-[0-3][0-9]";

  @Before
  public void setupBefore() {
    now = Instant.now();
  }

  @Test(expected = DateTimeParseException.class)
  public void givenInvalidFormatDateString_convertToInstant_shouldRaiseException() throws DateTimeParseException {
    DateTimeHelper.fromDateStringToInstant("32165");
  }

  @Test(expected = DateTimeParseException.class)
  public void givenInvalidRangeDateString_convertToInstant_shouldRaiseException() throws DateTimeParseException {
    DateTimeHelper.fromDateStringToInstant("2019-26-45");
  }

  @Test
  public void removeTimeFromInstant_souldResetTimeTo0() {
    assertThat(DateTimeHelper.removeTimeFromInstant(now).toString()).matches("^" + dateRegex + "T00:00:00Z$");
  }

  @Test
  public void convertInstantToString_shouldReturnValidDateFormat() {
    assertThat(DateTimeHelper.fromInstantToDateString(now)).matches("^" + dateRegex + "$");
  }

}
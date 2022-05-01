package com.smaato.task.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskUtil {

  public static boolean validateUri(String uri) {
    UrlValidator defaultValidator = new UrlValidator();
      return uri != null && !uri.isBlank() && !uri.isEmpty() && defaultValidator.isValid(uri);
  }
}

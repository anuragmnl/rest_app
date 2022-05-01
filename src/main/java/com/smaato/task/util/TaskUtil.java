package com.smaato.task.util;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskUtil {

  public static boolean isUrlValid(String url) {
    try {
      URL obj = new URL(url);
      obj.toURI();
      return true;
    } catch ( MalformedURLException | URISyntaxException e) {
      return false;
    }
  }
}

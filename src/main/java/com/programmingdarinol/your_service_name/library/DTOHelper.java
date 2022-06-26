package com.programmingdarinol.your_service_name.library;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DTOHelper {

  static ObjectMapper mapper = new ObjectMapper();

  public static <T> T mapTo(Object fromValue, Class<T> to){
    var value = mapper.convertValue(fromValue, to);
    return to.cast(value);
  }
}

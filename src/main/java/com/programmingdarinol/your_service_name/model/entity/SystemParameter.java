package com.programmingdarinol.your_service_name.model.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "system_parameter")
public class SystemParameter implements Serializable {

  @Serial
  private static final long serialVersionUID = -2438965738314450378L;

  @Id
  private String id;
  private String variable;
  private String value;
  private String description;
  @CreatedDate
  private Date createdDate;
  @CreatedBy
  private String createdBy;
  @LastModifiedDate
  private Date updatedDate;
  @LastModifiedBy
  private String updatedBy;
  private boolean deleted = false;
}
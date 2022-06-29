package com.freedom.health.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import com.freedom.health.constants.Enums.HEALTH_STATUS;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HealthResponse {

  private ApplicationInfo applicationInfo;
  private HEALTH_STATUS healthStatus;
  private List<DependencyHealthResponse> dependencyHealthResponses;
  private String applicationStartTime;
}

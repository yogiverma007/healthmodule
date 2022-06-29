package com.freedom.health.service;

import com.freedom.health.constants.Enums;
import com.freedom.health.model.DependencyHealthResponse;
import com.freedom.health.model.HealthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HealthService {

  @Autowired(required = false)
  private List<HealthCheck> healthChecks;

  private final AppInfoService appInfoService;

  @Autowired
  public HealthService(AppInfoService appInfoService) {
    this.appInfoService = appInfoService;
  }

  public HealthResponse healthDependencies(HttpServletResponse httpServletResponse) {
    HealthResponse healthResponse = new HealthResponse();
    healthResponse.setHealthStatus(Enums.HEALTH_STATUS.HEALTHY);
    healthResponse.setApplicationInfo(appInfoService.createApplicationInfo());
    healthResponse.setApplicationStartTime(getApplicationStartTime().toString());

    List<DependencyHealthResponse> dependencyHealthResponses = new ArrayList<>();
    for (HealthCheck healthCheck : healthChecks) {
      DependencyHealthResponse dependencyHealthResponse = null;
      try {
        dependencyHealthResponse = healthCheck.health();
      } catch (Exception e) {
        dependencyHealthResponse = new DependencyHealthResponse();
        dependencyHealthResponse.setHealthStatus(Enums.HEALTH_STATUS.NON_HEALTHY);
        dependencyHealthResponse.setEntity(healthCheck.entity());
        dependencyHealthResponse.setCause(e.getLocalizedMessage());
      } finally {
        dependencyHealthResponses.add(dependencyHealthResponse);
        if (dependencyHealthResponse != null) {
          setHealthAsFailure(
              dependencyHealthResponse.getHealthStatus(), httpServletResponse, healthResponse);
        } else {
          setHealthAsFailure(Enums.HEALTH_STATUS.NON_HEALTHY, httpServletResponse, healthResponse);
        }
      }
    }
    healthResponse.setDependencyHealthResponses(dependencyHealthResponses);
    return healthResponse;
  }

  private Date getApplicationStartTime() {
    RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
    long startTime = runtimeBean.getStartTime();
    Date startDate = new Date(startTime);
    return startDate;
  }

  private void setHealthAsFailure(
      Enums.HEALTH_STATUS healthStatus,
      HttpServletResponse httpServletResponse,
      HealthResponse healthResponse) {
    if (healthStatus.equals(Enums.HEALTH_STATUS.NON_HEALTHY)) {
      healthResponse.setHealthStatus(Enums.HEALTH_STATUS.NON_HEALTHY);
      httpServletResponse.setStatus(HttpStatus.FAILED_DEPENDENCY.value());
    }
  }
}

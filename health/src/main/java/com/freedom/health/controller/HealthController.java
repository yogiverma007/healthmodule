package com.freedom.health.controller;

import com.freedom.health.model.HealthResponse;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.freedom.health.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping("/api")
public class HealthController {

  private final HealthService healthService;

  @Autowired
  public HealthController(HealthService healthService) {
    this.healthService = healthService;
  }

  @GetMapping("/health")
  @ApiOperation(
      value =
          "This is Health Api to detect service health."
              + " Also this api will return all dependencies required for this service to get up and running")
  public HealthResponse health(HttpServletResponse httpServletResponse) {
    HealthResponse healthResponse = healthService.healthDependencies(httpServletResponse);
    log.info("Returning healthResponse: {}", healthResponse);
    return healthResponse;
  }
}

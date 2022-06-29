package com.freedom.health.service;

import com.freedom.health.model.HealthResponse;
import com.freedom.health.model.ApplicationInfo;
import com.freedom.health.model.DependencyHealthResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static com.freedom.health.constants.Enums.HEALTH_STATUS.HEALTHY;
import static com.freedom.health.constants.Enums.HEALTH_STATUS.NON_HEALTHY;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class HealthServiceTest {

  @Mock private HttpServletResponse httpServletResponse;

  @InjectMocks private HealthService healthService;

  @Spy private List<HealthCheck> healthChecks = new ArrayList<>();

  @Mock private DependencyHealthResponse dependencyHealthResponse;

  @Mock private HealthCheck healthCheck;

  @Mock private AppInfoService appInfoService;

  @Mock private ApplicationInfo applicationInfo;

  @Test
  public void healthDependencies_test_Failure() {

    when(healthCheck.health()).thenReturn(dependencyHealthResponse);
    when(dependencyHealthResponse.getHealthStatus()).thenReturn(NON_HEALTHY);
    when(appInfoService.createApplicationInfo()).thenReturn(applicationInfo);
    HealthResponse healthResponse = healthService.healthDependencies(httpServletResponse);
    assertEquals(NON_HEALTHY, healthResponse.getHealthStatus());
  }

  @Test
  public void healthDependencies_test_Success() {

    when(healthCheck.health()).thenReturn(dependencyHealthResponse);
    when(dependencyHealthResponse.getHealthStatus()).thenReturn(HEALTHY);
    when(appInfoService.createApplicationInfo()).thenReturn(applicationInfo);
    HealthResponse healthResponse = healthService.healthDependencies(httpServletResponse);
    assertEquals(HEALTHY, healthResponse.getHealthStatus());
  }

  @Test
  public void healthDependencies_test_Exception_Failure() {
    when(healthCheck.health()).thenThrow(NullPointerException.class);
    when(appInfoService.createApplicationInfo()).thenReturn(applicationInfo);
    HealthResponse healthResponse = healthService.healthDependencies(httpServletResponse);
    assertEquals(NON_HEALTHY, healthResponse.getHealthStatus());
  }

  @Before
  public void init() {
    MockitoAnnotations.openMocks(this);
    healthChecks.add(healthCheck);
  }
}

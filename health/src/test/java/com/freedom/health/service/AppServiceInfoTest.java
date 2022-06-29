package com.freedom.health.service;

import com.freedom.health.model.ApplicationInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AppServiceInfoTest {

  @InjectMocks private AppInfoService appInfoService;

  @Mock private GitProperties gitProperties;

  @Mock private BuildProperties buildProperties;

  @Test
  public void createApplicationInfo_test_success() {
    when(buildProperties.getVersion()).thenReturn("21");
    when(buildProperties.getArtifact()).thenReturn("abc");
    when(buildProperties.getGroup()).thenReturn("abc");
    when(buildProperties.getName()).thenReturn("abc");
    when(buildProperties.getTime()).thenReturn(Instant.EPOCH);
    when(gitProperties.getBranch()).thenReturn("develop");
    when(gitProperties.getCommitId()).thenReturn("12");
    when(gitProperties.getCommitTime()).thenReturn(Instant.EPOCH);
    when(gitProperties.getShortCommitId()).thenReturn("20");
    ApplicationInfo applicationInfo = appInfoService.createApplicationInfo();
    assertEquals("card-payments", applicationInfo.getApplicationName());
    assertEquals("develop", applicationInfo.getBranch());
  }

  @Before
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    appInfoService = new AppInfoService(gitProperties, buildProperties, "card-payments");
  }
}

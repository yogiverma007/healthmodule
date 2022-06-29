package com.freedom.health.service;

import com.freedom.health.model.ApplicationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.stereotype.Component;

@ConditionalOnResource(
    resources = "${spring.info.build.location:classpath:META-INF/build-info.properties}")
@Component
@Slf4j
public class AppInfoService {

  private final GitProperties gitProperties;
  private final BuildProperties buildProperties;
  private final String applicationName;
  private static final String COMMIT_USER_NAME = "commit.user.name";
  private static final String COMMIT_MESSAGE_FULL = "commit.message.full";

  @Autowired
  public AppInfoService(
      GitProperties gitProperties,
      BuildProperties buildProperties,
      @Value("${application.name}") String applicationName) {
    this.applicationName = applicationName;
    this.gitProperties = gitProperties;
    this.buildProperties = buildProperties;
  }

  public ApplicationInfo createApplicationInfo() {
    ApplicationInfo applicationInfo = new ApplicationInfo();
    applicationInfo.setApplicationName(applicationName);

    applicationInfo.setGroupId(buildProperties.getGroup());
    applicationInfo.setArtifactId(buildProperties.getArtifact());
    applicationInfo.setVersion(buildProperties.getVersion());

    if (buildProperties.getTime() != null)
      applicationInfo.setBuildTime(buildProperties.getTime().toString());

    applicationInfo.setBranch(gitProperties.getBranch());
    applicationInfo.setCommitId(gitProperties.getCommitId());

    if (gitProperties.getCommitTime() != null)
      applicationInfo.setCommitTime(gitProperties.getCommitTime().toString());

    applicationInfo.setLastCommitBy(gitProperties.get(COMMIT_USER_NAME));
    applicationInfo.setCommitMessage(gitProperties.get(COMMIT_MESSAGE_FULL));

    return applicationInfo;
  }
}

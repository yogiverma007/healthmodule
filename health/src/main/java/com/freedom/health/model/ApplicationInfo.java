package com.freedom.health.model;

import lombok.Data;

@Data
public class ApplicationInfo {

  private String groupId;
  private String artifactId;
  private String version;
  private String buildTime;
  private String branch;
  private String commitId;
  private String commitTime;
  private String lastCommitBy;
  private String commitMessage;
  private String applicationName;
}

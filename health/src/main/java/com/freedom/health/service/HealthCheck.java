package com.freedom.health.service;

import com.freedom.health.model.DependencyHealthResponse;

public interface HealthCheck {

  DependencyHealthResponse health();

  String entity();
}

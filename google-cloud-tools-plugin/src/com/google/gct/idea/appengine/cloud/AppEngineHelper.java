/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.gct.idea.appengine.cloud;

import com.intellij.remoteServer.runtime.deployment.ServerRuntimeInstance.DeploymentOperationCallback;
import com.intellij.remoteServer.runtime.log.LoggingHandler;

import java.io.File;

/**
 * Provides basic Gcloud based App Engine functionality for our Cloud Tools plugin.
 */
public interface AppEngineHelper {

  /**
   * The path to the gcloud command on the local file system.
   */
  File getGcloudCommandPath();

  /**
   * The app engine project ID configured for this helper.
   */
  String getProjectId();

  /**
   * The default app.yaml to use.
   */
  File defaultAppYaml();

  /**
   * The default Dockerfile we suggest for custom MVM deployments.
   *
   * @param deploymentArtifactType depending on the artifact type we provide a different default
   *                               Dockerfile
   * @return A {@link java.io.File} path to the default Dockerfile
   */
  File defaultDockerfile(DeploymentArtifactType deploymentArtifactType);

  /**
   * Creates a {@link Runnable} that will perform custom MVM deployment on {@code run()).
   *
   * @param loggingHandler logging messages will be output to this
   * @param artifactToDeploy the {@link File} path to the Java artifact to be deployed
   * @param appYamlPath the {@link File} path to the app.yaml to use for deployment
   * @param dockerfilePath the {@link File} path to the Dockerfile to be used for the custom MVM
   *                       runtime
   * @param deploymentCallback a callback for handling successful completion of the operation
   * @return the runnable that will perform the deployment operation
   */
  Runnable createCustomDeploymentOperation(
      LoggingHandler loggingHandler,
      File artifactToDeploy,
      File appYamlPath,
      File dockerfilePath,
      DeploymentOperationCallback deploymentCallback);

  /**
   * Creates a {@link Runnable} that will perform a standard MVM deployment with an automatically
   * configured runtime (app.yaml and Dockerfile) on {@code run()).
   *
   * @param loggingHandler logging messages will be output to this
   * @param artifactToDeploy the {@link File} path to the Java artifact to be deployed
   * @param deploymentCallback a callback for handling successful completion of the operation
   * @return the runnable that will perform the deployment operation
   */
  Runnable createAutoDeploymentOperation(
      LoggingHandler loggingHandler,
      File artifactToDeploy,
      DeploymentOperationCallback deploymentCallback);
}
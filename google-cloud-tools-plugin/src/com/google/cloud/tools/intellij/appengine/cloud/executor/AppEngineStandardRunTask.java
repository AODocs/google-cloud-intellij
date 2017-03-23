/*
 * Copyright 2016 Google Inc. All Rights Reserved.
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

package com.google.cloud.tools.intellij.appengine.cloud.executor;

import com.google.cloud.tools.appengine.api.devserver.AppEngineDevServer;
import com.google.cloud.tools.appengine.api.devserver.RunConfiguration;
import com.google.cloud.tools.appengine.cloudsdk.CloudSdk;
import com.google.cloud.tools.appengine.cloudsdk.CloudSdkAppEngineDevServer;
import com.google.cloud.tools.appengine.cloudsdk.CloudSdkAppEngineDevServer1;
import com.google.cloud.tools.appengine.cloudsdk.process.ProcessStartListener;
import com.google.cloud.tools.intellij.appengine.sdk.CloudSdkService;
import com.google.cloud.tools.intellij.appengine.sdk.CloudSdkVersionNotifier;
import com.google.cloud.tools.intellij.stats.UsageTrackerProvider;
import com.google.cloud.tools.intellij.util.GctTracking;
import com.google.common.base.Strings;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an App Engine Standard run task. (i.e., devappserver)
 */
public class AppEngineStandardRunTask extends AppEngineTask {

  private RunConfiguration runConfig;
  private String runnerId;
  private boolean useDevappserver2;

  /**
   * {@link AppEngineStandardRunTask} constructor.
   *
   * @param runConfig local run configuration to be sent to the common library
   * @param runnerId typically "Run" or "Debug", to indicate type of local run. To be used in
   *     metrics
   */
  public AppEngineStandardRunTask(@NotNull RunConfiguration runConfig, @Nullable String runnerId,
      boolean useDevappserver2) {
    this.runConfig = runConfig;
    this.runnerId = runnerId;
    this.useDevappserver2 = useDevappserver2;
  }

  @Override
  public void execute(ProcessStartListener startListener) {
    CloudSdkService sdkService = CloudSdkService.getInstance();

    // show a warning notification if the cloud sdk version is not supported
    CloudSdkVersionNotifier.getInstance().notifyIfUnsupportedVersion();

    CloudSdk sdk = new CloudSdk.Builder()
        .sdkPath(sdkService.getSdkHomePath())
        .async(true)
        .startListener(startListener)
        .build();

    AppEngineDevServer devServer = useDevappserver2
        ? new CloudSdkAppEngineDevServer(sdk)
        : new CloudSdkAppEngineDevServer1(sdk);
    devServer.run(runConfig);

    UsageTrackerProvider.getInstance()
        .trackEvent(GctTracking.APP_ENGINE_RUN)
        .addMetadata(GctTracking.METADATA_LABEL_KEY, Strings.nullToEmpty(runnerId))
        .ping();

  }
}

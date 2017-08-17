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

package com.google.cloud.tools.intellij.appengine.cloud.flexible;

import static com.google.cloud.tools.intellij.appengine.cloud.flexible.AppEngineFlexibleDeploymentArtifactType.JAR;
import static com.google.cloud.tools.intellij.appengine.cloud.flexible.AppEngineFlexibleDeploymentArtifactType.UNKNOWN;
import static com.google.cloud.tools.intellij.appengine.cloud.flexible.AppEngineFlexibleDeploymentArtifactType.WAR;
import static com.google.common.truth.Truth.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Test cases for {@link AppEngineFlexibleDeploymentArtifactType}. */
@RunWith(JUnit4.class)
public final class AppEngineFlexibleDeploymentArtifactTypeTest {

  @Test
  public void typeForPath_withJarPath_doesReturnJar() {
    Path jarPath = Paths.get("some", "path", "to", "a.jar");
    assertThat(AppEngineFlexibleDeploymentArtifactType.typeForPath(jarPath)).isEqualTo(JAR);
  }

  @Test
  public void typeForPath_withWarPath_doesReturnWar() {
    Path warPath = Paths.get("some", "path", "to", "a.war");
    assertThat(AppEngineFlexibleDeploymentArtifactType.typeForPath(warPath)).isEqualTo(WAR);
  }

  @Test
  public void typeForPath_withOtherExtension_doesReturnUnknown() {
    Path txtPath = Paths.get("some", "path", "to", "a.txt");
    assertThat(AppEngineFlexibleDeploymentArtifactType.typeForPath(txtPath)).isEqualTo(UNKNOWN);
  }

  @Test
  public void typeForPath_withNull_doesReturnUnknown() {
    assertThat(AppEngineFlexibleDeploymentArtifactType.typeForPath(null)).isEqualTo(UNKNOWN);
  }

  @Test
  public void getDefaultArtifactName_withJar_doesReturnTargetJar() {
    assertThat(JAR.getDefaultArtifactName()).isEqualTo("target.jar");
  }

  @Test
  public void getDefaultArtifactName_withWar_doesReturnTargetWar() {
    assertThat(WAR.getDefaultArtifactName()).isEqualTo("target.war");
  }

  @Test
  public void getDefaultArtifactName_withUnknown_doesReturnEmptyString() {
    assertThat(UNKNOWN.getDefaultArtifactName()).isEmpty();
  }
}

/*
 * Copyright 2017 Google Inc. All Rights Reserved.
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

package com.google.cloud.tools.intellij.apis;

import com.google.cloud.tools.intellij.appengine.facet.standard.AppEngineStandardFacet;
import com.google.cloud.tools.intellij.appengine.facet.standard.AppEngineStandardFacetType;
import com.google.cloud.tools.intellij.appengine.project.AppEngineProjectService;
import com.google.cloud.tools.intellij.ui.GoogleCloudToolsIcons;
import com.google.cloud.tools.intellij.util.GctBundle;
import com.intellij.facet.FacetManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.DumbAwareAction;
import git4idea.DialogManager;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.JavaVersion;

/**
 * The action in the Google Cloud Tools menu group that opens the wizard to add client libraries to
 * the user's project and manage cloud APIs.
 */
public final class AddCloudLibrariesAction extends DumbAwareAction {

  public AddCloudLibrariesAction() {
    super(
        GctBundle.message("cloud.libraries.menu.action.text"),
        GctBundle.message("cloud.libraries.menu.action.description"),
        GoogleCloudToolsIcons.CLOUD);
  }

  @Override
  @SuppressWarnings("MissingCasesInEnumSwitch")
  public void update(AnActionEvent e) {
    boolean addLibrariesEnabled = false;
    if (e.getProject() != null) {
      Set<CloudLibrariesModuleSupportType> moduleSupportTypes =
          Stream.of(ModuleManager.getInstance(e.getProject()).getModules())
              .map(this::checkModuleForAddCloudLibraries)
              .collect(Collectors.toSet());

      addLibrariesEnabled = moduleSupportTypes.contains(CloudLibrariesModuleSupportType.SUPPORTED);
      if (!addLibrariesEnabled) {
        // update message to hint what is missing.
        for (CloudLibrariesModuleSupportType supportType : moduleSupportTypes) {
          switch (supportType) {
            case MAVEN_REQUIRED:
              e.getPresentation()
                  .setDescription(
                      GctBundle.message("cloud.libraries.menu.action.maven.required.description"));
              break;
            case APPENGINE_JAVA8_REQUIRED:
              e.getPresentation()
                  .setDescription(
                      GctBundle.message(
                          "cloud.libraries.menu.action.gae.java8.required.description"));
              break;
          }
        }
      } else {
        // standard message for a supported module action.
        e.getPresentation()
            .setDescription(GctBundle.message("cloud.libraries.menu.action.description"));
      }
    }

    e.getPresentation().setEnabled(addLibrariesEnabled);
  }

  @Override
  public void actionPerformed(AnActionEvent e) {
    if (e.getProject() != null) {
      AddCloudLibrariesDialog librariesDialog = new AddCloudLibrariesDialog(e.getProject());
      DialogManager.show(librariesDialog);

      if (librariesDialog.isOK()) {
        CloudLibraryDependencyWriter.addLibraries(
            librariesDialog.getSelectedLibraries(), librariesDialog.getSelectedModule());
      }
    }
  }

  private enum CloudLibrariesModuleSupportType {
    SUPPORTED,
    MAVEN_REQUIRED,
    APPENGINE_JAVA8_REQUIRED
  }

  private CloudLibrariesModuleSupportType checkModuleForAddCloudLibraries(Module module) {
    // AppEngine Standard + Java 7 are not supported for GCP Libraries
    if (AppEngineProjectService.getInstance().hasAppEngineStandardFacet(module)) {
      AppEngineStandardFacet appEngineStandardFacet =
          FacetManager.getInstance(module).getFacetByType(AppEngineStandardFacetType.ID);
      if (!appEngineStandardFacet.getRuntimeJavaVersion().atLeast(JavaVersion.JAVA_1_8))
        return CloudLibrariesModuleSupportType.APPENGINE_JAVA8_REQUIRED;
    }

    return AppEngineProjectService.getInstance().isMavenModule(module)
        ? CloudLibrariesModuleSupportType.SUPPORTED
        : CloudLibrariesModuleSupportType.MAVEN_REQUIRED;
  }
}

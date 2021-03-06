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

package com.google.cloud.tools.intellij.resources;

import com.intellij.ui.components.JBLabel;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

/** UI for node in the repository selector representing a single GCP repository. */
public class RepositoryItem extends JBLabel {

  public RepositoryItem() {
    setBorder(BorderFactory.createEmptyBorder(2, 15, 2, 0));
    setOpaque(false);
    setHorizontalAlignment(SwingConstants.LEFT);
    setVerticalAlignment(SwingConstants.CENTER);
  }
}

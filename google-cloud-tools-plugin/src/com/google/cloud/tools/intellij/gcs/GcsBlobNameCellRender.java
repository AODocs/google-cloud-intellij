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

package com.google.cloud.tools.intellij.gcs;

import com.intellij.icons.AllIcons.Nodes;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/** Created by eshaul on 8/25/17. */
public class GcsBlobNameCellRender extends DefaultTableCellRenderer {

  public GcsBlobNameCellRender() {
    setOpaque(true);
  }

  @Override
  public Component getTableCellRendererComponent(
      JTable table, Object blobName, boolean isSelected, boolean hasFocus, int row, int column) {
    Component c = super.getTableCellRendererComponent(table, blobName, isSelected, hasFocus, row, column);

    String name = (String) blobName;
    setText(name);

    if (name.endsWith("/")) {
      c.setForeground(Color.GREEN);
      setIcon(Nodes.Folder);
    }

    return c;
  }
}

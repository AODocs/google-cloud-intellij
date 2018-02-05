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

package com.google.cloud.tools.intellij.testing.log;

import com.intellij.openapi.diagnostic.Logger;
import java.util.Arrays;
import org.apache.log4j.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** IJ based diagnostic logger for tests, logs everything into console. */
public class TestConsoleLogger extends Logger {
  @Override
  public boolean isDebugEnabled() {
    return true;
  }

  @Override
  public void debug(String message) {
    System.out.println(message);
  }

  @Override
  public void debug(@Nullable Throwable t) {
    if (t != null) {
      t.printStackTrace();
    }
  }

  @Override
  public void debug(String message, @Nullable Throwable t) {
    System.out.println(message);
    debug(t);
  }

  @Override
  public void info(String message) {
    System.out.println(message);
  }

  @Override
  public void info(String message, @Nullable Throwable t) {
    System.out.println(message);
    debug(t);
  }

  @Override
  public void warn(String message, @Nullable Throwable t) {
    System.out.println(message);
    debug(t);
  }

  @Override
  public void error(String message, @Nullable Throwable t, @NotNull String... details) {
    System.out.println(message);
    System.out.println(Arrays.toString(details));
    debug(t);
  }

  @Override
  public void setLevel(Level level) {
    /* unsupported */
  }
}

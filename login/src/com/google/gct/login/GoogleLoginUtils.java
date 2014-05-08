/*
 * Copyright (C) 2014 The Android Open Source Project
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
package com.google.gct.login;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;

import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.Image;
import java.awt.Toolkit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Utility methods of Google Login.
 */
public class GoogleLoginUtils {
  public static final Logger LOG = Logger.getInstance(GoogleLoginUtils.class);
  public static final int DEFAULT_PICTURE_SIZE = 96;

  /**
   * Gets the user's picture from  <code>userInfo</code>.
   *
   * @param userInfo  the class to be parsed.
   * @param pictureCallback
   * @return the user's picture from <code>userInfo</code>
   */
  @Nullable
  public static void getUserPicture(Userinfoplus userInfo, final IUserPropertyCallback pictureCallback) {
    // set the size of the image before it is served
    String urlString = userInfo.getPicture() + "?sz=" + DEFAULT_PICTURE_SIZE;
    URL url = null;
    try {
      url = new URL(urlString);
    }
    catch (MalformedURLException e) {
      LOG.error(e);
      // Should users be able to see picture url?
    }

    final URL newUrl = url;

    ApplicationManager.getApplication().executeOnPooledThread(new Runnable() {
      @Override
      public void run() {
        Image image = Toolkit.getDefaultToolkit().getImage(newUrl);
        pictureCallback.setProperty(image);
      }
    });
  }

  @Nullable
  public static void getUserInfo(@NotNull final Credential credential,
    final IUserPropertyCallback callback) {
    final Oauth2 userInfoService =
      new Oauth2.Builder(new NetHttpTransport(), new JacksonFactory(), credential)
        .setApplicationName("Android Studio")
        .build();

    ApplicationManager.getApplication().executeOnPooledThread(new Runnable() {
      @Override
      public void run() {
        Userinfoplus userInfo = null;
        try {
          userInfo = userInfoService.userinfo().get().execute();
        } catch (IOException e) {
          Messages.showErrorDialog("An error occurred while retrieving user information.\n" +
            e.getMessage() + "\nPlease check the error log for more detail.",
            "Error occurred while retrieving user information");
          LOG.error("Error retrieving user information.", e);
        }

        if (userInfo != null && userInfo.getId() != null) {
          callback.setProperty(userInfo);
        } else {
          callback.setProperty(null);
        }
      }
    });
  }

  /**
   * Returns a {@link Credential} object for a fake user.
   * Used for testing.
   * @return a {@link Credential} object for the fake user.
   */
  public static Credential makeFakeUserCredential() {
    String clientId = System.getenv().get("ANDROID_CLIENT_ID");
    String clientSecret = System.getenv().get("ANDROID_CLIENT_SECRET");
    String refreshToken = System.getenv().get("FAKE_USER_REFRESH_TOKEN");
    String accessToken = System.getenv().get("FAKE_USER_ACCESS_TOKEN");

    Credential cred =
      new GoogleCredential.Builder()
        .setJsonFactory(new JacksonFactory())
        .setTransport(new NetHttpTransport())
        .setClientSecrets(clientId, clientSecret)
        .build();
    cred.setAccessToken(accessToken);
    cred.setRefreshToken(refreshToken);
    return cred;
  }
}

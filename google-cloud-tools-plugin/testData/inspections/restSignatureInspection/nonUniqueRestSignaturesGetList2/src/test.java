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
package com.example.app;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.DefaultValue;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.CollectionResponse;

import java.lang.Boolean;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;


@Api
public class MyClass {
  // GET "list2"
  @ApiMethod(path="", httpMethod = "")
  public ArrayListOfString list2(@DefaultValue @Named("id") int id){
    return  null;
  }

  // GET "list2"
  @ApiMethod(path="", httpMethod = "")
  public ArrayListOfString list2(@DefaultValue @Named("id") double id){
    return  null;
  }
  public class ArrayListOfString extends ArrayList<String> {}
}
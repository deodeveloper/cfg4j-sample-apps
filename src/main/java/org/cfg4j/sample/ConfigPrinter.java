/*
 * Copyright 2015 Norbert Potocki (norbert.potocki@nort.pl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cfg4j.sample;

import org.cfg4j.provider.ConfigurationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConfigPrinter implements CommandLineRunner {

  private final ConfigurationProvider configurationProvider;

  @Autowired
  public ConfigPrinter(ConfigurationProvider configurationProvider) {
    this.configurationProvider = configurationProvider;
  }

  @Override
  public void run(String... args) throws Exception {
    String previousSettingValue = null;

    while (true) {
      String newSettingValue = configurationProvider.getProperty("some.setting");

      if (!newSettingValue.equals(previousSettingValue)) {
        System.out.println("new value: some.setting = " + newSettingValue);
        previousSettingValue = newSettingValue;
      }

      Thread.sleep(1000);
    }
  }
}

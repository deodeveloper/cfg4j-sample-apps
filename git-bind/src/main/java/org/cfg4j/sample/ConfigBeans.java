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
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.git.GitConfigurationSourceBuilder;
import org.cfg4j.source.refresh.strategy.PeriodicalRefreshStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigBeans {

  @Value("${configRepoPath:https://github.com/cfg4j/cfg4j-git-sample-config.git}")
  private String configRepoPath;

  @Bean
  public ConfigurationProvider configurationProvider() {
    ConfigurationSource source = new GitConfigurationSourceBuilder()
        .withRepositoryURI(configRepoPath)
        .build();

    return new ConfigurationProviderBuilder()
        .withConfigurationSource(source)
        .withRefreshStrategy(new PeriodicalRefreshStrategy(3000))
        .build();
  }

}

# [cfg4j](http://cfg4j.org) sample app (uses **git** as configuration store)
App demonstrating how to access configuration stored in a Git repository using cfg4j library. Uses simple method access.

## Usage
```
> cd cfg4j-sample-apps/
> ./gradlew build
> java -jar git-simple/build/libs/git-simple-1.0.0-SNAPSHOT.jar
```

You can:
* use any git repository: **-DconfigRepoPath=\<repositoryUrl\>** parameter. Both local and remote repos are supported.
* use non-default branch: **-DconfigBranch=\<branchName\>**

```
> java -DconfigRepoPath=/tmp/myRepo.git -DconfigBranch=myBranch -jar git-simple/build/libs/git-simple-1.0.0-SNAPSHOT.jar
```

## Code snippets

### Configuration beans - see ConfigBeans.java
```java
@Value("${configRepoPath:https://github.com/cfg4j/cfg4j-git-sample-config.git}")
private String configRepoPath; // Run with -DconfigRepoPath=<repositoryUrl> parameter to override

@Value("${configBranch:master}")
private String branch; // Run with -DconfigBranch=<branchName> parameter to override

@Bean
public ConfigurationProvider configurationProvider() {
  // Use Git repository as configuration store
  ConfigurationSource source = new GitConfigurationSourceBuilder()
      .withRepositoryURI(configRepoPath)
      .build();

  // Select branch to use (use new DefaultEnvironment()) for master
  Environment environment = new ImmutableEnvironment(branch);

  // Reload configuration every 5 seconds
  ReloadStrategy reloadStrategy = new PeriodicalReloadStrategy(5, TimeUnit.SECONDS);

  // Create provider
  return new ConfigurationProviderBuilder()
      .withConfigurationSource(source)
      .withEnvironment(environment)
      .withReloadStrategy(reloadStrategy)
      .build();
}
```

### Obtain configuration - see MainController.java
```java
String newSettingValue = configurationProvider.getProperty("some.setting", String.class);
```
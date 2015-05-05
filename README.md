This project shows a problem when loading spring integration sftp and reactor.

Reactor uses StringUtils from an older version of reactor and the spring support for reactor such as EnableReactor is available in reactor 2.x versions

```bash
./gradlew bootRun
```

```
Exception in thread "main" org.springframework.beans.factory.BeanDefinitionStoreException: Unexpected exception parsing XML document from class path resource [sftp.xml]; nested exception is java.lang.NoClassDefFoundError: reactor/util/StringUtils
```

[Fixed provided](http://stackoverflow.com/questions/29934964/spring-integration-sftp-with-reactor)

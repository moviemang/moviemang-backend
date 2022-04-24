# moviemang

| 항목         | version | 비고  |
|-------------|---------|-----|
| jdk         | 1.8     |     |
| spring boot | 2.6.6   |     |

## 서비스별 포트 
| 서비스         | port | 비고  |
|-------------|---------|-----|
| main-api-server   | 9091 |     |
| member-api-server | 9092 |     |
| playlist-api-server | 9093 |     |

## 프로젝트 구조
```
moviemang
├── HELP.md
├── README.md
├── apis
│   ├── main-api-server
│   │   ├── build.gradle
│   │   └── src
│   │       ├── main
│   │       │   ├── java
│   │       │   │   └── com.moviemang.main
│   │       │   │               ├── MainApiServerApplication.java
│   │       │   │               ├── config
│   │       │   │               │   └── WebConfig.java
│   │       │   │               ├── controller
│   │       │   │               │   └── MainController.java
│   │       │   │               └── domain
│   │       │   │                   ├── Dummy.java
│   │       │   │                   └── enums
│   │       │   └── resources
│   │       │       └── application.properties
│   │       └── test
│   │           └── java
│   │               └── com.moviemang.main
│   │                           └── MainApiServerApplicationTests.java
│   └── member-api-server
│       ├── build.gradle
│       └── src
│           ├── main
│           │   ├── java
│           │   │   └── com.moviemang.member
│           │   │               └── MemberApiServerApplication.java
│           │   └── resources
│           │       └── application.properties
│           └── test
│               └── java
│                   └── com.moviemang.member
│                               └── MemberApiServerApplicationTests.java
├── batch
│   ├── build.gradle
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com.moviemang.batch
│       │   │               └── BatchApplication.java
│       │   └── resources
│       │       └── application.properties
│       └── test
│           └── java
│               └── com.moviemang.batch
│                           └── BatchApplicationTests.java
├── build.gradle
├── core
│   ├── core-utils
│   │   ├── build.gradle
│   │   └── src
│   │       ├── main
│   │       │   ├── java
│   │       │   │   └── com.moviemang.coreutils
│   │       │   │               ├── CoreUtilsApplication.java
│   │       │   │               ├── RequestAttributeUtil.java
│   │       │   │               ├── exception
│   │       │   │               ├── model
│   │       │   │               │   ├── enums
│   │       │   │               │   └── vo
│   │       │   │               │       └── HttpClientRequest.java
│   │       │   │               └── utils
│   │       │   │                   └── httpclient
│   │       │   │                       └── HttpClient.java
│   │       │   └── resources
│   │       │       └── application.properties
│   │       └── test
│   │           └── java
│   │               └── com.moviemang.coreutils
│   │                           └── CoreUtilsApplicationTests.java
│   └── datastore
│       ├── build.gradle
│       └── src
│           ├── main
│           │   ├── java
│           │   │   └── com.moviemang.datastore
│           │   │               ├── DatastoreApplication.java
│           │   │               ├── config
│           │   │               │   └── MariaDataSourceConfig.java
│           │   │               └── repository
│           │   │                   └── MemberRepository.java
│           │   └── resources
│           │       └── application.properties
│           └── test
│               └── java
│                   └── com.moviemang.datastore
│                               └── DatastoreApplicationTests.java
├── settings.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── com.moviemang
    │   │           └── MoviemangApplication.java
    │   └── resources
    │       ├── application.properties
    │       ├── static
    │       └── templates
    └── test
        └── java
            └── com.moviemang
                    └── MoviemangApplicationTests.java
```



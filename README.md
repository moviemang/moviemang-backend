# moviemang

| 항목         | version | 비고  |
|-------------|---------|-----|
| jdk         | 1.8     |     |
| spring boot | 2.6.6   |     |

## 서비스별 포트 
| 서비스         | port | 비고  |
|-------------|------|-----|
| member-api-server | 8080 |     |
| playlist-api-server | 8081 |     |

## 프로젝트 구조
```
moviemang
├── HELP.md
├── README.md
├── apis
│   └── member-api-server
│       ├── build.gradle
│       └── src
│           ├── main
│           │   ├── java
│           │   │   └── com.moviemang.member
│           │   │               ├── controller
│           │   │               │   └── MemberControler.java
│           │   │               ├── service
│           │   │               │   └── MemberService.java
│           │   │               │   └── MemberServiceImpl.java
│           │   │               ├── domain
│           │   │               │   └── Member.java
│           │   │               │   └── Join.java
│           │   │               └── MemberApiServerApplication.java
│           │   └── resources
│           │       └── application.properties
│           └── test
│               └── java
│                   └── com.moviemang.member
│                               └── MemberApiServerApplicationTests.java
│   └── playlist-api-server
│       ├── build.gradle
│       └── src
│           ├── main
│           │   ├── java
│           │   │   └── com.moviemang.playlist
│           │   │               ├── controller
│           │   │               │   └── PlaylistControler.java
│           │   │               ├── service
│           │   │               │   └── PlaylistService.java
│           │   │               │   └── PlaylistServiceImpl.java
│           │   │               ├── domain
│           │   │               │   └── Playlist.java
│           │   │               │   └── LikePlaylist.java
│           │   │               └── PlaylistApiServerApplication.java
│           │   └── resources
│           │       └── application.yml
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
│           │   │               ├── entity
│           │   │               │   └── maria
│           │   │               │   │   ├── Member.java
│           │   │               │   │   ├── LoginLog.java
│           │   │               │   │   └── Tag.java
│           │   │               │   └── mongo
│           │   │               │   │   ├── Playlist.java
│           │   │               │   │   ├── Bookmark.java
│           │   │               │   │   ├── Like.java
│           │   │               │   │   └── Review.java
│           │   │               └── repository
│           │   │               │   └── maria
│           │   │               │   │   ├── MemberRepository.java
│           │   │               │   │   └── TagRepository.java
│           │   │               │   └── mongo
│           │   │               │   │   ├── PlaylistRepository.java
│           │   │               │   │   ├── BookmarkRepository.java
│           │   │               │   │   ├── ReviewRepository.java
│           │   │               │   │   └── LikeRepository.java
│           │   └── resources
│           │       ├── local
│           │       │   └── jdbc.properties
│           │       ├── dev
│           │       │   └── jdbc.properties
│           │       ├── prod
│           │       │   └── jdbc.properties
│           │       └── application.yml
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



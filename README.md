# 🐝 Community - 소셜 피드 기반 커뮤니티 서비스

Spring Boot 기반으로 구현된 SNS 피드 기능 중심의 커뮤니티 백엔드입니다.  
이 프로젝트는 사용자 관리, 팔로우, 게시물 작성 및 상호작용(댓글, 좋아요 등)을 중심으로 구성되어 있으며, CI/CD 까지 고려하였습니다.

---

## 🧰 기술 스택 및 개발 환경

| 분류 | 기술 |
|---|---|
| 프레임워크 | Spring Boot 3.4.4 |
| 테스트 | JUnit5, RestAssured |
| 인프라 | Docker 27.5.1, Jenkins 2.471 |
| 데이터베이스 | MySQL, H2 (Test) |
| 기타 | JWT, Redis (예정), OAuth2 (예정) |


---

## ✨ 주요 기능

### ✅ 사용자 (User)
- 회원가입 (이메일 인증 기반)
- OAuth2 로그인 (예정)
- 유저 정보 조회 및 프로필
- 팔로우/언팔로우

### ✅ 피드 (Feed)
- 게시글 작성, 수정, 삭제
- 게시글 좋아요, 댓글 기능
- 피드 조회 (팔로잉 기반, 시간순 정렬)

### ✅ 댓글 (Comment)
- 댓글 작성, 수정, 삭제
- 댓글 좋아요
- 댓글 리스트 조회

---

## ⚙️ 비기능 요구사항

### 🔐 보안
- 비밀번호 암호화 저장 (BCrypt)
- JWT 인증 기반 세션 관리
- 이메일 인증을 통한 사용자 신뢰성 확보
- 민감 정보 외부 노출 방지 (`application-template.yml` 분리)

### 🚀 성능 및 확장성
- Redis 캐싱 도입 예정 (피드 조회 개선)
- Kafka 또는 RabbitMQ 기반 이벤트 처리 예정
- 지연 로딩, 페이징 처리, N+1 쿼리 방지 적용 예정

### 🔧 유지보수성
- 도메인 모듈 분리 (`user`, `auth`, `feed`, `comment`, `common`)
- 요청/응답 DTO 구분
- 공통 응답 포맷(Response<T>) 사용

---

## 🧪 테스트 전략

### ✅ 단위 테스트 (JUnit5)
- Fake Repository & Service 활용
- 핵심 비즈니스 로직 검증
- `Given-When-Then` 패턴 적용

### ✅ 인수 테스트 (RestAssured)
- `AcceptanceTestTemplate` 기반 공통 셋업
- `DatabaseCleanUp`, `DataLoader` 유틸 적용
- 실제 API 요청 흐름 테스트

---

## 🔁 CI/CD 구성

### ✅ Jenkins Freestyle 기반

```plaintext
1. Github main 브랜치로 merge
2. Jenkins Webhook 트리거
3. 로컬 Jenkins → EC2 원격 SSH 접속
4. Artifact 전달 및 Spring Boot 애플리케이션 재시작
```
---

## Notion 명세서

- **Community 명세**: 

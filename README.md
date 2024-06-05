# dogWorld
키우는 반려동물이 오늘도 안전할수있도록 동물병원위치, 지식공유커뮤니티 , 산책을 위한 날씨정보를 제공하는 웹사이트입니다.

## 📃프로젝트 정보

#### 🗓️프로젝트기간
2023.12.08 ~ 지속적 업데이트중

#### 📚프로젝트 개발환경 및 사용기술
- `java version 17`
- `Spring Boot 3.1.5`
- `MySQL`
- `IntelliJ IDEA Ultimate`
- `Spring Security`
- `JWT token`
- `Spring Data JPA`
- `selenium 4.15.0`
- `Cloudinary 1.28.0`

## 🧑🏻‍💻팀 정보
프론트 서버: https://github.com/cktjdgus45/dogWorld <br/>
백엔드 서버: https://github.com/strSong0/dogWorld

## 📊프로젝트 구성

#### 시스템 구성도
![image](https://github.com/strSong0/dogWorld/assets/68099038/2389fb57-c3e0-43f1-8c65-3bceb7784c90)

#### DB ERD
![image](https://github.com/strSong0/dogWorld/assets/68099038/7f3f78fe-151c-44a5-b1d2-c3433bac9fc7)

## 🔑주요 기능

### 🧑👩회원기능
 기능 | HTTP METHOD | URL
 --- | ----------- | ---
 회원가입 | POST | /auth/signup
 로그인 | POST | /auth/login
 회원 정보 조회 | GET | /auth/me
 회원 정보 수정 | PUT | /auth/me
 회원 탈퇴 | DELETE | /auth/delete

#### 회원가입
회원가입 폼의 검증절차를 통과해야 회원가입 완료.<br/> 회원가입 완료 시 바로 jwt 토큰을 발급하며 로그인처리.<br/>

##### 검증내용
1. 공백 시 검증 실패
2. 아이디는 소문자로만 시작 가능합니다 | 사용 가능 문자 : 영소문자, 숫자 | 글자수 제한 : 최소 6자에서 15자
3. 비밀번호는 영문 숫자 특수문자를 모두 포함해야 합니다 | 사용 가능 문자 : 영대소문자, 숫자, 특수문자 (`~₩!@#$%^&*) | 글자수 제한 : 최소 8자에서 20자
4. ^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$ |  지정된 이메일 형식과 불일치 시 검증 실패

#### 로그인
사용자의 ID, PW 를 검증 후 jwt 토큰 발급

#### 회원정보 조회
username, name, 프로필사진url 반환

#### 회원 정보 수정
사용자의 email, name, 프로필사진을 등록 할 수 있다.<br/>
이때 등록한 프로필사진의 url은 Cloudinary 클라우드 서버에 저장이된다.

* * *

### 🐕24시 동물병원 조회
 기능 | HTTP METHOD | URL
 --- | ----------- | ---
 24시 동물병원 정보 조회 | GET | /api/crawl/{cityName}

#### 24시 동물병원 정보 조회
cityName과 셀레니움을 통하여 해당 cityName 에 맞는 24시간 동물병원 정보와 좌표를 크롤링하여 DB에 저장한다.<br/> 
이때 이미 동물병원의 데이터가 DB에 저장이 되어 있다면 크롤링을 하지 않고 DB의 정보를 가져다 쓴다.<br/> 

#### 크롤링 과정

![image](https://github.com/strSong0/dogWorld/assets/68099038/d226e249-ab81-42b9-9196-48b47dc4836f)

1. 도시명을 입력받는다.
2. '도시명' + '24시 동물병원' 의 형태로 네이버 지도에 검색
3. 크롤링을 시작하여 주소, 병원이름, 전화번호, 좌표 등을 객체에 담는다.
4. 주소의 좌표값은 네이버 OpenApi 인 Geocoding API를 사용하여 x좌표, y좌표를 가져온다.
5. 이후 데이터베이스에 저장된다.


* * *

### 📋게시글, 댓글 기능
 기능 | HTTP METHOD | URL
 --- | ----------- | ---
 아이디로 게시글 조회 | GET | /posts?username={username}
 전체 게시글 조회 | GET | /posts
 게시글 아이디로 조회 | GET | /posts/{postId}
 게시글 작성 | POST | /posts
 게시글 수정 | PUT | /posts/{postId}
 게시글 삭제 | DELETE | /posts/{postId}
 댓글 작성 | POST | /posts/{postId}/comments

#### 게시글 조회
1. 아이디로 게시글 조회
   - username에 알맞는 게시글을 찾아 postDto 리스트 형태로 반환
2. 전체 게시글 조회
   - 데이터베이스에 등록된 모든 게시글을 postDto 리스트 형태로 반환
3. 게시글 아이디로 조회
   - postId 값을 받아 PostDto 형태로 반환

#### 게시글 작성
게시글 작성시 text, image 는 필수 요구사항이다.

#### 게시글 수정
작성된 게시글을 수정할 수 있다.

#### 게시글 삭제
작성된 게시글을 삭제 할 수 있다.

#### 댓글 작성
작성된 댓글은 CommentDto 형태로 반환한다.

* * *


# 이거왜오름 배치 프로젝트

## 프로젝트 개요
이 프로젝트는 급등락 주식의 변동성 원인을 분석하여 레포트를 자동으로 생성하는 배치 프로젝트입니다. 스프링 배치를 사용하여 저장된 급등락 주식 데이터를 읽고, RAG를 활용하여 AI가 레포트를 작성한 후 저장하는 시스템입니다.

## 주요 기능
1. 급등락 주식 데이터 조회
2. RAG를 활용한 AI 레포트 생성
3. 레포트 캐싱 및 관리
4. 레포트 데이터베이스 저장

## 기술 스택
- Spring Batch
- Spring AI
- Kotlin
- MySQL
- ChromaDB (벡터 데이터베이스)

## 환경 설정
1. `.env.example` 파일을 `.env`로 복사
2. 다음 환경 변수들을 설정:
   - `OPENROUTER_KEY`: OpenRouter API 키
   - `OPENAI_GPT_KEY`: OpenAI API 키
   - `CHROMA_URL`: ChromaDB URL
   - `CHROMA_PORT`: ChromaDB 포트
   - `MYSQL_URL`: MySQL 데이터베이스 URL
   - `MYSQL_ID`: MySQL 사용자 ID
   - `MYSQL_KEY`: MySQL 비밀번호
   - `KOREA_INV_KEY`: 한국투자증권 API 키
   - `KOREA_INV_SECRET`: 한국투자증권 API 시크릿

## 배치 프로세스
1. `hotStockReportStagingInitStep`: 스테이징 테이블 초기화
2. `highRiserStep`: 
   - 급등락 주식 데이터 읽기
   - AI를 통한 레포트 생성
   - 레포트 캐시 및 스테이징 테이블에 저장
3. `swapDataStep`: 스테이징 테이블의 데이터를 실제 테이블로 이동

## 주요 컴포넌트
- `HighRiserReader`: 급등락 주식 데이터를 읽는 Reader
- `CreateReportProcessor`: AI를 사용하여 레포트를 생성하는 Processor
- `ReportCacheWriter`: 생성된 레포트를 캐시하는 Writer
- `HotStockReportStagingWriter`: 레포트를 스테이징 테이블에 저장하는 Writer
- `SwapDataTasklet`: 스테이징 테이블의 데이터를 실제 테이블로 이동하는 Tasklet

## 실행 방법
1. 환경 변수 설정
2. 프로젝트 빌드
3. 배치 실행

## 참고사항
- 레포트는 캐시되어 일정 시간 동안 재사용됩니다.
- AI 생성 실패 시 최대 3번까지 재시도합니다.
- 지정된 테이블에서 급등락 주식의 이름을 가져옵니다.



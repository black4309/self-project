DROP SEQUENCE LIB_MEMBER_SEQ;
DROP SEQUENCE LIB_BOARD_SEQ;
DROP SEQUENCE LIB_COMMENT_SEQ; 
DROP SEQUENCE LIB_CONTENTS_SEQ; 

DROP TABLE LIB_ADMIN;
DROP TABLE LIB_COMMENT;
DROP TABLE LIB_CONTENTS;
DROP TABLE LIB_BOARD;
DROP TABLE LIB_MEMBER;

------------------------ CREATE TABLE ---------------------------

-- 회원
CREATE TABLE LIB_MEMBER(
    MEMBER_UID    INTEGER       NOT NULL -- 회원_UID
   ,JOIN_DAY       DATE         NULL     -- 가입일자
   ,ID          VARCHAR2(50)   NOT NULL     -- 아이디
   ,PASSWORD    VARCHAR2(100)  NOT NULL   -- 패스워드
   ,NAME        VARCHAR2(50)    NULL     -- 이름
   ,E_MAIL      VARCHAR2(100)    NULL      -- 이메일
     ,CONSTRAINT pk_LIB_MEMBER_MEMBER_UID PRIMARY KEY(MEMBER_UID)
);

-- 관리자
CREATE TABLE LIB_ADMIN 
(
	ID VARCHAR2(50), 
	PASSWORD VARCHAR2(100)
);

-- 게시판
CREATE TABLE LIB_BOARD (
   BOARD_UID    INTEGER      NOT NULL -- 게시판_UID
   ,SUBJECT      VARCHAR2(50) NULL     -- 주제
   ,DESCRIPTION  VARCHAR2(100) NULL     -- 설명
   ,REG_DATE  DATE         NULL     -- 등록날짜
,CONSTRAINT pk_LIB_BOARD_BOARD_UID PRIMARY KEY(BOARD_UID)
);

-- 게시글
CREATE TABLE LIB_CONTENTS (
   CONTENTS_UID INTEGER        NOT NULL, -- 게시글_UID
   BOARD_UID    INTEGER        NOT NULL, -- 게시판_UID
   MEMBER_UID   INTEGER        NULL,     -- 회원_UID
   TITLE    VARCHAR2(100)  NULL,     -- 제목
   CONTENTS VARCHAR2(3000) NULL,     -- 내용
   COUNTS       INTEGER        NULL,     -- 조회수
   REG_DATE DATE           NULL      -- 등록일자
   
   ,CONSTRAINT pk_LIB_CONTENTS_CONTENTS_UID PRIMARY KEY(CONTENTS_UID)
   ,CONSTRAINT fk_LIB_CONTENTS_MEMBER_UID FOREIGN KEY(MEMBER_UID)
    REFERENCES LIB_MEMBER(MEMBER_UID)
   ,CONSTRAINT fk_LIB_CONTENTS_BOARD_UID FOREIGN KEY(BOARD_UID)
    REFERENCES LIB_BOARD(BOARD_UID) ON DELETE CASCADE
);

-- 댓글
CREATE TABLE LIB_COMMENT (
   COMMENT_UID      INTEGER       NOT NULL -- 댓글_UID
   ,MEMBER_UID       INTEGER       NULL     -- 회원_UID
   ,CONTENTS_UID      INTEGER       NULL     -- 게시글_UID
   ,CONTENTS         VARCHAR2(4000) NULL     -- 내용
   ,REG_DATE DATE          NULL      -- 등록일자
   ,CONSTRAINT pk_LIB_COMMENT_COMMENT_UID PRIMARY KEY(COMMENT_UID)
   ,CONSTRAINT fk_LIB_COMMENT_MEMBER_UID FOREIGN KEY(MEMBER_UID)
    REFERENCES LIB_MEMBER(MEMBER_UID)
    ,CONSTRAINT fk_LIB_COMMENT_CONTENTS_UID FOREIGN KEY(CONTENTS_UID)
    REFERENCES LIB_CONTENTS(CONTENTS_UID) ON DELETE CASCADE
);

------------------------ CREATE SEQ ---------------------------

CREATE SEQUENCE LIB_MEMBER_SEQ START WITH 1 INCREMENT BY 1 MAXVALUE 100000;
CREATE SEQUENCE LIB_BOARD_SEQ START WITH 1 INCREMENT BY 1 MAXVALUE 100000;
CREATE SEQUENCE LIB_CONTENTS_SEQ START WITH 1 INCREMENT BY 1 MAXVALUE 100000;
CREATE SEQUENCE LIB_COMMENT_SEQ START WITH 1 INCREMENT BY 1 MAXVALUE 100000;

------------------------ INSERT DATA ---------------------------

INSERT INTO LIB_MEMBER(MEMBER_UID,JOIN_DAY,ID, NAME, PASSWORD,E_MAIL) VALUES 
(LIB_MEMBER_SEQ.NEXTVAL,'20170909','1', '쌍용', '1','bbb123@navar.com');

INSERT INTO LIB_ADMIN(ID,PASSWORD) VALUES ('admin', '123' );

COMMIT;
CREATE SCHEMA IF NOT EXISTS "book";

CREATE TYPE "book"."condition" AS ENUM (
  'perfect',
  'good',
  'bad',
  'useless',
  'disable'
);

CREATE TYPE "book"."cover" AS ENUM (
  'paper',
  'hardcover'
);

CREATE TYPE "book"."genre" AS ENUM (
  'adventure',
  'romance',
  'fantasy',
  'sci_fi',
  'history',
  'horror',
  'distopian',
  'biography',
  'self_help',
  'memory',
  'true_crime',
  'poetry',
  'graphic_novel'
);

CREATE TABLE "users" (
                         "id" char(26) PRIMARY KEY,
                         "name" varchar(256) NOT NULL,
                         "email" varchar(512) NOT NULL,
                         "created_at" timestamp DEFAULT now(),
                         "inactived_at" timestamp DEFAULT null,
                         "active" bool DEFAULT true
);

CREATE TABLE "books" (
                         "isbn" char(13) PRIMARY KEY,
                         "title" varchar(512) NOT NULL,
                         "release_year" int NOT NULL,
                         "summary" text,
                         "author" text NOT NULL,
                         "page_len" int,
                         "publisher" text,
                         "inactived_at" timestamp DEFAULT null,
                         "active" bool DEFAULT true
);

CREATE TABLE "genre" (
                         "book_id" char(13),
                         "genre" book.genre NOT NULL
);

CREATE TABLE "portfolio" (
                             "id" char(26) PRIMARY KEY,
                             "book_id" char(13) NOT NULL,
                             "condition" book.condition,
                             "cover" book.cover
);

CREATE TABLE "loan" (
                        "id" char(26) PRIMARY KEY,
                        "portfolio_id" char(26) NOT NULL,
                        "user_id" char(26) NOT NULL,
                        "start_at" timestamp NOT NULL DEFAULT now(),
                        "return_at" timestamp DEFAULT null,
                        "period" int NOT NULL DEFAULT 30,
                        "loan_condition" book.condition,
                        "return_condition" book.condition DEFAULT null
);

CREATE INDEX ON "genre" ("book_id");

ALTER TABLE "genre" ADD FOREIGN KEY ("book_id") REFERENCES "books" ("isbn");

ALTER TABLE "portfolio" ADD FOREIGN KEY ("book_id") REFERENCES "books" ("isbn");

ALTER TABLE "loan" ADD FOREIGN KEY ("portfolio_id") REFERENCES "portfolio" ("id");

ALTER TABLE "loan" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

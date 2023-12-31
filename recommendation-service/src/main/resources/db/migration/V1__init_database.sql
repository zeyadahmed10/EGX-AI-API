CREATE EXTENSION IF NOT EXISTS vector;
create sequence if not exists user_history_seq start with 1 increment by 50;

create table if not exists user_history
(
    id integer not null,
    email varchar(255),
    embedding vector(768),
    primary key (id)
);
create table if not exists news_embedding
(
    id integer not null,
    embedding vector(768),
    hits integer,
    primary key (id)
);
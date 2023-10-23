create sequence if not exists news_seq start with 1 increment by 50;
create table if not exists news(

 id integer not null,
 reuters_code varchar(255) not null,
 title varchar(500) not null,
 time timestamp(6) not null,
 primary key(id)
);
CREATE INDEX idx_news_title
ON news(title);
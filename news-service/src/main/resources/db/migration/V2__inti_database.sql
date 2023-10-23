create sequence if not exists news_seq start with 1 increment by 50;
create sequence if not exists equity_seq start with 1 increment by 50;
create table if not exists news(

 id integer not null,
 title varchar(500) not null,
 article text,
 time timestamp(6) not null,
 equity_id integer,
 primary key(id)
);
create table if not exists equity
(
    id integer not null,
    isn varchar(255),
    listing_date varchar(255),
    name varchar(255),
    reuters_code varchar(255),
    sector varchar(255),
    primary key (id)
);
alter table if exists news add constraint FK_EquityNews foreign key (equity_id) references equity;

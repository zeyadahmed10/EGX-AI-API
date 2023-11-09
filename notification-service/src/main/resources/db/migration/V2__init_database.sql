create sequence if not exists equity_seq start with 1 increment by 50;
create sequence if not exists subscribed_user_seq start with 1 increment by 50;
create table if not exists equity (id integer not null, isn varchar(255), listing_date varchar(255), name varchar(255), reuters_code varchar(255), sector varchar(255), primary key (id));
create table if not exists news_subscription (equity_id integer not null, user_id integer not null, primary key (equity_id, user_id));
create table if not exists stock_subscription (equity_id integer not null, user_id integer not null, primary key (equity_id, user_id));
create table if not exists subscribed_user (id integer not null, email varchar(255) not null unique, name varchar(255) not null, primary key (id));
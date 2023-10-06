create sequence if not exists equity_seq start with 1 increment by 50;
create sequence if not exists historical_stock_seq start with 1 increment by 50;
create sequence if not exists updated_stock_seq start with 1 increment by 50;
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
create table if not exists historical_stock
(
    id integer not null,
    curr_price float(53) not null,
    highest float(53) not null,
    lowest float(53) not null,
    open float(53) not null,
    percentage_of_change float(53) not null,
    prev_close float(53) not null,
    rate_of_change float(53) not null,
    value float(53) not null,
    volume float(53) not null,
    time timestamp(6) not null,
    equity_id integer,
    primary key (id)
);
create table if not exists updated_stock
(
    id integer not null,
    curr_price float(53) not null,
    highest float(53) not null,
    lowest float(53) not null,
    open float(53) not null,
    percentage_of_change float(53) not null,
    prev_close float(53) not null,
    rate_of_change float(53) not null,
    value float(53) not null,
    volume float(53) not null,
    time timestamp(6) not null,
    equity_id integer unique,
    primary key (id));
alter table if exists historical_stock add constraint FK_EquityHistoricalStock foreign key (equity_id) references equity;
alter table if exists updated_stock add constraint FK_EquityUpdatedStock foreign key (equity_id) references equity;
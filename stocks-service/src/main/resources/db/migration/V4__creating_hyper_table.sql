ALTER TABLE historical_stock DROP CONSTRAINT historical_stock_pkey;
SELECT create_hypertable('historical_stock', 'time', if_not_exists => TRUE);

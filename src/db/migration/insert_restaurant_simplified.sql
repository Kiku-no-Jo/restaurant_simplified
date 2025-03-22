CREATE TYPE table_number_enum AS ENUM ('TABLE_1', 'TABLE_2', 'TABLE_3');


CREATE TABLE dish_availability
(
    id SERIAL PRIMARY KEY,
    dish_name VARCHAR(100) UNIQUE NOT NULL,
    available_stock double precision
);

CREATE TABLE "order"
(
    id                    SERIAL PRIMARY KEY,
    table_number          table_number_enum NOT NULL,
    amount_paid double precision,
    amount_due double precision,
    customer_arrival_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE dish_order
(
    id       SERIAL PRIMARY KEY,
    dish_name     VARCHAR(100) NOT NULL,
    unit_price double precision NOT NULL,
    quantity_to_order double precision NOT NULL,
    id_order int          NOT NULL,
    CONSTRAINT fk_order FOREIGN KEY (id_order) REFERENCES "order" (id)
);
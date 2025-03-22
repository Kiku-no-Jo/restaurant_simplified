INSERT INTO dish_availability (dish_name, available_stock)
VALUES ('Spaghetti Carbonara', 50.0),
       ('Margherita Pizza', 30.0),
       ('Grilled Salmon', 20.0),
       ('Caesar Salad', 40.0),
       ('Tiramisu', 25.0),
       ('Beef Burger', 35.0);


INSERT INTO "order" (table_number, amount_paid, amount_due, customer_arrival_date)
VALUES ('TABLE_1', 250.0, 320.0, '2023-10-01 12:30:00'), -- Order 1: 240.0 + 80.0 = 320.0
       ('TABLE_2', 0.0, 290.0, '2023-10-01 13:15:00'),   -- Order 2: 150.0 + 140.0 = 290.0
       ('TABLE_3', 600.0, 200.0, '2023-10-01 14:00:00'), -- Order 3: 50.0 + 150.0 = 200.0
       ('TABLE_1', 0.0, 280.0, '2023-10-01 14:45:00'),   -- Order 4: 120.0 + 160.0 = 280.0
       ('TABLE_2', 500.0, 220.0, '2023-10-01 15:30:00'), -- Order 5: 150.0 + 70.0 = 220.0
       ('TABLE_3', 0.0, 150.0, '2023-10-01 16:15:00'),   -- Order 6: 100.0 + 50.0 = 150.0
       ('TABLE_1', 400.0, 440.0, '2023-10-01 17:00:00'), -- Order 7: 360.0 + 80.0 = 440.0
       ('TABLE_2', 0.0, 370.0, '2023-10-01 17:45:00'),   -- Order 8: 300.0 + 70.0 = 370.0
       ('TABLE_3', 150.0, 150.0, '2023-10-01 18:30:00'), -- Order 9: 50.0 + 100.0 = 150.0
       ('TABLE_1', 0.0, 440.0, '2023-10-01 19:15:00'); -- Order 10: 120.0 + 240.0 = 360.0 + 80.0 = 440.0


INSERT INTO dish_order (dish_name, unit_price, quantity_to_order, id_order)
VALUES ('Spaghetti Carbonara', 120.0, 2.0, 1),  -- 120.0 * 2 = 240.0
       ('Margherita Pizza', 80.0, 1.0, 1),      -- 80.0 * 1 = 80.0
       ('Grilled Salmon', 150.0, 1.0, 2),       -- 150.0 * 1 = 150.0
       ('Caesar Salad', 70.0, 2.0, 2),          -- 70.0 * 2 = 140.0
       ('Tiramisu', 50.0, 1.0, 3),              -- 50.0 * 1 = 50.0
       ('Beef Burger', 50.0, 3.0, 3),           -- 50.0 * 3 = 150.0
       ('Spaghetti Carbonara', 120.0, 1.0, 4),  -- 120.0 * 1 = 120.0
       ('Margherita Pizza', 80.0, 2.0, 4),      -- 80.0 * 2 = 160.0
       ('Grilled Salmon', 150.0, 1.0, 5),       -- 150.0 * 1 = 150.0
       ('Caesar Salad', 70.0, 1.0, 5),          -- 70.0 * 1 = 70.0
       ('Tiramisu', 50.0, 2.0, 6),              -- 50.0 * 2 = 100.0
       ('Beef Burger', 50.0, 1.0, 6),           -- 50.0 * 1 = 50.0
       ('Spaghetti Carbonara', 120.0, 3.0, 7),  -- 120.0 * 3 = 360.0
       ('Margherita Pizza', 80.0, 1.0, 7),      -- 80.0 * 1 = 80.0
       ('Grilled Salmon', 150.0, 2.0, 8),       -- 150.0 * 2 = 300.0
       ('Caesar Salad', 70.0, 1.0, 8),          -- 70.0 * 1 = 70.0
       ('Tiramisu', 50.0, 1.0, 9),              -- 50.0 * 1 = 50.0
       ('Beef Burger', 50.0, 2.0, 9),           -- 50.0 * 2 = 100.0
       ('Spaghetti Carbonara', 120.0, 1.0, 10), -- 120.0 * 1 = 120.0
       ('Margherita Pizza', 80.0, 3.0, 10); -- 80.0 * 3 = 240.0
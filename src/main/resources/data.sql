INSERT INTO app_user (name, password, email) VALUES
('John Doe', 'password123', 'john.doe@example.com'),
('Jane Smith', 'password456', 'jane.smith@example.com'),
('Alice Johnson', 'password789', 'alice.johnson@example.com'),
('Bob Brown', 'password012', 'bob.brown@example.com'),
('Charlie Miller', 'password234', 'charlie.miller@example.com'),
('Daniel Wilson', 'password567', 'daniel.wilson@example.com'),
('Emily Davis', 'password890', 'emily.davis@example.com'),
('Frank Harris', 'password345', 'frank.harris@example.com'),
('Grace Lee', 'password678', 'grace.lee@example.com'),
('Hannah Walker', 'password901', 'hannah.walker@example.com'),
('Ivy Clarke', 'password1234', 'ivy.clarke@example.com'),
('Jack Turner', 'password4567', 'jack.turner@example.com'),
('Kathy Baker', 'password8901', 'kathy.baker@example.com'),
('Liam Nelson', 'password2345', 'liam.nelson@example.com'),
('Mona Hill', 'password5678', 'mona.hill@example.com'),
('Nina Reed', 'password6789', 'nina.reed@example.com'),
('Oscar Parker', 'password7890', 'oscar.parker@example.com'),
('Paul Carter', 'password0123', 'paul.carter@example.com'),
('Quincy Rogers', 'password3456', 'quincy.rogers@example.com'),
('Rachel Adams', 'password6780', 'rachel.adams@example.com'),
('Isaac Newton', 'password321', 'isaac.newton@example.com'),   -- user_id = 21
('James Maxwell', 'password654', 'james.maxwell@example.com'), -- user_id = 22
('Marie Curie', 'password987', 'marie.curie@example.com'),     -- user_id = 23
('Nikola Tesla', 'password159', 'nikola.tesla@example.com'),   -- user_id = 24
('Albert Einstein', 'password753', 'albert.einstein@example.com'), -- user_id = 25
('Michael Faraday', 'password852', 'michael.faraday@example.com'), -- user_id = 26
('Galileo Galilei', 'password951', 'galileo.galilei@example.com'), -- user_id = 27
('Leonardo da Vinci', 'password159', 'leonardo.davinci@example.com'), -- user_id = 28
('Charles Darwin', 'password357', 'charles.darwin@example.com'),     -- user_id = 29
('Louis Pasteur', 'password963', 'louis.pasteur@example.com');       -- user_id = 30



INSERT INTO user_roles (user_id, roles) VALUES
(1, 'DRIVER'),   -- John Doe
(2, 'RIDER'),    -- Jane Smith
(3, 'ADMIN'),    -- Alice Johnson
(4, 'RIDER'),    -- Bob Brown
(5, 'DRIVER'),   -- Charlie Miller
(6, 'ADMIN'),    -- Daniel Wilson
(7, 'RIDER'),    -- Emily Davis
(8, 'DRIVER'),   -- Frank Harris
(9, 'ADMIN'),    -- Grace Lee
(10, 'RIDER'),   -- Hannah Walker
(11, 'DRIVER'),   -- Ivy Clarke
(12, 'RIDER'),    -- Jack Turner
(13, 'ADMIN'),    -- Kathy Baker
(14, 'DRIVER'),   -- Liam Nelson
(15, 'RIDER'),    -- Mona Hill
(16, 'ADMIN'),    -- Nina Reed
(17, 'DRIVER'),   -- Oscar Parker
(18, 'RIDER'),    -- Paul Carter
(19, 'ADMIN'),    -- Quincy Rogers
(20, 'RIDER'),   -- Rachel Adams
(21, 'DRIVER'), -- Isaac Newton
(22, 'DRIVER'), -- James Maxwell
(23, 'DRIVER'), -- Marie Curie
(24, 'DRIVER'), -- Nikola Tesla
(25, 'DRIVER'), -- Albert Einstein
(26, 'DRIVER'), -- Michael Faraday
(27, 'DRIVER'), -- Galileo Galilei
(28, 'DRIVER'), -- Leonardo da Vinci
(29, 'DRIVER'), -- Charles Darwin
(30, 'DRIVER'); -- Louis Pasteur



INSERT INTO driver (user_id, rating, available, current_location) VALUES
(1, 4.5, TRUE, ST_GeomFromText('POINT(73.911621 18.567839)', 4326)), -- John Doe
(5, 4.7, TRUE, ST_GeomFromText('POINT(73.917754 18.566536)', 4326)), -- Charlie Miller
(8, 4.9, TRUE, ST_GeomFromText('POINT(73.913927 18.564013)', 4326)), -- Frank Harris
(11, 4.4, TRUE, ST_GeomFromText('POINT(73.912345 18.564789)', 4326)), -- Ivy Clarke
(14, 4.1, TRUE, ST_GeomFromText('POINT(73.914567 18.565123)', 4326)), -- Liam Nelson
(17, 4.8, TRUE, ST_GeomFromText('POINT(73.910123 18.563456)', 4326)), -- Oscar Parker
(21, 4.6, TRUE, ST_GeomFromText('POINT(73.911000 18.566000)', 4326)),  -- Driver near Viman Nagar
(22, 4.2, TRUE, ST_GeomFromText('POINT(73.913000 18.568000)', 4326)),  -- Driver near Viman Nagar
(23, 4.9, FALSE, ST_GeomFromText('POINT(73.914000 18.569000)', 4326)), -- Driver near Viman Nagar
(24, 4.3, TRUE, ST_GeomFromText('POINT(73.915000 18.570000)', 4326)),  -- Driver near Viman Nagar
(25, 4.1, TRUE, ST_GeomFromText('POINT(73.916000 18.571000)', 4326)),  -- Driver near Viman Nagar
(26, 4.8, TRUE, ST_GeomFromText('POINT(73.917000 18.572000)', 4326)),  -- Driver near Viman Nagar
(27, 4.7, FALSE, ST_GeomFromText('POINT(73.918000 18.573000)', 4326)), -- Driver near Viman Nagar
(28, 4.5, TRUE, ST_GeomFromText('POINT(73.919000 18.574000)', 4326)),  -- Driver near Viman Nagar
(29, 4.4, TRUE, ST_GeomFromText('POINT(73.920000 18.575000)', 4326)),  -- Driver near Viman Nagar
(30, 4.0, FALSE, ST_GeomFromText('POINT(73.921000 18.576000)', 4326)); -- Driver near Viman Nagar


INSERT INTO rider (user_id, rating) VALUES
(2, 4.8), -- Jane Smith
(4, 4.2), -- Bob Brown
(7, 4.6), -- Emily Davis
(10, 4.3),
(12, 4.7),  -- Jack Turner
(15, 4.5),  -- Mona Hill
(18, 4.9),  -- Paul Carter
(20, 4.6);  -- Rachel Adams


INSERT INTO Wallet (balance, user_id) VALUES
(200,1),
(200,2)

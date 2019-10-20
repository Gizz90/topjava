DELETE
FROM user_roles;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2019-10-15 10:00', 'UserBreakfast', 500),
       (100000, '2019-10-15 13:00', 'UserLunch', 1100),
       (100000, '2019-10-15 20:00', 'UserDinner', 500),
       (100001, '2019-10-16 10:00', 'AdminBreakfast', 300),
       (100001, '2019-10-16 13:00', 'AdminLunch', 1200),
       (100001, '2019-10-16 20:00', 'AdminDinner', 400);
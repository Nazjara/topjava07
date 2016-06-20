DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (description,user_id,calories,date_time)
VALUES ('Breakfast', 100000, '250','2016-06-20 10:00');

INSERT INTO meals (description,user_id,calories,date_time)
VALUES ('Dinner', 100000, '450','2016-06-20 14:00');

INSERT INTO meals (description,user_id,calories,date_time)
VALUES ('Supper', 100001, '200','2016-06-20 19:00');

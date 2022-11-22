--
-- Users data
--
INSERT INTO users (id, username, pseudonym, password) VALUES (100, 'elon', 'el', '$2a$10$YJh9Ic1.kQhpU7zqm0tlKeCq6Dcyou59EEdB57AnZV.j86GFmrikS');
INSERT INTO users (id, username, pseudonym, password) VALUES (101, 'bill', 'bl', '$2a$10$Cl32kStExMoJm11N9KU9Z.mLCAidnuSUW3Yren6DoIGxJ6O5lKz3u');
INSERT INTO users (id, username, pseudonym, password) VALUES (102, 'tim', 'jc', '$2a$10$wdwgkdXGFth4pJzPsWRkTu6f5SUJbwxcRDPMsPAjcJa/3dGIVJIn2');

--
-- Products data
--
INSERT INTO products (id, title, description, product_image, price, publish, owner_id) VALUES (1, 'title1', 'description1', 'https://www.marketplace-kata.com/img-1', 100, 'PUBLISH', 100);

INSERT INTO products (id, title, description, product_image, price, publish, owner_id) VALUES (2, 'title2', 'description2', 'https://www.marketplace-kata.com/img-2', 150, 'PUBLISH', 100);

INSERT INTO products (id, title, description, product_image, price, publish, owner_id) VALUES (4, 'title3', 'description3', 'https://www.marketplace-kata.com/img-3', 70, 'PUBLISH', 101);

INSERT INTO products (id, title, description, product_image, price, publish, owner_id) VALUES (5, 'title4', 'description4', 'https://www.marketplace-kata.com/img-4', 90, 'PUBLISH', 101);

INSERT INTO products (id, title, description, product_image, price, publish, owner_id) VALUES (6, 'title5', 'description5', 'https://www.marketplace-kata.com/img-5', 66, 'PUBLISH', 102);

INSERT INTO products (id, title, description, product_image, price, publish, owner_id) VALUES (7, 'title6', 'description6', 'https://www.marketplace-kata.com/img-6', 880, 'PUBLISH', 102);

INSERT INTO products (id, title, description, product_image, price, publish, owner_id) VALUES (8, 'title7', 'description7', 'https://www.marketplace-kata.com/img-7', 1000,'PUBLISH', 102);

INSERT INTO account (username, password, admin)
VALUES
	('testi','testisana',FALSE);

INSERT INTO bonus (name)
VALUES
	('ei bonusta'),
	('s-bonus'),
	('plussa');


INSERT INTO category (description)
VALUES
	('elintarvike');

INSERT INTO subcategory (description)
VALUES	
	('hedelmat'),
	('virvoitusjuoma'),
	('maitotuote'),
	('lihatuote');

INSERT INTO categorylink (category_id, subcategory_id)
	SELECT category_id, subcategory_id FROM category, subcategory WHERE category.description = 'elintarvike' AND subcategory.description = 'hedelmat';

INSERT INTO categorylink (category_id, subcategory_id)
	SELECT category_id, subcategory_id FROM category, subcategory WHERE category.description = 'elintarvike' AND subcategory.description = 'virvoitusjuoma';

INSERT INTO categorylink (category_id, subcategory_id)
	SELECT category_id, subcategory_id FROM category, subcategory WHERE category.description = 'elintarvike' AND subcategory.description = 'maitotuote';

INSERT INTO categorylink (category_id, subcategory_id)
	SELECT category_id, subcategory_id FROM category, subcategory WHERE category.description = 'elintarvike' AND subcategory.description = 'lihatuote';
	

INSERT INTO payment (name)
VALUES
	('Kateinen'),
	('Visa');

INSERT INTO shop (name, city, address, bonus_id)
	SELECT '','','', bonus_id FROM bonus WHERE name = 'ei bonusta';

INSERT INTO shop (name, city, address, bonus_id)
	SELECT 'Prisma Jumbo','Vantaa','Vantaanportti', bonus_id FROM bonus WHERE name = 's-bonus';

INSERT INTO shop (name, city, address, bonus_id)
	SELECT 'Citymarket Jumbo','Vantaa','Vantaanportti', bonus_id FROM bonus WHERE name = 'plussa';

INSERT INTO product (name, brand, weight)
	SELECT 'Maito','Valio',1;

INSERT INTO product (name, brand, weight)
	SELECT 'Omena','Royal Gala',1;

INSERT INTO product (name, brand, weight)
	SELECT 'Limu','CocaCola',1;
INSERT INTO account (username, password, admin)
VALUES
	('testi','testisana',FALSE);

INSERT INTO bonus (name)
VALUES
	('s-bonus'),
	('plussa');


INSERT INTO category (description)
VALUES
	('elintarvike');

INSERT INTO subcategory (description, category_id)
	SELECT 'hedelmat', category_id FROM category WHERE description = 'elintarvike';

INSERT INTO subcategory (description, category_id)
	SELECT 'virvoitusjuoma', category_id FROM category WHERE description = 'elintarvike';

INSERT INTO subcategory (description, category_id)
	SELECT 'maitotuote', category_id FROM category WHERE description = 'elintarvike';

INSERT INTO subcategory (description, category_id)
	SELECT 'lihatuote', category_id FROM category WHERE description = 'elintarvike';
	

INSERT INTO payment (name)
VALUES
	('Visa'),
	('Kateinen');

INSERT INTO shop (name, city, address, bonus_id)
	SELECT 'Prisma Jumbo','Vantaa','Vantaanportti', bonus_id FROM bonus WHERE name = 's-bonus';

INSERT INTO shop (name, city, address, bonus_id)
	SELECT 'Citymarket Jumbo','Vantaa','Vantaanportti', bonus_id FROM bonus WHERE name = 'plussa';

INSERT INTO product (name, brand, weight, subcategory_id)
	SELECT 'Maito','Valio',1, subcategory_id FROM subcategory WHERE description = 'maitotuote';

INSERT INTO product (name, brand, weight, subcategory_id)
	SELECT 'Omena','Royal Gala',1, subcategory_id FROM subcategory WHERE description = 'hedelmat';

INSERT INTO product (name, brand, weight, subcategory_id)
	SELECT 'Limu','CocaCola',1, subcategory_id FROM subcategory WHERE description = 'virvoitusjuoma';
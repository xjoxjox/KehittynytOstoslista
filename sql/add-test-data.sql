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
	SELECT 'Kevytmaito','Valio',1.5;

INSERT INTO product (name, brand, weight)
	SELECT 'Peruna','pesty',1;

INSERT INTO product (name, brand, weight)
	SELECT 'Alaskanseiti','Pirkka',0.4;

INSERT INTO product (name, brand, weight)
	SELECT 'Keittojuures','Pirkka',0.3;

INSERT INTO product (name, brand, weight)
	SELECT 'Meetvursti venalainen','Kotivara',0.17;

INSERT INTO product (name, brand, weight)
	SELECT 'Sulatejuusto Emmental','Kuusamon',0.2;

INSERT INTO product (name, brand, weight)
	SELECT 'Kerma ruokakerma','Arla',0.25;

INSERT INTO product (name, brand, weight)
	SELECT 'Jauheliha sika-nauta','Snellman',0.4;

INSERT INTO product (name, brand, weight)
	SELECT 'Tomaattimurska','Pirkka',0.5;

INSERT INTO product (name, brand, weight)
	SELECT 'Juustoraaste pizza','Porlammi',0.3;

INSERT INTO product (name, brand, weight)
	SELECT 'Jogurtti turkkilainen','Pirkka',0.2;

INSERT INTO product (name, brand, weight)
	SELECT 'Leikkele saunapalvi ohut','Atria',0.3;

INSERT INTO product (name, brand, weight)
	SELECT 'Banaani','Pirkka',1;

INSERT INTO product (name, brand, weight)
	SELECT 'Nektariini',' ',1;

INSERT INTO product (name, brand, weight)
	SELECT 'Paaryna','Guyot',1;

INSERT INTO product (name, brand, weight)
	SELECT 'Valkosipuli kori','Solo',0.2;

INSERT INTO product (name, brand, weight)
	SELECT 'Basilika','Jarvikyla',0.2;

INSERT INTO product (name, brand, weight)
	SELECT 'Porkkana irto',' ',1;

INSERT INTO product (name, brand, weight)
	SELECT 'Punakaali',' ',1;

INSERT INTO product (name, brand, weight)
	SELECT 'Kahvi Presidentti','Paulig',1;

INSERT INTO product (name, brand, weight)
	SELECT 'Tomaattimurska yrtti','Mutti',0.4;

INSERT INTO product (name, brand, weight)
	SELECT 'Sulatejuustoviipale','Rainbow',0.2;

INSERT INTO product (name, brand, weight)
	SELECT 'Margariini','Rainbow',0.4;

INSERT INTO product (name, brand, weight)
	SELECT 'Hunajameloni',' ',1;

INSERT INTO product (name, brand, weight)
	SELECT 'Banaani','Rainbow',1;

INSERT INTO product (name, brand, weight)
	SELECT 'Peruna varhais pussi',' ',1;

INSERT INTO product (name, brand, weight)
	SELECT 'Kevytmaito','Arla',1.5;

INSERT INTO product (name, brand, weight)
	SELECT 'Jogurtti paaryna','Kotimaista',0.4;

INSERT INTO product (name, brand, weight)
	SELECT 'Jogurtti kreikkalainen vad-granomena','Valio',0.2;

INSERT INTO product (name, brand, weight)
	SELECT 'Juusto punaleima Emmental','Arla',0.2;

INSERT INTO product (name, brand, weight)
	SELECT 'Rahka man/ban/vad','Kotimaista',0.2;

INSERT INTO product (name, brand, weight)
	SELECT 'Vesimeloni',' ',1;

INSERT INTO product (name, brand, weight)
	SELECT 'Maitorahka','Valio',0.2;

INSERT INTO product (name, brand, weight)
	SELECT 'Limu greippi','Kotimaista',1.5;

INSERT INTO product (name, brand, weight)
	SELECT 'Juustosnacks','Taffel',0.35;

INSERT INTO product (name, brand, weight)
	SELECT 'Tuorejuusto','Valio',0.2;

INSERT INTO product (name, brand, weight)
	SELECT 'Nacho','Kotimaista',0.2;

INSERT INTO product (name, brand, weight)
	SELECT 'Paprika pun',' ',1;

INSERT INTO product (name, brand, weight)
	SELECT 'Juustoraaste Emmental','Valio',0.3;

INSERT INTO product (name, brand, weight)
	SELECT 'Chili',' ',1;

INSERT INTO product (name, brand, weight)
	SELECT 'Mascarpone',' ',0.2;

INSERT INTO product (name, brand, weight)
	SELECT 'Jauheliha nauta kevyt','Atria',0.4;

INSERT INTO product (name, brand, weight)
	SELECT 'Jogurtti turkkilainen','Kotimaista',0.5;

INSERT INTO product (name, brand, weight)
	SELECT 'Juustokumina','Meirami',0.1;

INSERT INTO product (name, brand, weight)
	SELECT 'Korianteri jauhettu','Meirami',0.1;

INSERT INTO product (name, brand, weight)
	SELECT 'Sokeri hieno',' ',1;

INSERT INTO product (name, brand, weight)
	SELECT 'Creme fraiche','Valio',0.2;

INSERT INTO product (name, brand, weight)
	SELECT 'Sipuli pussi',' ',1;

INSERT INTO product (name, brand, weight)
	SELECT 'Limu max 4pack','Pepsi',8;

INSERT INTO product (name, brand, weight)
	SELECT 'Puuro kaurahiutale luomu','Kotimaista',1.5;

INSERT INTO product (name, brand, weight)
	SELECT 'Sulatejuusto','Kotimaista',0.4;

INSERT INTO product (name, brand, weight)
	SELECT 'Viili Viilis','Valio',0.2;

INSERT INTO product (name, brand, weight)
	SELECT 'Verigreippi',' ',1;

INSERT INTO product (name, brand, weight)
	SELECT 'Suolakurkku',' ',1;

INSERT INTO product (name, brand, weight)
	SELECT 'Paaryna','Conference',1;

INSERT INTO product (name, brand, weight)
	SELECT 'Salaatti tammenlehti',' ',0.3;

INSERT INTO product (name, brand, weight)
	SELECT 'Peruna jauhoinen',' ',1;

INSERT INTO product (name, brand, weight)
	SELECT 'Porkkana pussi',' ',1;

INSERT INTO product (name, brand, weight)
	SELECT 'Limu zero','CocaCola',3.0;

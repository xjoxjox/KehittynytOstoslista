CREATE TABLE account(
	account_id serial PRIMARY KEY,
	username VARCHAR (50) UNIQUE NOT NULL,
	password VARCHAR (50) NOT NULL
);

CREATE TABLE category(
	category_id serial PRIMARY KEY,
	description VARCHAR (50) NOT NULL
);

CREATE TABLE subcategory(
	subcategory_id serial PRIMARY KEY,
	description VARCHAR (50) NOT NULL,
	category_id INTEGER NOT NULL,
	CONSTRAINT category_id_fkey FOREIGN KEY (category_id) REFERENCES category(category_id) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE bonus(
	bonus_id serial PRIMARY KEY,
	name VARCHAR (50) NOT NULL
);

CREATE TABLE payment(
	payment_id serial PRIMARY KEY,
	name VARCHAR (50) NOT NULL
);

CREATE TABLE shop(
	shop_id serial PRIMARY KEY,
	name VARCHAR (50) NOT NULL,
	city VARCHAR (50) NOT NULL,
	address VARCHAR (50) NOT NULL,
	bonus_id INTEGER NOT NULL,
	CONSTRAINT bonus_id_fkey FOREIGN KEY (bonus_id) REFERENCES bonus(bonus_id) ON UPDATE CASCADE ON DELETE RESTRICT		
);

CREATE TABLE product(
	product_id serial PRIMARY KEY,
	name VARCHAR (50) NOT NULL,
	brand VARCHAR (50) NOT NULL,
	weight DECIMAL (10,3) NOT NULL,
	subcategory_id INTEGER NOT NULL,
	CONSTRAINT subcategory_id_fkey FOREIGN KEY (subcategory_id) REFERENCES subcategory(subcategory_id) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE shoppinglistsaved(
	shoppinglist_id serial PRIMARY KEY,
	name VARCHAR (50) NOT NULL,
	sum DECIMAL (10,2) NOT NULL,
	weight DECIMAL (10,3) NOT NULL,
	time_created TIMESTAMP NOT NULL DEFAULT now(),
	shop_id INTEGER NOT NULL,
	account_id INTEGER NOT NULL,
	CONSTRAINT shop_id_fkey FOREIGN KEY (shop_id) REFERENCES shop(shop_id) ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT account_id_fkey FOREIGN KEY (account_id) REFERENCES account(account_id) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE shoppinglistchecked(
	shoppinglist_id serial PRIMARY KEY,
	name VARCHAR (50) NOT NULL,
	sum DECIMAL (10,2) NOT NULL,
	weight DECIMAL (10,3) NOT NULL,
	time_checked TIMESTAMP NOT NULL DEFAULT now(),
	shop_id INTEGER NOT NULL,
	account_id INTEGER NOT NULL,
	payment_id INTEGER NOT NULL,
	bonus_id INTEGER NOT NULL,
	CONSTRAINT shop_id_fkey FOREIGN KEY (shop_id) REFERENCES shop(shop_id) ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT account_id_fkey FOREIGN KEY (account_id) REFERENCES account(account_id) ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT payment_id_fkey FOREIGN KEY (payment_id) REFERENCES payment(payment_id) ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT bonus_id_fkey FOREIGN KEY (bonus_id) REFERENCES bonus(bonus_id) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE productprice(
	price DECIMAL (10,3) NOT NULL,
	location INTEGER NOT NULL,
	productprice_date TIMESTAMP NOT NULL DEFAULT now(),
	product_id INTEGER NOT NULL,
	shop_id INTEGER NOT NULL,
	CONSTRAINT product_id_fkey FOREIGN KEY (product_id) REFERENCES product(product_id) ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT shop_id_fkey FOREIGN KEY (shop_id) REFERENCES shop(shop_id) ON UPDATE CASCADE ON DELETE RESTRICT,
	PRIMARY KEY (product_id, shop_id)
);

CREATE TABLE productlist(
	product_id INTEGER NOT NULL,
	shoppinglist_id INTEGER NOT NULL,
	CONSTRAINT product_id_fkey FOREIGN KEY (product_id) REFERENCES product(product_id) ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT shoppinglist_id_fkey FOREIGN KEY (shoppinglist_id) REFERENCES shoppinglistsaved(shoppinglist_id) ON UPDATE CASCADE ON DELETE RESTRICT,
	PRIMARY KEY (product_id, shoppinglist_id)
);


CREATE TABLE bonusshopped(
	bonus_id INTEGER NOT NULL,
	shoppinglist_id INTEGER NOT NULL,
	CONSTRAINT bonus_id_fkey FOREIGN KEY (bonus_id) REFERENCES bonus(bonus_id) ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT shoppinglist_id_fkey FOREIGN KEY (shoppinglist_id) REFERENCES shoppinglistchecked(shoppinglist_id) ON UPDATE CASCADE ON DELETE RESTRICT,
	PRIMARY KEY (bonus_id, shoppinglist_id)
);


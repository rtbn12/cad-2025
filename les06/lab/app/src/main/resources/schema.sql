Create table CATEGORIES(
   category_id int primary key,
   name VARCHAR(600) not null,
   description VARCHAR(1000)
);

Create table PRODUCTS(
   product_id int primary key,
   name VARCHAR(600) not null,
   description VARCHAR(1000),
   category_id int,
   price DECIMAL(10, 2) not null check(price >=0),
   stock_quantity int not null check(stock_quantity >=0),
   image_url VARCHAR(600),
   created_at TIMESTAMP,
   updated_at TIMESTAMP,
   FOREIGN KEY (category_id) REFERENCES CATEGORIES(category_id) ON DELETE SET NULL
)
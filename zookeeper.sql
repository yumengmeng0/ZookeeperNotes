-- 商品表
CREATE DATABASE IF NOT EXISTS `zkproduct` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE zkproduct;

SHOW CREATE DATABASE zkproduct; 

CREATE TABLE product (
  id INT PRIMARY KEY AUTO_INCREMENT,-- 商品编号
  product_name VARCHAR(20) NOT NULL,   -- 商品名称
  stock INT NOT NULL,  -- 库存
  VERSION INT NOT NULL -- 版本
);

INSERT INTO product (product_name, stock, VERSION) VALUES  ('锦鲤-清空购物车-大奖', 5, 0);

-- 订单表
CREATE TABLE `order` (
  id VARCHAR(100) PRIMARY KEY, -- 订单编号  
  pid INT NOT NULL, -- 商品编号 
  userid INT NOT NULL -- 用户编号
);

INSERT INTO `order`(id, pid, userid) VALUES ('f45baf97-6766-4627-bf93-e24d1bc69a3f', 1, 101);

INSERT INTO ORDER(id, pid, userid) VALUES ('f45baf97-6766-4627-bf93-e24d1bc69a3f', 1, 101);
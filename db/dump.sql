CREATE TABLE IF NOT EXISTS `produtos` (`id` INT AUTO_INCREMENT, `nome` VARCHAR(50) NOT NULL, `descricao` VARCHAR(255) NOT NULL, `preco` DOUBLE NOT NULL, PRIMARY KEY(id));
INSERT INTO produtos (id, nome, descricao, preco) VALUES
(1, 'Smartphone Xiaomi Mi 9T 128GB', 'Smartphone Xiaomi Mi 9T 6GB/128GB RAM Dual SIM Versão Global', 2899),
(2, 'iPhone X', 'Iphone X Apple 64GB', 4999),
(3, 'Macbook Pro 2019', 'Macbook Pro 2019 I9 32gb 1tb', 24999),
(4, 'Google Chromecast 3', 'Google Chromecast 3', 279),
(5, 'Projetor Epson', 'Projetor Epson Powerlite S41+, Branco,V11H842024 3300 Lúmens', 2599);

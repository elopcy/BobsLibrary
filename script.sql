CREATE SCHEMA bd_bobs_library;
USE bd_bobs_library;
CREATE USER 'bobslibrary'@'localhost' IDENTIFIED BY 'bobslibrary';
GRANT ALL PRIVILEGES ON bd_bobs_library.* TO 'bobslibrary'@'localhost';
FLUSH PRIVILEGES;
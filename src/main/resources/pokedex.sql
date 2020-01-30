-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.18 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para pokedex
CREATE DATABASE IF NOT EXISTS `pokedex` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `pokedex`;

-- Volcando estructura para tabla pokedex.habilidad
CREATE TABLE IF NOT EXISTS `habilidad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla pokedex.habilidad: ~0 rows (aproximadamente)
DELETE FROM `habilidad`;
/*!40000 ALTER TABLE `habilidad` DISABLE KEYS */;
INSERT INTO `habilidad` (`id`, `nombre`) VALUES
	(6, 'ascua'),
	(5, 'electricidad estatica'),
	(2, 'foco interno'),
	(7, 'hedor'),
	(1, 'impasible'),
	(3, 'justiciero'),
	(4, 'pararayos');
/*!40000 ALTER TABLE `habilidad` ENABLE KEYS */;

-- Volcando estructura para tabla pokedex.pokemon
CREATE TABLE IF NOT EXISTS `pokemon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla pokedex.pokemon: ~0 rows (aproximadamente)
DELETE FROM `pokemon`;
/*!40000 ALTER TABLE `pokemon` DISABLE KEYS */;
INSERT INTO `pokemon` (`id`, `nombre`) VALUES
	(4, 'bulbasaur'),
	(3, 'charmander'),
	(1, 'lucario'),
	(2, 'pikachu');
/*!40000 ALTER TABLE `pokemon` ENABLE KEYS */;

-- Volcando estructura para tabla pokedex.pokemon_has_habilidades
CREATE TABLE IF NOT EXISTS `pokemon_has_habilidades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_pokemon` int(11) NOT NULL,
  `id_habilidad` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pokemon` (`id_pokemon`),
  KEY `fk_habilidad` (`id_habilidad`),
  CONSTRAINT `fk_habilidad` FOREIGN KEY (`id_habilidad`) REFERENCES `habilidad` (`id`),
  CONSTRAINT `fk_pokemon` FOREIGN KEY (`id_pokemon`) REFERENCES `pokemon` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla pokedex.pokemon_has_habilidades: ~0 rows (aproximadamente)
DELETE FROM `pokemon_has_habilidades`;
/*!40000 ALTER TABLE `pokemon_has_habilidades` DISABLE KEYS */;
INSERT INTO `pokemon_has_habilidades` (`id`, `id_pokemon`, `id_habilidad`) VALUES
	(1, 1, 1),
	(2, 1, 2),
	(3, 1, 3),
	(4, 2, 4),
	(5, 2, 5),
	(6, 3, 6),
	(7, 3, 3),
	(8, 3, 1),
	(9, 4, 7),
	(10, 4, 1);
/*!40000 ALTER TABLE `pokemon_has_habilidades` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

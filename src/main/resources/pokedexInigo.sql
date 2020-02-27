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


-- Volcando estructura de base de datos para pokedexinigo
CREATE DATABASE IF NOT EXISTS `pokedexinigo` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `pokedexinigo`;

-- Volcando estructura para tabla pokedexinigo.habilidad
CREATE TABLE IF NOT EXISTS `habilidad` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla pokedexinigo.habilidad: ~7 rows (aproximadamente)
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

-- Volcando estructura para tabla pokedexinigo.pokemon
CREATE TABLE IF NOT EXISTS `pokemon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `imagen` varchar(800) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla pokedexinigo.pokemon: ~11 rows (aproximadamente)
DELETE FROM `pokemon`;
/*!40000 ALTER TABLE `pokemon` DISABLE KEYS */;
INSERT INTO `pokemon` (`id`, `nombre`, `imagen`) VALUES
	(1, 'lucario', 'https://vignette.wikia.nocookie.net/es.pokemon/images/d/d0/Lucario.png'),
	(2, 'pikachu', 'https://i.pinimg.com/236x/76/47/9d/76479dd91dc55c2768ddccfc30a4fbf5--pikachu-halloween-costume-diy-halloween-costumes.jpg'),
	(4, 'bulbasaur', 'https://assets.pokemon.com/assets/cms2/img/pokedex/full/001.png'),
	(8, 'pidgeot', 'https://vignette.wikia.nocookie.net/es.pokemon/images/a/a9/Pidgeot.png'),
	(9, 'charizard', 'https://assets.pokemon.com/assets/cms2/img/pokedex/full/006.png'),
	(10, 'squirtle', 'https://assets.pokemon.com/assets/cms2/img/pokedex/full/007.png'),
	(17, 'charmeleon', 'https://www.geek.com/wp-content/uploads/2019/04/pantherchameleon1-625x352.jpg'),
	(53, 'riolu', 'https://assets.pokemon.com/assets/cms2/img/pokedex/full/447.png'),
	(58, 'Duralodon', 'https://cdn.bulbagarden.net/upload/3/38/884Duraludon.png'),
	(76, 'Metagross', 'https://www.alfabetajuega.com/abj_public_files/multimedia/imagenes/201410/83556.alfabetajuega-recopilatiorio-megaevoluciones-pokemon-36-071014.jpg'),
	(78, 'pichu', 'https://pm1.narvii.com/6589/96e02d76c7eeebbbd9b64aa01bbeaf7504158bf6_hq.jpg'),
	(80, 'geodude', 'https://www.alfabetajuega.com/wp-content/uploads/2019/05/geodude-iwate.jpg');
/*!40000 ALTER TABLE `pokemon` ENABLE KEYS */;

-- Volcando estructura para tabla pokedexinigo.pokemon_has_habilidades
CREATE TABLE IF NOT EXISTS `pokemon_has_habilidades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_pokemon` int(11) NOT NULL,
  `id_habilidad` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_pokemon` (`id_pokemon`,`id_habilidad`),
  KEY `fk_habilidad` (`id_habilidad`),
  KEY `fk_pokemon` (`id_pokemon`),
  CONSTRAINT `fk_habilidad` FOREIGN KEY (`id_habilidad`) REFERENCES `habilidad` (`id`),
  CONSTRAINT `fk_pokemon` FOREIGN KEY (`id_pokemon`) REFERENCES `pokemon` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla pokedexinigo.pokemon_has_habilidades: ~15 rows (aproximadamente)
DELETE FROM `pokemon_has_habilidades`;
/*!40000 ALTER TABLE `pokemon_has_habilidades` DISABLE KEYS */;
INSERT INTO `pokemon_has_habilidades` (`id`, `id_pokemon`, `id_habilidad`) VALUES
	(97, 2, 4),
	(98, 2, 5),
	(95, 4, 1),
	(96, 4, 7),
	(84, 17, 1),
	(82, 17, 2),
	(85, 17, 4),
	(83, 17, 6),
	(34, 58, 3),
	(33, 58, 4),
	(72, 76, 4),
	(80, 78, 1),
	(81, 78, 2),
	(78, 78, 4),
	(79, 78, 5),
	(93, 80, 2),
	(94, 80, 3),
	(91, 80, 5),
	(92, 80, 6);
/*!40000 ALTER TABLE `pokemon_has_habilidades` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

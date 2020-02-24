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

-- Volcando datos para la tabla pokedex.habilidad: ~7 rows (aproximadamente)
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
  `imagen` varchar(800) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla pokedex.pokemon: ~16 rows (aproximadamente)
DELETE FROM `pokemon`;
/*!40000 ALTER TABLE `pokemon` DISABLE KEYS */;
INSERT INTO `pokemon` (`id`, `nombre`, `imagen`) VALUES
	(1, 'lucario', 'https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/11ea81db-083a-4971-ba53-5e684fe179cd/dab4inf-8802e701-8bbf-4e6f-b7d2-98a42215dcad.png/v1/fill/w_369,h_367,q_80,strp/nick_the_lucario_meme_downloadable_base_by_unownace_dab4inf-fullview.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9MzY3IiwicGF0aCI6IlwvZlwvMTFlYTgxZGItMDgzYS00OTcxLWJhNTMtNWU2ODRmZTE3OWNkXC9kYWI0aW5mLTg4MDJlNzAxLThiYmYtNGU2Zi1iN2QyLTk4YTQyMjE1ZGNhZC5wbmciLCJ3aWR0aCI6Ijw9MzY5In1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmltYWdlLm9wZXJhdGlvbnMiXX0.rk154x3eHfHRdTeZXXft82pwvnOv5V2JFfvOQPuwITo'),
	(2, 'pikachu', 'https://pics.me.me/pikachu-in-gulag-1930-colorized-insert-dead-pikachu-meme-here-42787528.png'),
	(3, 'charmander', 'https://assets.pokemon.com/assets/cms2/img/pokedex/full/004.png'),
	(4, 'bulbasaur', 'https://i3.kym-cdn.com/photos/images/newsfeed/000/006/464/1.Bulbasaur.png'),
	(8, 'pidgeot', 'https://cdn.memegenerator.es/imagenes/memes/full/4/98/4986840.jpg'),
	(9, 'charizard', 'https://pm1.narvii.com/6315/840550f925ad1444e2d38d1cd05638f9da3f3c23_hq.jpg'),
	(10, 'squirtle', 'https://pm1.narvii.com/6770/cabbd48cdabe20fa2cfcd8cdd141baac7ad1e891v2_hq.jpg'),
	(17, 'charmeleon', 'https://www.geek.com/wp-content/uploads/2019/04/pantherchameleon1-625x352.jpg'),
	(53, 'riolu', 'https://assets.pokemon.com/assets/cms2/img/pokedex/full/447.png'),
	(58, 'Duralodon', 'https://cdn.bulbagarden.net/upload/3/38/884Duraludon.png'),
	(76, 'Metagross', 'https://www.alfabetajuega.com/abj_public_files/multimedia/imagenes/201410/83556.alfabetajuega-recopilatiorio-megaevoluciones-pokemon-36-071014.jpg'),
	(78, 'pichu', 'https://pm1.narvii.com/6589/96e02d76c7eeebbbd9b64aa01bbeaf7504158bf6_hq.jpg');
/*!40000 ALTER TABLE `pokemon` ENABLE KEYS */;

-- Volcando estructura para tabla pokedex.pokemon_has_habilidades
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
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla pokedex.pokemon_has_habilidades: ~25 rows (aproximadamente)
DELETE FROM `pokemon_has_habilidades`;
/*!40000 ALTER TABLE `pokemon_has_habilidades` DISABLE KEYS */;
INSERT INTO `pokemon_has_habilidades` (`id`, `id_pokemon`, `id_habilidad`) VALUES
	(4, 2, 4),
	(5, 2, 5),
	(8, 3, 1),
	(7, 3, 3),
	(6, 3, 6),
	(10, 4, 1),
	(9, 4, 7),
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
	(79, 78, 5);
/*!40000 ALTER TABLE `pokemon_has_habilidades` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

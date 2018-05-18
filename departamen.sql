-- --------------------------------------------------------
-- Host:                         localhost
-- Versión del servidor:         PostgreSQL 10.0, compiled by Visual C++ build 1800, 64-bit
-- SO del servidor:              
-- HeidiSQL Versión:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES  */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Volcando datos para la tabla public.yng_department: 0 rows
/*!40000 ALTER TABLE "yng_department" DISABLE KEYS */;
INSERT INTO "yng_department" ("department_id", "name", "province_id") VALUES
	(1, E'Adolfo Alsina', 2);
/*!40000 ALTER TABLE "yng_department" ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

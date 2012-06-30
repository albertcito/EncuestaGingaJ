-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 14-06-2012 a las 19:24:55
-- Versión del servidor: 5.5.16
-- Versión de PHP: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `ginga`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `opciones`
--

CREATE TABLE IF NOT EXISTS `opciones` (
  `id_opcion` int(20) NOT NULL AUTO_INCREMENT,
  `id_votacion` int(20) NOT NULL,
  `opcion` varchar(1000) NOT NULL,
  PRIMARY KEY (`id_opcion`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Volcado de datos para la tabla `opciones`
--

INSERT INTO `opciones` (`id_opcion`, `id_votacion`, `opcion`) VALUES
(1, 1, 'Valdivia'),
(2, 1, 'Santiago'),
(3, 1, 'Valparaíso'),
(4, 1, 'Antofagasta');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `votacion`
--

CREATE TABLE IF NOT EXISTS `votacion` (
  `id_votacion` int(20) NOT NULL AUTO_INCREMENT,
  `pregunta` varchar(1000) NOT NULL,
  PRIMARY KEY (`id_votacion`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `votacion`
--

INSERT INTO `votacion` (`id_votacion`, `pregunta`) VALUES
(1, '¿Cuál es la Capital de Chile ?');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `voto`
--

CREATE TABLE IF NOT EXISTS `voto` (
  `id_voto` bigint(20) NOT NULL AUTO_INCREMENT,
  `mac_tv` int(20) NOT NULL,
  `id_votacion` int(20) NOT NULL,
  `id_opcion` int(20) NOT NULL,
  PRIMARY KEY (`id_voto`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=28 ;

--
-- Volcado de datos para la tabla `voto`
--

INSERT INTO `voto` (`id_voto`, `mac_tv`, `id_votacion`, `id_opcion`) VALUES
(1, 1, 1, 4),
(2, 1, 1, 1),
(3, 1, 1, 1),
(4, 1, 1, 2),
(5, 1, 1, 2),
(6, 1, 1, 1),
(7, 1, 1, 2),
(8, 1, 1, 1),
(9, 1, 1, 3),
(10, 1, 1, 2),
(11, 1, 1, 2),
(12, 1, 1, 3),
(13, 1, 1, 2),
(14, 1, 1, 2),
(15, 1, 1, 2),
(16, 1, 1, 2),
(17, 1, 1, 2),
(18, 1, 1, 2),
(19, 1, 1, 2),
(20, 1, 1, 2),
(21, 1, 1, 1),
(22, 1, 1, 3),
(23, 1, 1, 4),
(24, 1, 1, 2),
(25, 1, 1, 2),
(26, 1, 1, 2),
(27, 1, 1, 2);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

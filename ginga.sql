-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 02-07-2012 a las 05:35:56
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=20 ;

--
-- Volcado de datos para la tabla `opciones`
--

INSERT INTO `opciones` (`id_opcion`, `id_votacion`, `opcion`) VALUES
(1, 1, 'Valdivia'),
(2, 1, 'Santiago'),
(3, 1, 'Valparaíso'),
(4, 1, 'Antofagasta'),
(5, 2, 'Pedro de Valdivia'),
(6, 2, 'Juana de Arco'),
(7, 0, ''),
(8, 0, ''),
(9, 3, 'Rojo'),
(10, 3, 'Blanco'),
(11, 3, 'Amarillo'),
(12, 4, 'Colo Colo'),
(13, 4, 'Universidad de Chile'),
(14, 5, 'Madrid'),
(15, 5, 'Berlin'),
(16, 5, 'Paris'),
(17, 5, 'Toronto'),
(18, 6, 'Hombre'),
(19, 6, 'Mujer');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `votacion`
--

CREATE TABLE IF NOT EXISTS `votacion` (
  `id_votacion` int(20) NOT NULL AUTO_INCREMENT,
  `pregunta` varchar(1000) NOT NULL,
  PRIMARY KEY (`id_votacion`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Volcado de datos para la tabla `votacion`
--

INSERT INTO `votacion` (`id_votacion`, `pregunta`) VALUES
(1, '¿Cuál es la Capital de Chile ?'),
(2, '¿Quién Descubrió América?'),
(3, '¿De qué color es el caballo blanco de Napoleon?'),
(4, '¿Quién Gana el Super Clasico?'),
(5, '¿Cual es la Capital de Francia?'),
(6, '¿Cual es tu sexo?');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `voto`
--

CREATE TABLE IF NOT EXISTS `voto` (
  `id_voto` bigint(20) NOT NULL AUTO_INCREMENT,
  `mac_tv` varchar(100) NOT NULL,
  `id_votacion` int(20) NOT NULL,
  `id_opcion` int(20) NOT NULL,
  PRIMARY KEY (`id_voto`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=127 ;

--
-- Volcado de datos para la tabla `voto`
--

INSERT INTO `voto` (`id_voto`, `mac_tv`, `id_votacion`, `id_opcion`) VALUES
(1, '1', 1, 4),
(2, '1', 1, 1),
(3, '1', 1, 1),
(4, '1', 1, 2),
(5, '1', 1, 2),
(6, '1', 1, 1),
(7, '1', 1, 2),
(8, '1', 1, 1),
(9, '1', 1, 3),
(10, '1', 1, 2),
(11, '1', 1, 2),
(12, '1', 1, 3),
(13, '1', 1, 2),
(14, '1', 1, 2),
(15, '1', 1, 2),
(16, '1', 1, 2),
(17, '1', 1, 2),
(18, '1', 1, 2),
(19, '1', 1, 2),
(20, '1', 1, 2),
(21, '1', 1, 1),
(22, '1', 1, 3),
(23, '1', 1, 4),
(24, '1', 1, 2),
(25, '1', 1, 2),
(26, '1', 1, 2),
(27, '1', 1, 2),
(28, '1', 1, 1),
(29, '1', 1, 1),
(30, '1', 1, 2),
(31, '1', 1, 1),
(32, '1', 1, 4),
(33, '1', 1, 3),
(34, '1', 1, 3),
(35, '1', 1, 3),
(36, '1', 1, 3),
(37, '1', 1, 2),
(38, '1', 1, 1),
(39, '1', 1, 1),
(40, '1', 1, 1),
(41, '1', 1, 1),
(42, '1', 1, 2),
(43, '1', 1, 3),
(44, '1', 1, 2),
(45, '1', 1, 2),
(46, '1', 2, 6),
(47, '1', 2, 6),
(48, '1', 2, 6),
(49, '1', 2, 5),
(50, '1', 2, 5),
(51, '1', 2, 5),
(52, '1', 2, 5),
(53, '1', 2, 6),
(54, '1', 2, 5),
(55, '1', 2, 5),
(56, '1', 3, 10),
(57, '1', 3, 10),
(58, '1', 3, 11),
(59, '1', 3, 9),
(60, '1', 3, 9),
(61, '1', 3, 9),
(62, '1', 3, 9),
(63, '1', 3, 9),
(64, '1', 3, 9),
(65, '1', 3, 9),
(66, '1', 3, 11),
(67, '1', 3, 11),
(68, '1', 3, 11),
(69, '1', 3, 10),
(70, '1', 3, 11),
(71, '1', 3, 10),
(72, '1', 3, 10),
(73, '1', 3, 10),
(74, '1', 3, 11),
(75, '1', 3, 10),
(76, '1', 3, 10),
(77, '1', 3, 11),
(78, '1', 3, 9),
(79, '1', 4, 12),
(80, '1', 4, 13),
(81, '1', 4, 12),
(82, '1', 4, 13),
(83, '1', 4, 13),
(84, '1', 4, 12),
(85, '1', 4, 12),
(86, '1', 4, 13),
(87, '1', 4, 13),
(88, '1', 4, 13),
(89, '1', 4, 13),
(90, '1', 4, 13),
(91, '1', 4, 13),
(92, '1', 4, 12),
(93, '1', 4, 12),
(94, '1', 4, 13),
(95, '1', 5, 16),
(96, '1', 5, 14),
(97, '1', 5, 15),
(98, '1', 5, 17),
(99, '1', 5, 16),
(100, '1', 5, 17),
(101, '1', 5, 16),
(102, '1', 5, 16),
(103, '1', 5, 15),
(104, '1', 5, 15),
(105, '1', 5, 16),
(106, '1', 5, 17),
(107, '1', 5, 15),
(108, '1', 5, 16),
(109, '1', 5, 14),
(110, '1', 5, 16),
(111, '1', 5, 14),
(112, '1', 5, 16),
(113, '1', 5, 15),
(114, '1', 5, 17),
(115, '1', 5, 16),
(116, '1', 5, 15),
(117, '1', 5, 16),
(118, '1', 5, 17),
(119, '1', 5, 17),
(120, '1', 5, 16),
(121, '1', 5, 16),
(122, '1', 5, 15),
(123, '1', 5, 16),
(124, '1', 5, 17),
(125, '08-00-27-00-04-3E', 5, 15),
(126, '08-00-27-00-04-3E', 6, 18);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

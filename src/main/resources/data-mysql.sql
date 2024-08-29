-- Fichier: data-mysql.sql

-- Insertion dans la table localisation
INSERT INTO `localisation` (`created_date`, `last_modified_date`, `batiment`, `lieu`, `salle`) VALUES
('2024-08-29 15:00:00', NULL, 'Bâtiment A', 'Campus Nord', 'Salle 101'),
('2024-08-29 15:01:00', NULL, 'Bâtiment B', 'Campus Sud', 'Salle 201'),
('2024-08-29 15:02:00', NULL, 'Bâtiment C', 'Campus Est', 'Salle 301'),
('2024-08-29 15:03:00', NULL, 'Bâtiment D', 'Campus Ouest', 'Salle 401'),
('2024-08-29 15:04:00', NULL, 'Bâtiment E', 'Campus Central', 'Salle 501');

-- Insertion dans la table materiel
INSERT INTO `materiel` (`id_localisation`, `quantite`, `created_date`, `last_modified_date`, `description`, `marque`, `reference`) VALUES
(1, 5, '2024-08-29 15:10:00', NULL, 'Ordinateur portable', 'Dell', 'XPS 15'),
(2, 10, '2024-08-29 15:11:00', NULL, 'Projecteur', 'Epson', 'EB-U05'),
(3, 15, '2024-08-29 15:12:00', NULL, 'Tableau blanc interactif', 'Smart', 'SB680'),
(4, 20, '2024-08-29 15:13:00', NULL, 'Chaise de bureau', 'Herman Miller', 'Aeron'),
(5, 8, '2024-08-29 15:14:00', NULL, 'Imprimante 3D', 'Prusa', 'i3 MK3S+');

-- Insertion dans la table pannes
INSERT INTO `pannes` (`id_materiel`, `created_date`, `last_modified_date`, `description`, `image_url`) VALUES
(1, '2024-08-29 15:20:00', NULL, 'Écran cassé', 'ecran_casse.jpg'),
(2, '2024-08-29 15:21:00', NULL, 'Lampe grillée', 'lampe_grillee.jpg'),
(3, '2024-08-29 15:22:00', NULL, 'Problème de calibration', 'calibration.jpg'),
(4, '2024-08-29 15:23:00', NULL, 'Roulettes bloquées', 'roulettes.jpg'),
(5, '2024-08-29 15:24:00', NULL, 'Buse obstruée', 'buse_obstruee.jpg');

-- Insertion dans la table role (en plus des rôles existants)
INSERT INTO `role` (`created_date`, `last_modified_date`, `role_description`, `role_name`) VALUES
('2024-08-29 15:30:00', NULL, 'Rôle pour les enseignants', 'ROLE_TEACHER'),
('2024-08-29 15:31:00', NULL, 'Rôle pour les étudiants', 'ROLE_STUDENT'),
('2024-08-29 15:32:00', NULL, 'Rôle pour le personnel technique', 'ROLE_TECHNICIAN');

-- Insertion dans la table utilisateur (en plus des utilisateurs existants)
INSERT INTO `utilisateur` (`active`, `is_admin`, `created_date`, `last_modified_date`, `adresse`, `email`, `first_name`, `last_name`, `password`, `telephone`) VALUES
(b'1', b'0', '2024-08-29 15:40:00', NULL, '123 Rue de la Paix', 'jean.dupont@email.com', 'Jean', 'Dupont', '$2a$10$abc123', '0123456789'),
(b'1', b'0', '2024-08-29 15:41:00', NULL, '456 Avenue des Champs', 'marie.martin@email.com', 'Marie', 'Martin', '$2a$10$def456', '9876543210'),
(b'1', b'0', '2024-08-29 15:42:00', NULL, '789 Boulevard Haussmann', 'pierre.durand@email.com', 'Pierre', 'Durand', '$2a$10$ghi789', '1122334455');

-- Insertion dans la table role_utilisateur
INSERT INTO `role_utilisateur` (`id_role`, `id_utilisateur`) VALUES
(3, 3), (4, 4), (5, 5);

-- Insertion dans la table reservation
INSERT INTO `reservation` (`date_debut`, `date_fin`, `date_retour`, `id_materiel`, `id_utilisateur`, `quantite`, `validate`, `created_date`, `last_modified_date`, `motif_pret`) VALUES
('2024-09-01', '2024-09-15', NULL, 1, 3, 1, b'1', '2024-08-29 16:00:00', NULL, 'Projet de recherche'),
('2024-09-02', '2024-09-16', NULL, 2, 4, 1, b'1', '2024-08-29 16:01:00', NULL, 'Présentation en classe'),
('2024-09-03', '2024-09-17', NULL, 3, 5, 1, b'0', '2024-08-29 16:02:00', NULL, 'Formation'),
('2024-09-04', '2024-09-18', NULL, 4, 3, 5, b'1', '2024-08-29 16:03:00', NULL, 'Aménagement bureau'),
('2024-09-05', '2024-09-19', NULL, 5, 4, 1, b'0', '2024-08-29 16:04:00', NULL, 'Projet étudiant');

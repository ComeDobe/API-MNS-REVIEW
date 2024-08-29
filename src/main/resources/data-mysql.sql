-- Fichier: data-mysql.sql

-- Insertion dans la table localisation
INSERT INTO `localisation` (`created_date`, `last_modified_date`, `batiment`, `lieu`, `salle`) VALUES
('2024-08-27 14:00:00', NULL, 'Bâtiment A', 'Campus Nord', 'Salle 101'),
('2024-08-27 14:01:00', NULL, 'Bâtiment B', 'Campus Sud', 'Salle 201'),
('2024-08-27 14:02:00', NULL, 'Bâtiment C', 'Campus Est', 'Salle 301'),
('2024-08-27 14:03:00', NULL, 'Bâtiment D', 'Campus Ouest', 'Salle 401'),
('2024-08-27 14:04:00', NULL, 'Bâtiment E', 'Campus Central', 'Salle 501'),
('2024-08-27 14:05:00', NULL, 'Bâtiment F', 'Campus Nord', 'Salle 601'),
('2024-08-27 14:06:00', NULL, 'Bâtiment G', 'Campus Sud', 'Salle 701'),
('2024-08-27 14:07:00', NULL, 'Bâtiment H', 'Campus Est', 'Salle 801'),
('2024-08-27 14:08:00', NULL, 'Bâtiment I', 'Campus Ouest', 'Salle 901'),
('2024-08-27 14:09:00', NULL, 'Bâtiment J', 'Campus Central', 'Salle 1001');

-- Insertion dans la table materiel
INSERT INTO `materiel` (`id_localisation`, `quantite`, `created_date`, `last_modified_date`, `description`, `marque`, `reference`) VALUES
(1, 5, '2024-08-27 15:00:00', NULL, 'Ordinateur portable', 'Dell', 'XPS 15'),
(2, 10, '2024-08-27 15:01:00', NULL, 'Projecteur', 'Epson', 'EB-U05'),
(3, 15, '2024-08-27 15:02:00', NULL, 'Tableau blanc interactif', 'Smart', 'SB680'),
(4, 20, '2024-08-27 15:03:00', NULL, 'Chaise de bureau', 'Herman Miller', 'Aeron'),
(5, 8, '2024-08-27 15:04:00', NULL, 'Imprimante 3D', 'Prusa', 'i3 MK3S+'),
(6, 12, '2024-08-27 15:05:00', NULL, 'Microscope', 'Olympus', 'CX23'),
(7, 6, '2024-08-27 15:06:00', NULL, 'Scanner', 'Fujitsu', 'ScanSnap iX1600'),
(8, 25, '2024-08-27 15:07:00', NULL, 'Casque VR', 'Oculus', 'Quest 2'),
(9, 30, '2024-08-27 15:08:00', NULL, 'Tablette graphique', 'Wacom', 'Intuos Pro'),
(10, 4, '2024-08-27 15:09:00', NULL, 'Serveur', 'HP', 'ProLiant DL380 Gen10');

-- Insertion dans la table pannes
INSERT INTO `pannes` (`id_materiel`, `created_date`, `last_modified_date`, `description`, `image_url`) VALUES
(1, '2024-08-27 16:00:00', NULL, 'Écran cassé', 'ecran_casse.jpg'),
(2, '2024-08-27 16:01:00', NULL, 'Lampe grillée', 'lampe_grillee.jpg'),
(3, '2024-08-27 16:02:00', NULL, 'Problème de calibration', 'calibration.jpg'),
(4, '2024-08-27 16:03:00', NULL, 'Roulettes bloquées', 'roulettes.jpg'),
(5, '2024-08-27 16:04:00', NULL, 'Buse obstruée', 'buse_obstruee.jpg'),
(6, '2024-08-27 16:05:00', NULL, 'Lentille rayée', 'lentille_rayee.jpg'),
(7, '2024-08-27 16:06:00', NULL, 'Problème d''alimentation', 'alimentation.jpg'),
(8, '2024-08-27 16:07:00', NULL, 'Sangle cassée', 'sangle_cassee.jpg'),
(9, '2024-08-27 16:08:00', NULL, 'Stylet ne répond pas', 'stylet.jpg'),
(10, '2024-08-27 16:09:00', NULL, 'Surchauffe', 'surchauffe.jpg');

-- Insertion dans la table role (ajout de 8 rôles supplémentaires)
INSERT INTO `role` (`created_date`, `last_modified_date`, `role_description`, `role_name`) VALUES
('2024-08-27 18:00:00', NULL, 'Rôle pour les enseignants', 'ROLE_TEACHER'),
('2024-08-27 18:01:00', NULL, 'Rôle pour les étudiants', 'ROLE_STUDENT'),
('2024-08-27 18:02:00', NULL, 'Rôle pour le personnel technique', 'ROLE_TECHNICIAN'),
('2024-08-27 18:03:00', NULL, 'Rôle pour les chercheurs', 'ROLE_RESEARCHER'),
('2024-08-27 18:04:00', NULL, 'Rôle pour les bibliothécaires', 'ROLE_LIBRARIAN'),
('2024-08-27 18:05:00', NULL, 'Rôle pour le personnel administratif', 'ROLE_STAFF'),
('2024-08-27 18:06:00', NULL, 'Rôle pour les invités', 'ROLE_GUEST'),
('2024-08-27 18:07:00', NULL, 'Rôle pour les modérateurs', 'ROLE_MODERATOR');

-- Insertion dans la table utilisateur (ajout de 8 utilisateurs supplémentaires)
INSERT INTO `utilisateur` (`active`, `is_admin`, `created_date`, `last_modified_date`, `adresse`, `email`, `first_name`, `last_name`, `password`, `telephone`) VALUES
(b'1', b'0', '2024-08-27 19:00:00', NULL, '123 Rue de la Paix', 'jean.dupont@email.com', 'Jean', 'Dupont', '$2a$10$abc123', '0123456789'),
(b'1', b'0', '2024-08-27 19:01:00', NULL, '456 Avenue des Champs', 'marie.martin@email.com', 'Marie', 'Martin', '$2a$10$def456', '9876543210'),
(b'1', b'0', '2024-08-27 19:02:00', NULL, '789 Boulevard Haussmann', 'pierre.durand@email.com', 'Pierre', 'Durand', '$2a$10$ghi789', '1122334455'),
(b'1', b'0', '2024-08-27 19:03:00', NULL, '321 Rue du Faubourg', 'sophie.leroy@email.com', 'Sophie', 'Leroy', '$2a$10$jkl012', '5544332211'),
(b'1', b'0', '2024-08-27 19:04:00', NULL, '654 Place de la République', 'lucas.moreau@email.com', 'Lucas', 'Moreau', '$2a$10$mno345', '6677889900'),
(b'1', b'0', '2024-08-27 19:05:00', NULL, '987 Rue de Rivoli', 'emma.petit@email.com', 'Emma', 'Petit', '$2a$10$pqr678', '1234567890'),
(b'1', b'0', '2024-08-27 19:06:00', NULL, '147 Avenue Montaigne', 'thomas.roux@email.com', 'Thomas', 'Roux', '$2a$10$stu901', '9876543210'),
(b'1', b'0', '2024-08-27 19:07:00', NULL, '258 Rue du Bac', 'chloe.simon@email.com', 'Chloé', 'Simon', '$2a$10$vwx234', '1470258369');

-- Insertion dans la table pret
-- INSERT INTO `pret` (`date_debut`, `date_fin`, `id_materiel`, `id_utilisateur`, `prolongation_valide`, `quantite`, `created_date`, `last_modified_date`, `description`) VALUES
-- ('2024-09-01', '2024-09-15', 1, 1, b'0', 1, '2024-08-27 17:00:00', NULL, 'Prêt pour projet'),
-- ('2024-09-02', '2024-09-16', 2, 2, b'0', 1, '2024-08-27 17:01:00', NULL, 'Présentation'),
-- ('2024-09-03', '2024-09-17', 3, 1, b'1', 1, '2024-08-27 17:02:00', NULL, 'Cours interactif'),
-- ('2024-09-04', '2024-09-18', 4, 2, b'0', 5, '2024-08-27 17:03:00', NULL, 'Aménagement bureau'),
-- ('2024-09-05', '2024-09-19', 5, 1, b'0', 1, '2024-08-27 17:04:00', NULL, 'Prototype'),
-- ('2024-09-06', '2024-09-20', 6, 2, b'1', 1, '2024-08-27 17:05:00', NULL, 'Recherche'),
-- ('2024-09-07', '2024-09-21', 7, 1, b'0', 1, '2024-08-27 17:06:00', NULL, 'Numérisation documents'),
-- ('2024-09-08', '2024-09-22', 8, 2, b'0', 2, '2024-08-27 17:07:00', NULL, 'Formation VR'),
-- ('2024-09-09', '2024-09-23', 9, 1, b'1', 1, '2024-08-27 17:08:00', NULL, 'Cours de dessin'),
-- ('2024-09-10', '2024-09-24', 10, 2, b'0', 1, '2024-08-27 17:09:00', NULL, 'Test de charge');

-- Insertion dans la table role_utilisateur
INSERT INTO `role_utilisateur` (`id_role`, `id_utilisateur`) VALUES
(3, 3), (4, 4), (5, 5), (6, 6), (7, 7), (8, 8), (9, 9), (10, 10);

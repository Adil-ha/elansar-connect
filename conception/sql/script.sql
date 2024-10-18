-- Création de la base de données
CREATE DATABASE IF NOT EXISTS elansar_connect;

USE elansar_connect;

-- Table des membres (adhérents)
CREATE TABLE T_Member (
    id_member INT AUTO_INCREMENT,
    lastname VARCHAR(50) NOT NULL,
    firstname VARCHAR(50) NOT NULL,
    date_birth DATE,
    address VARCHAR(100),
    phone_number VARCHAR(20),
    email VARCHAR(100) UNIQUE,
    created_at DATE NOT NULL,
    contribution_status ENUM(
        'active',
        'inactive',
        'pending'
    ) NOT NULL,
    PRIMARY KEY (id_member)
);

-- Table des cotisations (contributions)
CREATE TABLE T_Contribution (
    id_contribution INT AUTO_INCREMENT,
    amount DECIMAL(10, 2) NOT NULL,
    date_payment DATE NOT NULL,
    type_payment ENUM(
        'monthly',
        'quarterly',
        'semiannual',
        'annual'
    ) NOT NULL, -- Mois, trimestre, semestre, année
    mode_payment ENUM('cash', 'card', 'transfer') NOT NULL, -- Liquide, carte, transfert
    id_member INT NOT NULL,
    PRIMARY KEY (id_contribution),
    FOREIGN KEY (id_member) REFERENCES T_Member (id_member)
);

-- Table des rôles
CREATE TABLE T_Role (
    id_role INT AUTO_INCREMENT,
    name ENUM(
        'admin',
        'professor',
        'user',
        'superadmin'
    ) NOT NULL,
    PRIMARY KEY (id_role)
);

-- Table des utilisateurs (administrateurs, professeurs, etc.)
CREATE TABLE T_User (
    id_user INT AUTO_INCREMENT,
    lastname VARCHAR(50) NOT NULL,
    firstname VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(20),
    password VARCHAR(255) NOT NULL, -- Champ plus long pour le hash du mot de passe
    created_at DATE NOT NULL,
    PRIMARY KEY (id_user)
);

-- Table des représentants légaux
CREATE TABLE legal_representative (
    id_representative INT AUTO_INCREMENT,
    lastname VARCHAR(50) NOT NULL,
    firstname VARCHAR(50) NOT NULL,
    relation ENUM('parent', 'guardian', 'other') NOT NULL, -- Relation légale
    phone_number VARCHAR(20),
    email VARCHAR(100) NOT NULL UNIQUE,
    address VARCHAR(100),
    is_member BOOLEAN NOT NULL,
    id_member INT, -- Clé étrangère facultative
    PRIMARY KEY (id_representative),
    FOREIGN KEY (id_member) REFERENCES T_Member (id_member)
);

-- Table des créneaux horaires
CREATE TABLE niche (
    id_niche INT AUTO_INCREMENT,
    days ENUM(
        'Monday',
        'Tuesday',
        'Wednesday',
        'Thursday',
        'Friday',
        'Saturday',
        'Sunday'
    ) NOT NULL,
    moment ENUM(
        'morning',
        'afternoon',
        'evening'
    ) NOT NULL,
    PRIMARY KEY (id_niche)
);

-- Table des statistiques
CREATE TABLE t_statistic (
    id_statistic INT AUTO_INCREMENT,
    type_statistic VARCHAR(50),
    date_statistic DATE,
    total_member INT,
    total_contribution DECIMAL(10, 2),
    percentage_payer DECIMAL(5, 2),
    total_students INT,
    total_payment_scolary DECIMAL(10, 2),
    PRIMARY KEY (id_statistic)
);

-- Table des projets
CREATE TABLE T_Project (
    id_project INT AUTO_INCREMENT,
    name VARCHAR(100),
    description TEXT,
    budget DECIMAL(10, 2),
    date_start DATE,
    date_end DATE,
    status ENUM(
        'pending',
        'ongoing',
        'completed',
        'cancelled'
    ),
    id_user INT NOT NULL,
    PRIMARY KEY (id_project),
    FOREIGN KEY (id_user) REFERENCES T_User (id_user)
);

-- Table des classes
CREATE TABLE T_class (
    id_class INT AUTO_INCREMENT,
    name VARCHAR(50),
    level ENUM(
        'beginner',
        'intermediate',
        'advanced'
    ) NOT NULL, -- Niveau
    age_range VARCHAR(50), -- Tranche d'âge
    id_user INT NOT NULL, -- Professeur responsable
    id_niche INT NOT NULL, -- Créneau horaire
    PRIMARY KEY (id_class),
    FOREIGN KEY (id_user) REFERENCES T_User (id_user),
    FOREIGN KEY (id_niche) REFERENCES niche (id_niche)
);

-- Table des dépenses (liées à un projet)
CREATE TABLE T_Spent (
    id_spent INT AUTO_INCREMENT,
    description VARCHAR(255),
    amount DECIMAL(10, 2),
    date_spent DATE,
    type_spent ENUM(
        'equipment',
        'service',
        'maintenance',
        'other'
    ),
    id_user INT NOT NULL,
    id_project INT NOT NULL,
    PRIMARY KEY (id_spent),
    FOREIGN KEY (id_user) REFERENCES T_User (id_user),
    FOREIGN KEY (id_project) REFERENCES T_Project (id_project)
);

-- Table des élèves
CREATE TABLE T_Student (
    id_student INT AUTO_INCREMENT,
    firstname VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    date_birth DATE NOT NULL,
    id_class INT NOT NULL, -- Classe de l'élève
    id_representative INT NOT NULL, -- Représentant légal
    PRIMARY KEY (id_student),
    FOREIGN KEY (id_class) REFERENCES T_class (id_class),
    FOREIGN KEY (id_representative) REFERENCES legal_representative (id_representative)
);

-- Table des paiements scolaires
CREATE TABLE School_Payment (
    id_payment INT AUTO_INCREMENT,
    amount DECIMAL(10, 2) NOT NULL,
    date_payment DATE NOT NULL,
    reduced_rate BOOLEAN, -- Tarifs réduits pour les adhérents
    id_student INT NOT NULL,
    PRIMARY KEY (id_payment),
    FOREIGN KEY (id_student) REFERENCES T_Student (id_student)
);

-- Table des absences
CREATE TABLE T_Absence (
    id_absence INT AUTO_INCREMENT,
    date_absence DATE,
    id_student INT NOT NULL, -- Élève absent
    id_class INT NOT NULL, -- Classe de l'élève
    PRIMARY KEY (id_absence),
    FOREIGN KEY (id_student) REFERENCES T_Student (id_student),
    FOREIGN KEY (id_class) REFERENCES T_class (id_class)
);

-- Table de liaison entre les utilisateurs et leurs rôles
CREATE TABLE T_User_Role (
    id_role INT NOT NULL,
    id_user INT NOT NULL,
    PRIMARY KEY (id_role, id_user),
    FOREIGN KEY (id_role) REFERENCES T_Role (id_role),
    FOREIGN KEY (id_user) REFERENCES T_User (id_user)
);
-- ============================================
-- Script de création de la base de données
-- Training Center Management - PostgreSQL
-- ============================================

-- Créer la base de données
CREATE DATABASE training_center_db
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Se connecter à la base de données (à exécuter séparément)
-- \c training_center_db

-- Créer un utilisateur dédié (optionnel mais recommandé pour la production)
-- CREATE USER training_user WITH PASSWORD 'change_this_password';
-- GRANT ALL PRIVILEGES ON DATABASE training_center_db TO training_user;

-- Les tables seront créées automatiquement par Hibernate/JPA
-- grâce à : spring.jpa.hibernate.ddl-auto=update

-- ============================================
-- Vérification (après le premier démarrage)
-- ============================================

-- Lister les tables créées
-- SELECT table_name 
-- FROM information_schema.tables 
-- WHERE table_schema = 'public' 
-- ORDER BY table_name;

-- Vérifier les contraintes
-- SELECT 
--     tc.constraint_name, 
--     tc.table_name, 
--     kcu.column_name,
--     ccu.table_name AS foreign_table_name,
--     ccu.column_name AS foreign_column_name 
-- FROM information_schema.table_constraints AS tc 
-- JOIN information_schema.key_column_usage AS kcu
--   ON tc.constraint_name = kcu.constraint_name
-- JOIN information_schema.constraint_column_usage AS ccu
--   ON ccu.constraint_name = tc.constraint_name
-- WHERE tc.constraint_type = 'FOREIGN KEY'
-- ORDER BY tc.table_name;

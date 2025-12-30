-- ============================================
-- Script de test de connexion PostgreSQL
-- ============================================

-- 1. Vérifier que PostgreSQL est accessible
-- Exécuter : psql -U postgres

-- 2. Vérifier que la base existe
SELECT datname FROM pg_database WHERE datname = 'training_center_db';

-- Si elle n'existe pas, la créer :
-- CREATE DATABASE training_center_db;

-- 3. Se connecter à la base
-- \c training_center_db

-- 4. Vérifier les tables existantes
SELECT table_name 
FROM information_schema.tables 
WHERE table_schema = 'public' 
ORDER BY table_name;

-- 5. Vérifier les permissions
SELECT 
    datname,
    datacl
FROM pg_database 
WHERE datname = 'training_center_db';

-- 6. Vérifier que l'utilisateur postgres a les droits
SELECT 
    usename,
    usecreatedb,
    usesuper
FROM pg_user 
WHERE usename = 'postgres';

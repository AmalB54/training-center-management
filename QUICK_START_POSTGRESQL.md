# Démarrage Rapide - PostgreSQL

## Étapes Rapides

### 1. Installer PostgreSQL
- Télécharger depuis : https://www.postgresql.org/download/
- Installer avec le mot de passe `postgres` (ou noter celui que vous choisissez)

### 2. Créer la Base de Données

```bash
# Se connecter à PostgreSQL
psql -U postgres

# Créer la base de données
CREATE DATABASE training_center_db;

# Quitter
\q
```

Ou utiliser le script fourni :
```bash
psql -U postgres -f database/setup_postgresql.sql
```

### 3. Configurer l'Application

Modifier `src/main/resources/application.properties` :

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/training_center_db
spring.datasource.username=postgres
spring.datasource.password=votre_mot_de_passe
```

### 4. Démarrer l'Application

```bash
mvn clean spring-boot:run
```

### 5. Vérifier

- L'application démarre sans erreur
- Les tables sont créées automatiquement
- Accéder à http://localhost:8081/admin

## Utiliser H2 pour le Développement

Si vous voulez garder H2 pour le développement :

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Vérifier les Tables

```bash
psql -U postgres -d training_center_db

# Lister les tables
\dt

# Quitter
\q
```

## Aide

Pour plus de détails, voir `POSTGRESQL_MIGRATION.md`

# Guide de Migration H2 → PostgreSQL

## Prérequis

1. **PostgreSQL installé** sur votre machine
   - Télécharger depuis : https://www.postgresql.org/download/
   - Version recommandée : PostgreSQL 14 ou supérieur

2. **Outils nécessaires** :
   - psql (client PostgreSQL en ligne de commande)
   - pgAdmin (interface graphique, optionnel)

## Étape 1 : Installation de PostgreSQL

### Windows
1. Télécharger l'installateur depuis le site officiel
2. Exécuter l'installateur
3. Noter le mot de passe du superutilisateur `postgres` que vous définissez
4. Laisser le port par défaut : `5432`

### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo systemctl start postgresql
sudo systemctl enable postgresql
```

### macOS
```bash
brew install postgresql@14
brew services start postgresql@14
```

## Étape 2 : Créer la Base de Données

### Méthode 1 : Via psql (Ligne de commande)

```bash
# Se connecter à PostgreSQL
psql -U postgres

# Créer la base de données
CREATE DATABASE training_center_db;

# Créer un utilisateur dédié (optionnel mais recommandé)
CREATE USER training_user WITH PASSWORD 'your_secure_password';
GRANT ALL PRIVILEGES ON DATABASE training_center_db TO training_user;

# Quitter psql
\q
```

### Méthode 2 : Via pgAdmin (Interface graphique)

1. Ouvrir pgAdmin
2. Se connecter au serveur PostgreSQL
3. Clic droit sur "Databases" → "Create" → "Database"
4. Nom : `training_center_db`
5. Owner : `postgres` (ou votre utilisateur)
6. Cliquer sur "Save"

## Étape 3 : Configuration de l'Application

### Option A : Utiliser PostgreSQL (Production)

Le fichier `application.properties` est déjà configuré pour PostgreSQL.

**Modifier les paramètres de connexion** dans `src/main/resources/application.properties` :

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/training_center_db
spring.datasource.username=postgres
spring.datasource.password=votre_mot_de_passe
```

### Option B : Garder H2 pour le développement

Pour utiliser H2 en développement, lancer l'application avec :

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Ou dans votre IDE, ajouter dans les arguments VM :
```
-Dspring.profiles.active=dev
```

## Étape 4 : Migration des Données (si vous avez déjà des données H2)

### Si vous avez des données importantes dans H2

1. **Exporter les données depuis H2** :
   - Accéder à http://localhost:8081/h2-console
   - Exécuter des requêtes SQL pour exporter les données

2. **Importer dans PostgreSQL** :
   ```bash
   psql -U postgres -d training_center_db -f exported_data.sql
   ```

### Si vous partez de zéro

Les tables seront créées automatiquement au premier démarrage grâce à :
```properties
spring.jpa.hibernate.ddl-auto=update
```

## Étape 5 : Tester la Connexion

1. **Démarrer PostgreSQL** :
   ```bash
   # Windows (si installé comme service, il démarre automatiquement)
   # Linux
   sudo systemctl start postgresql
   # macOS
   brew services start postgresql@14
   ```

2. **Vérifier que PostgreSQL écoute** :
   ```bash
   # Windows
   netstat -an | findstr 5432
   
   # Linux/macOS
   netstat -an | grep 5432
   ```

3. **Démarrer l'application** :
   ```bash
   mvn spring-boot:run
   ```

4. **Vérifier les logs** :
   - Vous devriez voir : `HikariPool-1 - Starting...`
   - Puis : `HikariPool-1 - Start completed.`
   - Pas d'erreurs de connexion

## Étape 6 : Vérification

1. **Se connecter à PostgreSQL** :
   ```bash
   psql -U postgres -d training_center_db
   ```

2. **Lister les tables** :
   ```sql
   \dt
   ```

3. **Vérifier qu'elles sont créées** :
   - `students`
   - `trainers`
   - `courses`
   - `specialities`
   - `enrollments`
   - `grades`
   - `course_sessions`
   - `course_specialty` (table de jointure)

## Configuration Avancée

### Pool de Connexions

Les paramètres du pool HikariCP sont déjà configurés :
```properties
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=20000
```

### SSL (pour production)

Si vous utilisez PostgreSQL avec SSL :
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/training_center_db?ssl=true&sslmode=require
```

### Variables d'Environnement (Recommandé pour production)

Au lieu de mettre les mots de passe dans le fichier, utilisez des variables d'environnement :

```properties
spring.datasource.url=${DATABASE_URL:jdbc:postgresql://localhost:5432/training_center_db}
spring.datasource.username=${DATABASE_USERNAME:postgres}
spring.datasource.password=${DATABASE_PASSWORD:postgres}
```

Puis définir les variables :
```bash
# Windows
set DATABASE_PASSWORD=votre_mot_de_passe

# Linux/macOS
export DATABASE_PASSWORD=votre_mot_de_passe
```

## Dépannage

### Erreur : "Connection refused"
- Vérifier que PostgreSQL est démarré
- Vérifier le port (par défaut 5432)
- Vérifier le firewall

### Erreur : "Authentication failed"
- Vérifier le nom d'utilisateur et le mot de passe
- Vérifier le fichier `pg_hba.conf` si nécessaire

### Erreur : "Database does not exist"
- Créer la base de données (voir Étape 2)

### Erreur : "Permission denied"
- Vérifier les permissions de l'utilisateur PostgreSQL
- Utiliser un utilisateur avec les droits appropriés

### Voir les requêtes SQL exécutées

Déjà activé dans `application.properties` :
```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## Différences H2 vs PostgreSQL

| Fonctionnalité | H2 | PostgreSQL |
|---------------|----|----------- |
| Type | In-Memory | Serveur |
| Persistance | Non (mémoire) | Oui (disque) |
| Production | Non recommandé | Oui |
| Performance | Rapide pour dev | Optimisé pour prod |
| Fonctions SQL | Limitées | Complètes |

## Retour à H2 (si nécessaire)

Pour revenir temporairement à H2 :
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Ou modifier `application.properties` pour utiliser H2.

## Prochaines Étapes

1. ✅ Migration terminée
2. 🔄 Tester toutes les fonctionnalités
3. 📊 Vérifier les performances
4. 🔒 Configurer les sauvegardes PostgreSQL
5. 📝 Documenter les procédures de maintenance

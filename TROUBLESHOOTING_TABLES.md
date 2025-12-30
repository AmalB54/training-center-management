# Dépannage : Tables non créées dans PostgreSQL

## Vérifications à Faire

### 1. Vérifier que PostgreSQL est démarré

```bash
# Windows - Vérifier le service
Get-Service -Name postgresql*

# Linux
sudo systemctl status postgresql

# macOS
brew services list | grep postgresql
```

### 2. Vérifier que la base de données existe

```bash
# Se connecter à PostgreSQL
psql -U postgres

# Lister les bases de données
\l

# Vérifier que training_center_db existe
# Si elle n'existe pas, la créer :
CREATE DATABASE training_center_db;

# Se connecter à la base
\c training_center_db

# Vérifier les tables (devrait être vide au début)
\dt

# Quitter
\q
```

### 3. Vérifier les logs de l'application

Au démarrage, cherchez dans les logs :

**✅ Signes que ça fonctionne :**
```
HikariPool-1 - Starting...
HikariPool-1 - Start completed.
Hibernate: create table courses ...
Hibernate: create table students ...
```

**❌ Signes de problème :**
```
Connection refused
Authentication failed
Database does not exist
```

### 4. Vérifier la connexion

Testez la connexion manuellement :

```bash
psql -U postgres -d training_center_db -h localhost
# Entrer le mot de passe : amal
```

Si ça ne fonctionne pas, vérifiez :
- Le mot de passe dans `application.properties`
- Que PostgreSQL écoute sur le port 5432

### 5. Forcer la création des tables

Si les tables ne sont toujours pas créées, changez temporairement dans `application.properties` :

```properties
# Au lieu de update, utiliser create-drop (ATTENTION : supprime les données)
spring.jpa.hibernate.ddl-auto=create-drop
```

**⚠️ ATTENTION** : `create-drop` supprime toutes les tables à l'arrêt de l'application !

Après vérification, remettez :
```properties
spring.jpa.hibernate.ddl-auto=update
```

### 6. Vérifier que les entités sont scannées

Vérifiez que votre classe principale scanne le bon package :

```java
@SpringBootApplication  // Scanne automatiquement le package et sous-packages
public class TrainingCenterManagementApplication {
    // ...
}
```

### 7. Vérifier les permissions PostgreSQL

L'utilisateur `postgres` doit avoir les droits de création :

```sql
-- Se connecter en tant que postgres
psql -U postgres

-- Vérifier les permissions
\du

-- Si nécessaire, donner tous les droits
GRANT ALL PRIVILEGES ON DATABASE training_center_db TO postgres;
```

## Solutions selon le Problème

### Problème : "Connection refused"
**Solution :**
1. Démarrer PostgreSQL
2. Vérifier le port (5432 par défaut)
3. Vérifier le firewall

### Problème : "Authentication failed"
**Solution :**
1. Vérifier le mot de passe dans `application.properties`
2. Tester avec `psql -U postgres`

### Problème : "Database does not exist"
**Solution :**
```sql
CREATE DATABASE training_center_db;
```

### Problème : Tables créées mais vides
**C'est normal !** Les tables sont créées vides. Ajoutez des données via l'interface admin.

### Problème : Aucune table créée
**Solution :**
1. Vérifier les logs pour des erreurs
2. Changer temporairement `ddl-auto=create-drop`
3. Vérifier que les entités ont `@Entity` et `@Table`

## Commandes Utiles

### Vérifier les tables créées
```sql
-- Se connecter
psql -U postgres -d training_center_db

-- Lister les tables
\dt

-- Voir la structure d'une table
\d students

-- Compter les tables
SELECT COUNT(*) FROM information_schema.tables 
WHERE table_schema = 'public';
```

### Voir les logs SQL
Les logs SQL sont déjà activés dans `application.properties` :
```properties
spring.jpa.show-sql=true
logging.level.org.hibernate.SQL=DEBUG
```

Vous devriez voir les requêtes `CREATE TABLE` dans la console.

## Test Rapide

1. **Démarrer l'application** :
   ```bash
   mvn spring-boot:run
   ```

2. **Vérifier les logs** :
   - Cherchez "HikariPool-1 - Start completed"
   - Cherchez "create table" dans les logs

3. **Vérifier dans PostgreSQL** :
   ```bash
   psql -U postgres -d training_center_db -c "\dt"
   ```

4. **Si aucune table** :
   - Vérifier les erreurs dans les logs
   - Tester la connexion manuellement
   - Changer temporairement `ddl-auto=create-drop`

## Tables Attendues

Après le démarrage, vous devriez avoir :
- `students`
- `trainers`
- `courses`
- `specialities`
- `enrollments`
- `grades`
- `course_sessions`
- `course_specialty` (table de jointure)

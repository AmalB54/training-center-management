# Diagnostic : Pourquoi les tables ne sont pas créées ?

## ✅ Checklist de Vérification

### Étape 1 : Vérifier que PostgreSQL fonctionne

```bash
# Tester la connexion
psql -U postgres -h localhost

# Si ça demande un mot de passe, entrez : amal
# Si ça fonctionne, vous êtes connecté ✅
```

### Étape 2 : Vérifier que la base de données existe

```sql
-- Dans psql
\l

-- Chercher training_center_db dans la liste
-- Si elle n'existe pas :
CREATE DATABASE training_center_db;
```

### Étape 3 : Vérifier la configuration

Vérifiez dans `src/main/resources/application.properties` :

```properties
# Doit être présent
spring.datasource.url=jdbc:postgresql://localhost:5432/training_center_db
spring.datasource.username=postgres
spring.datasource.password=amal
spring.jpa.hibernate.ddl-auto=update
```

### Étape 4 : Vérifier les logs au démarrage

Lancez l'application et cherchez dans les logs :

**✅ Si ça fonctionne, vous verrez :**
```
HikariPool-1 - Starting...
HikariPool-1 - Start completed.
Hibernate: create table courses ...
Hibernate: create table students ...
```

**❌ Si ça ne fonctionne pas, vous verrez :**
```
Connection refused
Authentication failed
Database "training_center_db" does not exist
```

### Étape 5 : Forcer la création (Solution temporaire)

Si les tables ne sont toujours pas créées, modifiez `application.properties` :

```properties
# Changer de update à create (TEMPORAIRE)
spring.jpa.hibernate.ddl-auto=create
```

**⚠️ ATTENTION** : `create` supprime et recrée les tables à chaque démarrage !

1. Démarrer l'application avec `create`
2. Vérifier que les tables sont créées
3. **Remettre immédiatement** `update` :
   ```properties
   spring.jpa.hibernate.ddl-auto=update
   ```

## 🔍 Diagnostic Détaillé

### Problème 1 : Erreur de connexion

**Symptômes :**
- `Connection refused`
- `Connection timed out`

**Solutions :**
1. Vérifier que PostgreSQL est démarré :
   ```bash
   # Windows
   Get-Service postgresql*
   
   # Linux
   sudo systemctl status postgresql
   ```

2. Vérifier le port (5432 par défaut)

3. Vérifier le firewall

### Problème 2 : Erreur d'authentification

**Symptômes :**
- `Authentication failed`
- `Password authentication failed`

**Solutions :**
1. Vérifier le mot de passe dans `application.properties`
2. Tester avec psql :
   ```bash
   psql -U postgres -h localhost
   # Entrer le mot de passe
   ```

### Problème 3 : Base de données n'existe pas

**Symptômes :**
- `Database "training_center_db" does not exist`

**Solution :**
```sql
CREATE DATABASE training_center_db;
```

### Problème 4 : Pas d'erreur mais pas de tables

**Symptômes :**
- L'application démarre sans erreur
- Mais aucune table dans PostgreSQL

**Solutions :**

1. **Vérifier les logs SQL** :
   - Les logs doivent montrer les requêtes `CREATE TABLE`
   - Si vous ne voyez rien, Hibernate ne crée pas les tables

2. **Vérifier que les entités sont scannées** :
   - Toutes les entités doivent avoir `@Entity`
   - Elles doivent être dans le package `com.iit.trainingcenter.entity`

3. **Forcer avec create** :
   ```properties
   spring.jpa.hibernate.ddl-auto=create
   ```

4. **Vérifier les permissions** :
   ```sql
   -- Se connecter en tant que postgres
   psql -U postgres -d training_center_db
   
   -- Vérifier les permissions
   \du
   
   -- Donner tous les droits si nécessaire
   GRANT ALL PRIVILEGES ON DATABASE training_center_db TO postgres;
   ```

## 🚀 Solution Rapide

### Option 1 : Utiliser H2 temporairement

Pour tester rapidement, utilisez H2 :

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Option 2 : Forcer la création

1. Modifier `application.properties` :
   ```properties
   spring.jpa.hibernate.ddl-auto=create
   ```

2. Démarrer l'application :
   ```bash
   mvn spring-boot:run
   ```

3. Vérifier les tables :
   ```bash
   psql -U postgres -d training_center_db -c "\dt"
   ```

4. **IMPORTANT** : Remettre `update` immédiatement :
   ```properties
   spring.jpa.hibernate.ddl-auto=update
   ```

## 📋 Commandes de Vérification

### Vérifier les tables créées
```bash
psql -U postgres -d training_center_db -c "\dt"
```

### Voir toutes les tables avec leurs colonnes
```bash
psql -U postgres -d training_center_db -c "\d+"
```

### Compter les tables
```sql
SELECT COUNT(*) 
FROM information_schema.tables 
WHERE table_schema = 'public';
```

### Voir les logs SQL en temps réel
Les logs sont déjà activés dans `application.properties`. 
Regardez la console lors du démarrage.

## 🎯 Résolution Étape par Étape

1. ✅ **PostgreSQL est démarré ?**
   ```bash
   psql -U postgres
   ```

2. ✅ **La base existe ?**
   ```sql
   \l
   CREATE DATABASE training_center_db;  -- Si nécessaire
   ```

3. ✅ **La connexion fonctionne ?**
   ```bash
   psql -U postgres -d training_center_db
   ```

4. ✅ **Les logs montrent des erreurs ?**
   - Regardez la console au démarrage
   - Cherchez "ERROR" ou "Exception"

5. ✅ **Forcer la création ?**
   - Changez `ddl-auto=create` temporairement
   - Vérifiez les tables
   - Remettez `update`

## 📞 Si Rien ne Fonctionne

1. Vérifiez les logs complets de l'application
2. Testez avec H2 (profil dev) pour isoler le problème
3. Vérifiez la version de PostgreSQL (14+ recommandé)
4. Vérifiez que le driver PostgreSQL est dans `pom.xml`

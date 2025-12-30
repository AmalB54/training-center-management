# Guide d'exécution du projet Training Center Management

## Prérequis
- ✅ Java 17 ou supérieur (vous avez Java 21)
- ✅ Maven 3.6+ (vous avez Maven 3.9.11)
- ✅ Navigateur web

## Méthode 1 : Via Maven (Ligne de commande)

### Étape 1 : Compiler le projet
```bash
cd c:\dev\Spring\workspace\training-center-management
mvn clean install
```

### Étape 2 : Lancer l'application
```bash
mvn spring-boot:run
```

Ou directement :
```bash
mvn clean spring-boot:run
```

## Méthode 2 : Via IDE (IntelliJ IDEA / Eclipse / VS Code)

### IntelliJ IDEA
1. Ouvrir le projet : `File` → `Open` → Sélectionner le dossier du projet
2. Attendre que Maven télécharge les dépendances
3. Cliquer droit sur `TrainingCenterManagementApplication.java`
4. Sélectionner `Run 'TrainingCenterManagementApplication'`

### Eclipse / Spring Tool Suite
1. `File` → `Import` → `Existing Maven Projects`
2. Sélectionner le dossier du projet
3. Cliquer droit sur `TrainingCenterManagementApplication.java`
4. `Run As` → `Java Application`

### VS Code
1. Installer l'extension "Extension Pack for Java"
2. Ouvrir le projet
3. Ouvrir `TrainingCenterManagementApplication.java`
4. Cliquer sur le bouton "Run" au-dessus de `main()`

## Méthode 3 : Exécuter le JAR

### Créer le JAR
```bash
mvn clean package
```

### Exécuter le JAR
```bash
java -jar target/training-center-management-0.0.1-SNAPSHOT.jar
```

## Vérification du démarrage

Une fois lancé, vous devriez voir dans la console :
```
Started TrainingCenterManagementApplication in X.XXX seconds
```

## Accès à l'application

### Interface Admin (Thymeleaf)
- **URL** : http://localhost:8081/admin
- **Login** : `admin` / `admin123`
- **Rôle** : ADMIN

### Console H2 Database
- **URL** : http://localhost:8081/h2-console
- **JDBC URL** : `jdbc:h2:mem:trainingdb`
- **Username** : `sa`
- **Password** : (vide)

### APIs REST (pour Angular)
- **Base URL** : http://localhost:8081/api
- **Authentification** : HTTP Basic Auth
  - Admin : `admin` / `admin123`
  - Student : `student` / `student123`
  - Trainer : `trainer` / `trainer123`

## Endpoints disponibles

### Admin (Thymeleaf)
- Dashboard : http://localhost:8081/admin
- Students : http://localhost:8081/admin/students
- Trainers : http://localhost:8081/admin/trainers
- Courses : http://localhost:8081/admin/courses
- Enrollments : http://localhost:8081/admin/enrollments
- Grades : http://localhost:8081/admin/grades
- Timetable : http://localhost:8081/admin/timetable
- Specialties : http://localhost:8081/admin/specialties

### APIs REST
- GET `/api/students` - Liste des étudiants (ADMIN)
- GET `/api/trainers` - Liste des formateurs (ADMIN)
- GET `/api/courses` - Liste des cours (ADMIN, STUDENT, TRAINER)
- GET `/api/enrollments/student/{matricule}` - Inscriptions d'un étudiant
- GET `/api/grades/student/{matricule}` - Notes d'un étudiant
- POST `/api/courses` - Créer un cours (ADMIN, TRAINER)
- POST `/api/enrollments` - Créer une inscription (ADMIN, STUDENT)
- POST `/api/grades` - Créer une note (ADMIN, TRAINER)

## Test rapide

1. **Démarrer l'application** : `mvn spring-boot:run`
2. **Ouvrir le navigateur** : http://localhost:8081/admin
3. **Se connecter** : `admin` / `admin123`
4. **Vérifier le dashboard** : Vous devriez voir les statistiques
5. **Tester la création** :
   - Créer une Specialty
   - Créer un Student
   - Créer un Trainer
   - Créer un Course
   - Créer un Enrollment

## Dépannage

### Port 8081 déjà utilisé
Modifier `application.properties` :
```properties
server.port=8082
```

### Erreur de compilation
```bash
mvn clean install -U
```

### Problème de dépendances
```bash
mvn clean install -DskipTests
```

### Voir les logs SQL
Déjà activé dans `application.properties` :
```properties
spring.jpa.show-sql=true
```

## Structure de la base de données

La base H2 en mémoire est créée automatiquement au démarrage grâce à :
```properties
spring.jpa.hibernate.ddl-auto=update
```

Les tables sont créées automatiquement à partir des entités JPA.

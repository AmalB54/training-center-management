# Guide des Profils Spring Boot

## 📋 Profils disponibles

Le projet utilise deux profils Spring Boot :

### 1. **`prod`** (Production - PostgreSQL) - **DÉFAUT**
- Base de données : PostgreSQL
- Cache Thymeleaf : Activé
- Logging : Minimal (WARN)
- SQL visible : Non

### 2. **`dev`** (Développement - H2)
- Base de données : H2 (en mémoire)
- Cache Thymeleaf : Désactivé
- Logging : Détaillé (DEBUG)
- SQL visible : Oui
- Console H2 : Disponible sur `/h2-console`

## 🚀 Comment utiliser les profils

### Méthode 1 : Via la ligne de commande

**Production (PostgreSQL) - Par défaut :**
```bash
mvn spring-boot:run
```

**Développement (H2) :**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Méthode 2 : Via les variables d'environnement

**Windows (PowerShell) :**
```powershell
$env:SPRING_PROFILES_ACTIVE="dev"
mvn spring-boot:run
```

**Linux/macOS :**
```bash
export SPRING_PROFILES_ACTIVE=dev
mvn spring-boot:run
```

### Méthode 3 : Dans votre IDE

**IntelliJ IDEA :**
1. Run → Edit Configurations
2. Ajouter dans "VM options" ou "Program arguments" :
   ```
   -Dspring.profiles.active=dev
   ```
   Ou dans "Environment variables" :
   ```
   SPRING_PROFILES_ACTIVE=dev
   ```

**Eclipse / Spring Tool Suite :**
1. Run → Run Configurations
2. Arguments → VM arguments :
   ```
   -Dspring.profiles.active=dev
   ```

**VS Code :**
Dans `.vscode/launch.json` :
```json
{
  "configurations": [{
    "type": "java",
    "request": "launch",
    "mainClass": "com.iit.trainingcenter.TrainingCenterManagementApplication",
    "vmArgs": "-Dspring.profiles.active=dev"
  }]
}
```

### Méthode 4 : Modifier `application.properties`

Dans `src/main/resources/application.properties`, changer :
```properties
spring.profiles.active=prod  # Pour PostgreSQL
```
en :
```properties
spring.profiles.active=dev   # Pour H2
```

## 📁 Structure des fichiers

```
src/main/resources/
├── application.properties          # Configuration commune + profil par défaut
├── application-dev.properties      # Configuration développement (H2)
└── application-prod.properties     # Configuration production (PostgreSQL)
```

## ⚙️ Configuration actuelle

**Par défaut**, le projet utilise le profil **`prod`** (PostgreSQL).

Pour changer le profil par défaut, modifiez dans `application.properties` :
```properties
spring.profiles.active=dev  # ou prod
```

## 🔍 Vérifier le profil actif

Au démarrage de l'application, vous verrez dans les logs :
```
No active profile set, falling back to 1 default profile: "default"
```
Ou :
```
The following profiles are active: prod
```

## 💡 Recommandations

- **Développement local** : Utilisez le profil `dev` avec H2 (plus rapide, pas besoin de PostgreSQL)
- **Tests** : Utilisez le profil `dev` avec H2
- **Production** : Utilisez le profil `prod` avec PostgreSQL

## 🎯 Exemple d'utilisation

### Scénario 1 : Développement rapide
```bash
# Démarrer avec H2 (pas besoin de PostgreSQL)
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Scénario 2 : Tester avec PostgreSQL
```bash
# Démarrer avec PostgreSQL (assurez-vous que PostgreSQL est démarré)
mvn spring-boot:run
# ou explicitement :
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

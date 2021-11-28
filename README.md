OAuth, Authorization/Authentication
Example
https://github.com/bezkoder/spring-boot-security-postgresql/blob/master/src/main/java/com/bezkoder/spring/security/postgresql/controllers/AuthController.java

Maven - Compilation Surfire

@interface annotations

DB 
Example:

@OneToMany(targetEntity = UserTarget.class, mappedBy = "user", orphanRemoval = true, cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private final List<UserTarget> userTargets = new ArrayList<>();
    
        
public abstract class UpseartableRepository<T extends BaseEntity> implements Upseartable<T>, CrudRepository<T, UUID> {

Testing

Debugging 

AOP




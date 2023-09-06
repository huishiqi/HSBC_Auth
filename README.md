# HSBC_Auth
hsbc hometask of authentication and authorization

main functions in AuthApi
1. boolean createUser(String username, String password) throws Exception;

2. boolean deleteUser(String username);

3. boolean createRole(String roleName);

4. boolean deleteRole(String roleName);

5. boolean addRoleToUser(String roleName, String username);

6. String login(String username, String password) throws Exception;

7. void logout(String token);

8. boolean hasRole(String token, String roleName);

9. List<Role> myAllRoles(String token);

Main Entities:
1. User: contains all user information
2. Role: contains all role information

Main services:
1. AuthService: do the token generate and invalidate
2. UserService: functions with users 
including: create user, delete user, user login in check, add role to user, check user roles, get all user roles
3. RoleService: functions with roles
including: create roles, delete role, get role by roleName
4. AuthApi: All main business api provided, how the interface is designed.

Main utils:
1. JwtUtils: do token generation using JWT, and token verify
2. PasswordUtil: do password encoding by hash and password match
3. StringUtils: utility of String

External libraries:
1. java-jwt: this is used for the jwt token generation after user login.
2. junit and mockito-core for unit test


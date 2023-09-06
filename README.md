# HSBC_Auth
hsbc hometask of authentication and authorization

Main functions in AuthApi (AuthApi achieves its all functions by calling three services AuthService, UserService and RoleService)
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
1. AuthService: do the token generation and invalidate
   
2. UserService: functions with users 
including: create user, delete user, user login check, add role to user, check user role, get all user roles

3. RoleService: functions with roles
including: create role, delete role, get role by roleName


Main utils:
1. JwtUtils: do token generation using JWT, and token verification
   
2. PasswordUtil: do password encoding by hash and password match
  
3. StringUtils: utility of String

External libraries used:
1. java-jwt: this is used for the token generation with expire time and username claimed after user login.
2. junit and mockito-core for unit test


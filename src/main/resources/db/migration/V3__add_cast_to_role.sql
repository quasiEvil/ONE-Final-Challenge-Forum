ALTER TABLE users
ALTER COLUMN role TYPE user_roles USING role::user_roles;
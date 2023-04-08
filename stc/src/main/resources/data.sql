INSERT INTO permission_group (group_name)
Values ('Admin Group');
INSERT INTO permission (permission_level, user_email, group_id)
VALUES ('EDIT', 'edit@email', 1);
INSERT INTO permission (permission_level, user_email, group_id)
VALUES ('VIEW', 'view@email', 1);



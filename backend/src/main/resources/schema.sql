INSERT INTO roles
    (id, label)
SELECT 1, 'USER'
WHERE NOT EXISTS (
    SELECT id FROM roles WHERE id BETWEEN 0 AND 2
);


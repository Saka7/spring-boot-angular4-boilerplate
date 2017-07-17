#!/bin/sh

db_name=test
user_name=test
user_password=test
db_schema_file_name=scripts/$db_name""_schema.pg.sql

echo "Confirm user $user_name and database $db_name creation: "

psql -U postgres << EOF
CREATE USER $user_name WITH ENCRYPTED PASSWORD '$user_password';
CREATE DATABASE $db_name OWNER $user_name;
EOF

if [ $? -eq 0 ]; then
    echo "Database $db_name successfully initialized with owner $user_name"
    if [ -f $db_schema_file_name ]; then
      PGPASSWORD=$user_password psql -U $user_name $db_name < $db_schema_file_name
    fi
else
    echo "Database $db_name failed to initialize"
fi
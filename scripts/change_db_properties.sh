#!/bin/sh

printf "Are you changing db properties for the first time [y/n]: "
read -r changed_for_the_first_time

if echo $changed_for_the_first_time | grep -iqF 'n'; then
  printf "Enter old db name: "
  read -r old_db_name
  printf "Enter old db user: "
  read -r old_db_user
  printf "Enter old db password: "
  read -r old_db_password
else
  old_db_name=test
  old_db_user=test
  old_db_password=test
fi

printf "Enter database name: "
read -r db_name
printf "Enter database username: "
read -r db_user
printf "Enter database user password: "
read -r db_password

init_db_file=scripts/init_db.sh
app_properties_file=backend/src/main/resources/application.properties

# Update database initialization file
sed -i "s/db_name\s*=\s*$old_db_name/db_name=$db_name/" $init_db_file
sed -i "s/user_name\s*=\s*$old_db_user/user_name=$db_user/" $init_db_file
sed -i "s/user_password\s*=\s*$old_db_password/user_password=$db_password/" $init_db_file
mv scripts/$old_db_name""_schema.pg.sql scripts/$db_name""_schema.pg.sql

# Update application properties file
sed -i "s/\/$old_db_name/\/$db_name/" $app_properties_file
sed -i "s/username\s*=\s*$old_db_user/username=$db_user/" $app_properties_file
sed -i "s/password\s*=\s*$old_db_password/password=$db_password/" $app_properties_file

if [ $? -eq 0 ]; then
  echo "Database $db_name and $db_user has been successfully changed"
  echo "====="
fi

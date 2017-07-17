#!/bin/sh

printf "Are you changing app name and version for the first time [y/n]: "
read -r changed_for_the_first_time

if echo $changed_for_the_first_time | grep -iqF 'n'; then
  printf "Enter old app name: "
  read -r old_app_name
  printf "Enter old app version: "
  read -r old_app_version
else
  old_app_version=0.0.1
  old_app_name=app-name
fi

printf "Enter app name: "
read -r app_name
printf "Enter app version: "
read -r app_version

package_json_file=frontend/package.json
angular_cli_file=frontend/angular-cli.json
build_gradle_file=backend/build.gradle

# Update package.json file
sed -i "s/$old_app_name/$app_name/" $package_json_file
sed -i "s/$old_app_version/$app_version/" $package_json_file

# Update angular cli file
sed -i "s/$old_app_name/$app_name/" $angular_cli_file
sed -i "s/$old_app_name/$app_name/" $angular_cli_file

# Update gradle build file
sed -i "s/baseName\s*=\s*'$old_app_name'/baseName='$app_name'/" $build_gradle_file
sed -i "s/version\s*=\s*'$old_app_version'/version='$app_version'/" $build_gradle_file

if [ $? -eq 0 ]; then
  echo "app name has been successfully changed to $app_name"
  echo "====="
fi

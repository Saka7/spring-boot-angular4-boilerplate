#!/bin/sh

sh ./scripts/change_app_name.sh && \
sh ./scripts/change_db_properties.sh && \
sh ./scripts/init_db.sh

if [ $? -eq 0 ];then
  echo "Quickstart successfully generated"
fi

printf "Build? (y/n): "
read -r build

if echo $build | grep -iqF 'y'; then
  sh ./scripts/build_frontend.sh && \
  sh ./scripts/build_backend.sh
fi

printf "Run? (y/n): "
read -r run

if echo $run | grep -iqF 'y'; then
  gradle bootRun -p backend
fi


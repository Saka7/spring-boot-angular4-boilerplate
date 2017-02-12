#!/bin/sh

npm install --prefix ./frontend && \
npm run build --prefix ./frontend && \
gradle -p ./backend build

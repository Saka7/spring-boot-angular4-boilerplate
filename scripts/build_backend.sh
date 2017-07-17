#!/bin/sh

rm -rf backend/app.mv.db backend/app.trace.db
gradle -p ./backend build

#!/bin/bash
echo "INSERT IGNORE INTO user (username,password,role) VALUES " > insertUser.sql

echo "('user','user','user'), ('admin','admin','Admin');" >> insertUser.sql



# ZORRIANA GUI

This project was made in Apache NetBeans IDE 12.6 using an Oracle Database XE connection for Databases course of 7th semester in ESIME Culhuacan IPN.
Computer Engineering Degree.

## Table of contents:

- 🚀 [Installation](#installation)
- 📺 [Playlists](#playlists)
- 🗓 [Screenshots](#screenshots)
- 🗄 [Database](#database)

## Installation:

Configurations to set up the database:

Connect as System:

```
conn system/PassAdmin
```
Change current session to the example container XEPDB1:
```
alter session set container = xepdb1;
```
Create tablespace for the database (change TU USUARIO for the current user of the computer):
```
create tablespace tbs_bodega
datafile 'C:\app\TU USUARIO \product\18.0.0\oradata\XE\XEPDB1\tbs_bodega'
size 50M
autoextend on next 10M
maxsize 200M online;
```
Create user for the database: 

```
create user ltcgun
identified by mheealx
default tablespace tbs_bodega
quota 200M on tbs_bodega;
```
Create role for permissions:
```
create role jefazo;
```
Grant permissions to role:
```
grant create session, create table, alter any table, drop any table,
insert any table, update any table, delete any table, create sequence, create trigger,
select any table to jefazo;
```
Grant role to user:
```
grant jefazo to ltcgun;
```

Connect to the new user:
```
conn ltcgun/mheealx@localhost:1521/xepdb1
```
Now with SQL Developer, you can run the first document to create tables:

[Tables](tablas.txt)





## Screenshots

![Login](https://github.com/g4x6n/zorriana/raw/master/.readme/login.png)
![Dashboard](https://github.com/g4x6n/zorriana/raw/master/.readme/dashboard.png)


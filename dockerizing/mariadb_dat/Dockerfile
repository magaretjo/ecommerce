FROM mariadb
ENV MYSQL_ROOT_PASSWORD eraser
ENV MYSQL_DATABASE mydb
COPY ./mysql /var/lib/mysql
EXPOSE 3306
ENTRYPOINT ["mysqld", "--user=root"]
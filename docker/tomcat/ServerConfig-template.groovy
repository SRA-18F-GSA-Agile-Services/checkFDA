dataSource.dbCreate = '${SEARCHFDA_DB_CREATE}' // one of 'create', 'create-drop', 'update', 'validate', ''
dataSource.username = '${MYSQL_ENV_MYSQL_USER}'
dataSource.password = '${MYSQL_ENV_MYSQL_PASSWORD}'
dataSource.url = 'jdbc:mysql://mysql:3306/${MYSQL_ENV_MYSQL_DATABASE}?useUnicode=true&autoReconnect=true'
openfdaapi.token = ${OPENFDA_API_TOKEN}
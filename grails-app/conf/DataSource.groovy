// MySQL
def fileloc = ['../UserConfig.groovy', 'webapps/ROOT/Jenkins.groovy'].grep { new File(it).exists() }.first();
def dbConfig = new ConfigSlurper(grailsSettings.grailsEnv).parse(new File(fileloc).toURI().toURL())

dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
// MySQL
/*environments {
	development {
		dataSource {
			dbCreate = "update"
			url = 'jdbc:mysql://<URL>/<DB>?useUnicode=true&autoReconnect=true'
		}
	}
	test {
		dataSource {
			dbCreate = "update"
			url = 'jdbc:mysql://<URL>/<DB>?useUnicode=true&autoReconnect=true'
		}
	}
	production {
		dataSource {
			dbCreate = "update"
			url = 'jdbc:mysql://<URL>/<DB>?useUnicode=true&autoReconnect=true'
		}
	}
}*/
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
    test {
        dataSource {
			dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
			url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
		}
    }
    production {
		dataSource {
			dbCreate = "create-drop"
			pooled = true
			driverClassName = "com.mysql.jdbc.Driver"
			dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
			url = dbConfig.rds.url
			username=dbConfig.rds.username
			password=dbConfig.rds.password 
	
			properties {
				maxActive = 50
				maxIdle = 25
				minIdle = 1
				initialSize = 1
		   
				numTestsPerEvictionRun = 3
				maxWait = 10000
		   
				testOnBorrow = true
				testWhileIdle = true
				testOnReturn = true
		   
				validationQuery = "select now()"
		   
				minEvictableIdleTimeMillis = 1000 * 60 * 5
				timeBetweenEvictionRunsMillis = 1000 * 60 * 5
			 }
		}
    }
}

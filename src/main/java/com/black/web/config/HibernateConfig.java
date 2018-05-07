package com.black.web.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
public class HibernateConfig {

	@Value("${spring.hibernate.dialect}")
	private String HIBERNATE_DIALECT;
	  
	@Value("${spring.hibernate.show_sql}")
	private String HIBERNATE_SHOW_SQL;
	  
	@Value("${spring.hibernate.hbm2ddl.auto}")
	private String HIBERNATE_HBM2DDL_AUTO;

	@Value("${spring.entitymanager.packagesToScan}")
	private String ENTITYMANAGER_PACKAGES_TO_SCAN;
	  
	@Bean
	@Autowired
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
	  LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
	  sessionFactoryBean.setDataSource(dataSource);
	  sessionFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
	  Properties hibernateProperties = new Properties();
	  hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
	  hibernateProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
	  hibernateProperties.setProperty("hibernate.current_session_context_class", "org.springframework.orm.hibernate5.SpringSessionContext");
	  hibernateProperties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
	  sessionFactoryBean.setHibernateProperties(hibernateProperties);
	    
	  return sessionFactoryBean;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
	  HibernateTransactionManager transactionManager = 
	      new HibernateTransactionManager();
	  transactionManager.setSessionFactory(sessionFactory);
	  return transactionManager;
	}
    
}

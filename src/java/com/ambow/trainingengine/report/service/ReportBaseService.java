package com.ambow.trainingengine.report.service;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.ambow.core.dao.HibernateEntityDao;

@SuppressWarnings("unchecked")
public class ReportBaseService extends HibernateEntityDao{
	private SimpleJdbcTemplate jdbcTemplate ;

	public SimpleJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}

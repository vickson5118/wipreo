package com.wipreo.factory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.wipreo.utils.Constants;

@WebListener
public class InitDaoFactory implements ServletContextListener {

	private DaoFactory factory;

	@Override
	public void contextInitialized(final ServletContextEvent event) {

		final ServletContext context = event.getServletContext();

		try {
			this.factory = DaoFactory.getInstance();
			context.setAttribute(Constants.ATT_FACTORY, this.factory);
		} catch (final Exception e) {
			e.printStackTrace();
		}

	}
}

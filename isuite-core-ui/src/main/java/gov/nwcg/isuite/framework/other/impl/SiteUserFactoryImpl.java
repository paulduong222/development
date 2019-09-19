/**
 * 
 */
package gov.nwcg.isuite.framework.other.impl;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.other.SiteUserFactory;
import gov.nwcg.isuite.framework.other.ValidityStrategy;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author doug
 * 
 */
public class SiteUserFactoryImpl implements SiteUserFactory {

	private ValidityStrategy loginNameStrategy;

	private ValidityStrategy passwordStrategy;

	private ValidityStrategy nameStrategy;

	private static final Logger log = Logger.getLogger(SiteUserFactoryImpl.class);

	/**
	 * Constructor
	 * 
	 * @param loginNameStrategy
	 * 				ValidityStrategy used to validate loginNames
	 * @param passwordStrategy
	 * 				ValidityStrategy used to validate passwords
	 * @param nameStrategy
	 * 				ValidityStrategy used to validate first and last names
	 * 
	 * @important None of these params can be null.
	 */
	public SiteUserFactoryImpl(ValidityStrategy loginNameStrategy,
			ValidityStrategy passwordStrategy, ValidityStrategy nameStrategy) {
		setLoginNameStrategy(loginNameStrategy);
		setPasswordStrategy(passwordStrategy);
		setNameStrategy(nameStrategy);
	}

	/*
	 * Default constructor
	 */
	SiteUserFactoryImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.site.SiteUserFactory#createUser(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public User createUser(String loginName, String password,
			String firstName, String lastName) throws ValidationException {
		Map<Enum<?>, Object[]> errors = new HashMap<Enum<?>, Object[]>();

		try {
			getLoginNameStrategy().validate(loginName);
		} catch (ValidationException e) {
			errors.putAll(e.getErrors());
		}
		try {
			getPasswordStrategy().validate(password);

		} catch (ValidationException e) {
		   errors.putAll(e.getErrors());
		}

		try {
			getNameStrategy().validate(firstName);
		} catch (ValidationException e) {
		   errors.putAll(e.getErrors());
		}
		try {
			getNameStrategy().validate(lastName);
		} catch (ValidationException e) {
		   errors.putAll(e.getErrors());
		}

		if (errors.size() != 0) {
			throw new ValidationException("could not create user", errors);
		}

		return null; //new UserImpl(loginName, password, firstName, lastName);
	}

	/**
	 * Accessor for loginNameStrategy
	 * 
	 * @return the loginNameStrategy, will not be null
	 * @see #setLoginNameStrategy(ValidityStrategy)
	 */
	public final ValidityStrategy getLoginNameStrategy() {
		return loginNameStrategy;
	}

	/**
	 * Accessor for LoginNameStrategy
	 * 
	 * @param loginNameStrategy
	 *            the loginNameStrategy to set, can not be null
	 * @see #getLoginNameStrategy()
	 */
	final void setLoginNameStrategy(ValidityStrategy loginNameStrategy) {
		if (loginNameStrategy == null) {
			throw new IllegalArgumentException(
					"loginNameStrategy can not be null");
		}
		this.loginNameStrategy = loginNameStrategy;
	}

	/**
	 * Accessor for passwordStrategy.
	 * 
	 * @return the passwordStrategy will not be null
	 * @see #setPasswordStrategy(ValidityStrategy)
	 */
	public final ValidityStrategy getPasswordStrategy() {
		return passwordStrategy;
	}

	/**
	 * Accessor for passwordStrategy.
	 * 
	 * @param passwordStrategy
	 *            the passwordStrategy to set, can not be null
	 * @see #getPasswordStrategy()
	 */
	final void setPasswordStrategy(ValidityStrategy passwordStrategy) {
		if (passwordStrategy == null) {
			throw new IllegalArgumentException(
					"passwordStrategy can not be null");
		}
		this.passwordStrategy = passwordStrategy;
	}

	/**
	 * Accessor for NameStrategy
	 * 
	 * @return the nameStrategy, will not be null
	 * @see #setNameStrategy(ValidityStrategy)
	 */
	public final ValidityStrategy getNameStrategy() {
		return nameStrategy;
	}

	/**
	 * Accessor for NameStrategy
	 * 
	 * @param nameStrategy
	 *            the nameStrategy to set, can not be null
	 * @see #getNameStrategy()
	 */
	final void setNameStrategy(ValidityStrategy nameStrategy) {
		log.debug("Entering SiteUserFactoryImpl : setNameStrategy(nameStrategy)");
		if (nameStrategy == null) {
			throw new IllegalArgumentException("nameStrategy can not be null");
		}
		this.nameStrategy = nameStrategy;
	}

}

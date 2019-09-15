/*
 * AbstractTest.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package utilities;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import security.LoginService;
import utilities.internal.EclipseConsole;

public abstract class AbstractTest {

	// Supporting services --------------------------------

	@Autowired
	private LoginService						loginService;
	@Autowired
	private JpaTransactionManager				transactionManager;

	// Internal state -------------------------------------

	private final DefaultTransactionDefinition	transactionDefinition;
	private TransactionStatus					currentTransaction;
	private final Properties					entityMap;


	// Constructor ----------------------------------------

	public AbstractTest() {
		EclipseConsole.fix();
		LogManager.getLogger("org.hibernate").setLevel(Level.OFF);

		this.transactionDefinition = new DefaultTransactionDefinition();
		this.transactionDefinition.setName("TestTransaction");
		this.transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

		try (InputStream stream = new FileInputStream(DatabaseConfig.entityMapFilename)) {
			this.entityMap = new Properties();
			this.entityMap.load(stream);
		} catch (final Throwable oops) {
			throw new RuntimeException(oops);
		}
	}
	// Set up and tear down -------------------------------

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	// Supporting methods ---------------------------------

	protected void authenticate(final String username) {
		UserDetails userDetails;
		TestingAuthenticationToken authenticationToken;
		SecurityContext context;

		if (username == null)
			authenticationToken = null;
		else {
			userDetails = this.loginService.loadUserByUsername(username);
			authenticationToken = new TestingAuthenticationToken(userDetails, null);
		}

		context = SecurityContextHolder.getContext();
		context.setAuthentication(authenticationToken);
	}

	protected void unauthenticate() {
		this.authenticate(null);
	}

	protected void checkExceptions(final Class<?> expected, final Class<?> caught) {
		if (expected != null && caught == null)
			throw new RuntimeException(expected.getName() + " was expected");
		else if (expected == null && caught != null)
			throw new RuntimeException(caught.getName() + " was unexpected");
		else if (expected != null && caught != null && !expected.equals(caught))
			throw new RuntimeException(expected.getName() + " was expected, but " + caught.getName() + " was thrown");
	}

	protected void startTransaction() {
		this.currentTransaction = this.transactionManager.getTransaction(this.transactionDefinition);
	}

	protected void commitTransaction() {
		this.transactionManager.commit(this.currentTransaction);
	}

	protected void rollbackTransaction() {
		this.transactionManager.rollback(this.currentTransaction);
	}

	protected void flushTransaction() {
		this.currentTransaction.flush();
	}

	protected boolean existsEntity(final String beanName) {
		assert beanName != null && beanName.matches("^[A-Za-z0-9\\-]+$");

		boolean result;

		result = this.entityMap.containsKey(beanName);

		return result;
	}

	protected int getEntityId(final String beanName) {
		assert beanName != null && beanName.matches("^[A-Za-z0-9\\-]+$");
		assert this.existsEntity(beanName);

		int result;
		String id;

		id = (String) this.entityMap.get(beanName);
		result = Integer.valueOf(id);

		return result;
	}

	public int sumatorioPrimerosNumerosImpares(int numDeImpares){
		int res = 0;
		List<Integer> numerosImpares = new ArrayList<>();
		for(int i=1; numerosImpares.size()==numDeImpares; i++){
			if(i%2!=0){
				res += i;
				numerosImpares.add(i);
			}
		}
		return res;
	}

	public static void metodoCozitah(){

		int pos = 0;
		int nPos = 0;
		int neg = 0;
		int nNeg = 0;
		int zeros = 0;

		for (int i = 0; i < 10; i++){
			Scanner a = new Scanner(System.in);
			int b = a.nextInt();
			if (b > 0) {
				pos += b;
				nPos++;
			} else if (b < 0) {
				neg += -b;
				nNeg ++;
			} else
				zeros++;
		}

		System.out.println("la media de los numeros positivos es: " + (double) pos/nPos);
		System.out.println("la media de los numeros negativos es: " + (double) neg/nNeg);
		System.out.println("el numero de ceros es: " + zeros);

	}

}

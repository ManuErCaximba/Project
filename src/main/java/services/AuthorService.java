
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import domain.Actor;
import domain.Paper;
import domain.Submission;
import forms.AuthorForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.AuthorRepository;
import security.Authority;
import security.UserAccount;
import domain.Author;

@Service
@Transactional
public class AuthorService {

	@Autowired
	private AuthorRepository	authorRepository;

	@Autowired
	private ActorService actorService;

	@Autowired
	private SubmissionService submissionService;

	@Autowired
	private Validator validator;

	public Author create() {
		Author author;
		final Authority auth;
		final UserAccount userAccount;
		final Collection<Authority> authorities;

		author = new Author();
		userAccount = new UserAccount();
		auth = new Authority();
		authorities = new ArrayList<>();

		auth.setAuthority(Authority.AUTHOR);
		authorities.add(auth);
		userAccount.setAuthorities(authorities);
		author.setUserAccount(userAccount);

		return author;
	}

	public Collection<Author> findAll() {
		Collection<Author> res;
		res = this.authorRepository.findAll();
		return res;
	}

	public Author findOne(final int authorId) {
		Assert.isTrue(authorId != 0);
		final Author res = this.authorRepository.findOne(authorId);
		Assert.notNull(res);
		return res;
	}

	public Author save(final Author author) {
		Assert.notNull(author);
		Author result;
		if (author.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String res = encoder.encodePassword(author.getUserAccount().getPassword(), null);
			author.getUserAccount().setPassword(res);
		}
		result = this.authorRepository.save(author);
		return result;
	}

	public void delete(final Author s) {

		final Actor actor = this.actorService.getActorLogged();
		Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("AUTHOR"));
		Assert.notNull(s);
		Assert.isTrue(actor.getId() != 0);

		this.authorRepository.delete(s);
	}

	public Author reconstruct(final Author s, final BindingResult binding) {

		Author result;
		if (s.getId() == 0) {
			this.validator.validate(s, binding);
			result = s;
		} else {
			result = this.authorRepository.findOne(s.getId());

			result.setName(s.getName());
			result.setPhoto(s.getPhoto());
			result.setPhoneNumber(s.getPhoneNumber());
			result.setEmail(s.getEmail());
			result.setAddress(s.getAddress());
			result.setSurname(s.getSurname());
			result.setMiddleName(s.getMiddleName());
			result.setScore(s.getScore());

			this.validator.validate(s, binding);
		}
		return result;
	}

	//Objeto formulario
	public Author reconstruct(final AuthorForm s, final BindingResult binding) {

		final Author result = this.create();
		result.setAddress(s.getAddress());
		result.setEmail(s.getEmail());
		result.setId(s.getId());
		result.setName(s.getName());
		result.setPhoneNumber(s.getPhoneNumber());
		result.setPhoto(s.getPhoto());
		result.setSurname(s.getSurname());
		result.getUserAccount().setPassword(s.getPassword());
		result.getUserAccount().setUsername(s.getUsername());
		result.setVersion(s.getVersion());
		result.setMiddleName(s.getMiddleName());

		this.validator.validate(result, binding);
		return result;
	}

	//Other methods
	public Collection<Paper> getCameraReadyPapersFromAuthor(Author author){
		Collection<Paper> res = new ArrayList<>();
		Collection<Submission> submissions = this.submissionService.getSubmissionsAcceptedAndCameraReadyByAuthor(author.getId());

		for (Submission s : submissions){
			if(s.getCameraReadyPaper() != null){
				res.add(s.getCameraReadyPaper());
			}
		}

		return res;
	}

}

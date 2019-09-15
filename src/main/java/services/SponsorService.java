
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import domain.Actor;
import forms.SponsorForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.SponsorRepository;
import security.Authority;
import security.UserAccount;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	@Autowired
	private SponsorRepository	sponsorRepository;

	@Autowired
	private ActorService actorService;

	@Autowired
	private Validator validator;


	public Collection<Sponsor> findAll() {
		Collection<Sponsor> res;
		res = this.sponsorRepository.findAll();
		return res;
	}

	public Sponsor findOne(final int sponsorId) {
		Assert.isTrue(sponsorId != 0);
		final Sponsor res = this.sponsorRepository.findOne(sponsorId);
		Assert.notNull(res);
		return res;
	}

	public Sponsor create() {
		Sponsor sponsor;
		final Authority auth;
		final UserAccount userAccount;
		final Collection<Authority> authorities;

		sponsor = new Sponsor();
		userAccount = new UserAccount();
		auth = new Authority();
		authorities = new ArrayList<>();

		auth.setAuthority(Authority.SPONSOR);
		authorities.add(auth);
		userAccount.setAuthorities(authorities);
		sponsor.setUserAccount(userAccount);

		return sponsor;
	}

	public Sponsor save(final Sponsor sponsor) {
		Assert.notNull(sponsor);
		Sponsor result;
		if (sponsor.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String res = encoder.encodePassword(sponsor.getUserAccount().getPassword(), null);
			sponsor.getUserAccount().setPassword(res);
		}
		result = this.sponsorRepository.save(sponsor);
		return result;
	}

	public void delete(final Sponsor s) {

		final Actor actor = this.actorService.getActorLogged();
		Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("SPONSOR"));
		Assert.notNull(s);
		Assert.isTrue(actor.getId() != 0);

		this.sponsorRepository.delete(s);
	}

	public Sponsor reconstruct(final Sponsor s, final BindingResult binding) {

		Sponsor result;
		if (s.getId() == 0) {
			this.validator.validate(s, binding);
			result = s;
		} else {
			result = this.sponsorRepository.findOne(s.getId());

			result.setName(s.getName());
			result.setPhoto(s.getPhoto());
			result.setPhoneNumber(s.getPhoneNumber());
			result.setEmail(s.getEmail());
			result.setAddress(s.getAddress());
			result.setSurname(s.getSurname());

			this.validator.validate(s, binding);
		}
		return result;
	}

	//Objeto formulario
	public Sponsor reconstruct(final SponsorForm s, final BindingResult binding) {

		final Sponsor result = this.create();
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
}

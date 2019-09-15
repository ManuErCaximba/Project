package services;

import domain.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.ConfigurationRepository;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class ConfigurationService {

    //Managed Repositories
    @Autowired
    private ConfigurationRepository configurationRepository;

    //Supporting services
    @Autowired
    private Validator validator;

    //CRUD Methods
    public Configuration findOne(int id){
        return this.configurationRepository.findOne(id);
    }

    public Collection<Configuration> findAll(){
        return this.configurationRepository.findAll();
    }

    public Configuration save(Configuration configuration){
        Configuration result;

        Assert.notNull(configuration);

        result = this.configurationRepository.save(configuration);

        return result;
    }

    //Other methods
    public Configuration reconstruct(Configuration configuration, BindingResult binding){
        Configuration result;

        result = configuration;

        this.validator.validate(result, binding);

        return result;
    }
}

package karol.train_waybill.mapper;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import karol.train_waybill.CompanyView;
import karol.train_waybill.database.Company;
import karol.train_waybill.repository.CompanyRepository;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") //, uses = {ObjectIdMapper.class})
public abstract class CompanyViewMapper {
	
	@Autowired
    private CompanyRepository companyRepo;

    public abstract CompanyView toCompanyView(Company company);

    public abstract List<CompanyView> toCompanyView(List<Company> companies);

    public CompanyView toCompanyViewById(int id) {
    	Company company = companyRepo.findById(id).get();
        if (company == null) {
            return null;
        }
        return toCompanyView(company);
    }
}

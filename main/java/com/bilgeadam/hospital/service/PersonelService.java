package com.bilgeadam.hospital.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bilgeadam.hospital.entity.Personel;
import com.bilgeadam.hospital.repo.PersonelRepo;

@Service
public class PersonelService {

	@Autowired
	private PersonelRepo personelRepo;

	public List<Personel> getAll() {

		return personelRepo.findAll();

	}

	public void deleteById(Long id) {

		personelRepo.deleteById(id);

	}

	public Personel getById(Long id) {

		return personelRepo.findById(id).get();

	}

	public Personel add(Personel personel) {

		return personelRepo.save(personel);
	}

	public void save(Personel personel) {

		personelRepo.save(personel);

	}

	public void update(Long id, @Valid Personel personel) {

		Personel existPersonel = getById(id);

		existPersonel.setImage(personel.getImage());
		existPersonel.setFirstName(personel.getFirstName());
		existPersonel.setLastName(personel.getLastName());
		existPersonel.setBirthdate(personel.getBirthdate());
		existPersonel.setMobile(personel.getMobile());
		existPersonel.setEmail(personel.getEmail());
		existPersonel.setGender(personel.getGender());
		existPersonel.setTitle(personel.getTitle());
		existPersonel.setDepartment(personel.getDepartment());
		existPersonel.setStartDate(personel.getStartDate());
		existPersonel.setEndDate(personel.getEndDate());		
		
		personelRepo.save(existPersonel);

	}

	public List<Personel> personels(int pageNo, int recordCount) {

		Pageable pageable = PageRequest.of(pageNo, recordCount);

		return personelRepo.findAll(pageable).get().toList();
	}

	public List<Personel> personels(int pageNo, int recordCount, String sortBy) {
		Pageable pageable = PageRequest.of(pageNo, recordCount, Sort.by(sortBy));

		return personelRepo.findAll(pageable).get().toList();
	}

	public Page<Personel> personels(int pageNo, int recordCount, String sortBy, String direction) {
		Pageable pageable = PageRequest.of(pageNo - 1, recordCount,
				Sort.by(("desc".equals(direction) ? Direction.DESC : Direction.ASC), sortBy));

		return personelRepo.findAll(pageable);
	}

	public Page<Personel> personels(Integer pageNo, Integer recordCount, String sortBy, String direction, String keyword) {
		Pageable pageable = PageRequest.of(pageNo - 1, recordCount,
				Sort.by(("desc".equals(direction) ? Direction.DESC : Direction.ASC), sortBy));

		if (keyword.isEmpty()) {
			return personelRepo.findAll(pageable);
		} else {
			// return personelRepo.findAllByName(keyword, pageable);
			// keyword = "%" + keyword + "%";
			// return personelRepo.findAllByNameLike(keyword, pageable);

			return personelRepo.findAllByFirstNameContainingIgnoreCase(keyword, pageable);
		}
	}

//	public PersonelService(PersonelRepo personelRepo) {
//		this.personelRepo = personelRepo;
//	}

}

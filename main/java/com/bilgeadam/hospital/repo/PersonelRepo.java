package com.bilgeadam.hospital.repo;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bilgeadam.hospital.entity.Personel;
@Repository
public interface PersonelRepo extends JpaRepository<Personel, Long> {

	Page<Personel> findAllByFirstName(String keyword, Pageable pageable);

	Page<Personel> findAllByFirstNameLike(String keyword, Pageable pageable);

	Page<Personel> findAllByFirstNameContainingIgnoreCase(String keyword, Pageable pageable);
}

package com.ee172.team2.nemo.service;

import com.ee172.team2.nemo.model.WeddingCouple;
import com.ee172.team2.nemo.model.WeddingGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ee172.team2.nemo.repository.WeddingGuestRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

@Service
public class WeddingGuestService {

	@Autowired
	private WeddingGuestRepository weddingGuestDao;

	public List<WeddingGuest> findAll() {
		return weddingGuestDao.findAll();
	}

	public Optional<WeddingGuest> findById(Integer id) {
		return weddingGuestDao.findById(id);
	}

	public WeddingGuest save(WeddingGuest guest) {
		return weddingGuestDao.save(guest);
	}

	public List<WeddingGuest> findByWeddingId(Integer id) {
		WeddingCouple weddingCouple = new WeddingCouple();
		weddingCouple.setWeddingId(id);
		return weddingGuestDao.findByWeddingCouple(weddingCouple);
	}

	public void delete(Integer id) {
		weddingGuestDao.deleteById(id);
	}

	/**
	 * 条件查询
	 * 
	 * @param weddingId
	 * @param guestName
	 * @return
	 */
	public List<WeddingGuest> findByCondition(String weddingId, String guestName) {
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("guestName",
				ExampleMatcher.GenericPropertyMatcher::contains);
		WeddingGuest guest = new WeddingGuest();
		WeddingCouple couple = new WeddingCouple();
		couple.setWeddingId(Integer.parseInt(weddingId));
		guest.setWeddingCouple(couple);
		guest.setGuestName(guestName);
		Example<WeddingGuest> guestExample = Example.of(guest, exampleMatcher);
		return weddingGuestDao.findAll(guestExample);
	}

	// 任何其他必要的方法
}

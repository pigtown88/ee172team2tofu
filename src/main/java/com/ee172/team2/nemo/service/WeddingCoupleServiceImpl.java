package com.ee172.team2.nemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ee172.team2.nemo.model.WeddingCouple;
import com.ee172.team2.nemo.repository.WeddingCoupleRepository;

@Service
public class WeddingCoupleServiceImpl implements WeddingCoupleService{

	@Autowired
	private WeddingCoupleRepository weddingCoupleDao;
	

	// 得到weddingCouple實例
	public List<WeddingCouple> findAll() {
		return  weddingCoupleDao.findAll();
	}

	// 根據WEB-ID查找
	public Optional<WeddingCouple> findById(Integer id) {
		return weddingCoupleDao.findById(id);
	}

	// 儲存
	public WeddingCouple save(WeddingCouple couple) {
		return weddingCoupleDao.save(couple);
	}
	

	//删除
    public void delete(Integer id) {
    	weddingCoupleDao.deleteById(id);
    }
}




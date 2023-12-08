package com.ee172.team2.nemo.service;




import com.ee172.team2.nemo.model.WeddingCouple;
import com.ee172.team2.nemo.model.WeddingGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ee172.team2.nemo.model.WeddingGuest;
import com.ee172.team2.nemo.repository.WeddingCoupleRepository;
import com.ee172.team2.nemo.repository.WeddingGuestRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WeddingGuestService {

    @Autowired
    private WeddingGuestRepository weddingGuestDao;
    @Autowired
    private WeddingCoupleRepository weddingCoupleDao;

    public List<WeddingGuest> findAll() {
        return weddingGuestDao.findAll();
    }

    public Optional<WeddingGuest> findById(Integer id) {
        return weddingGuestDao.findById(id);
    }

    public WeddingGuest save(WeddingGuest guest) {
        return weddingGuestDao.save(guest);
    }
    public List<WeddingGuest> findByWeddingId(Integer id){
    	WeddingCouple weddingCouple = new WeddingCouple();
    	weddingCouple.setWeddingId(id);
    	return weddingGuestDao.findByWeddingCouple(weddingCouple);
    }

    // 任何其他必要的方法
}

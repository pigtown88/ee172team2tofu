package com.ee172.team2.lpt.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ee172.team2.liwen.model.Member;
import com.ee172.team2.liwen.repository.MemberRepository;
import com.ee172.team2.lpt.DTO.ReserveDTO;
import com.ee172.team2.lpt.model.Arena;
import com.ee172.team2.lpt.model.Reserve;
import com.ee172.team2.lpt.repository.ArenaRepository;
import com.ee172.team2.lpt.repository.ReserveRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ReserveService {

	@Autowired
	private ReserveRepository reserveDAO;
	
	@Autowired	
	private ArenaRepository arenaDAO;
	
	@Autowired
	private MemberRepository memberDAO;
	

	

	public Reserve findByReserveId(Integer id) {
		Optional<Reserve> optional = reserveDAO.findById(id);

		if (optional.isPresent()) {
			return optional.get();
		}

		return null;

	}

	// 由memberId拿到會員所有場地預約並轉成DTO
	public List<ReserveDTO> findReserveByMemberId(Integer memberId) {
		List<Reserve> reserve = reserveDAO.findByMemberMemberId(memberId);

		if (reserve != null && !reserve.isEmpty()) {

			List<ReserveDTO> reserveDTOList = new ArrayList<>();

			for (Reserve r : reserve) {
				ReserveDTO reserveDTO = new ReserveDTO();
				reserveDTO.setReserveId(String.valueOf(r.getReserveId()));
				reserveDTO.setReserveDayStart(String.valueOf(r.getReserveDayStart()));
				reserveDTO.setReserveDayEnd(String.valueOf(r.getReserveDayEnd()));
				reserveDTO.setArenaId(String.valueOf(r.getArena().getArenaId()));
				reserveDTO.setMemberId(String.valueOf(r.getMember().getMemberId()));

				reserveDTOList.add(reserveDTO);
			}
			return reserveDTOList;
		}
		return null;
	}

	// 應該沒用
	public List<Reserve> findAllReserves() {
		return reserveDAO.findAll();
	}

	// DTO轉物件後預約場地
	public void addReserve(ReserveDTO reserveDTO) throws ParseException {

		Reserve reserve = new Reserve();

//		reserve.setReserveId(Integer.valueOf(reserveDTO.getReserveId()));
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		reserve.setReserveDayStart(dateFormat.parse(reserveDTO.getReserveDayStart()));
		reserve.setReserveDayEnd(dateFormat.parse(reserveDTO.getReserveDayEnd()));
		
		Arena arena = arenaDAO.findById(Integer.valueOf(reserveDTO.getArenaId())).orElseThrow(() -> new EntityNotFoundException("Arena not found"));
		reserve.setArena(arena);
		
		Member member = memberDAO.findById(Integer.valueOf(reserveDTO.getMemberId())).orElseThrow(() -> new EntityNotFoundException("Member not found"));
		reserve.setMember(member);
		
		reserveDAO.save(reserve);
	}

	public void deleteByReserveId(Integer id) {
		reserveDAO.deleteById(id);
	}

	// 修改場地預約，需新增檢查修改邏輯
	public void updateReserve(Reserve reserve) {
		reserveDAO.save(reserve);
	}

}

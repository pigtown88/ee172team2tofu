
package com.ee172.team2.lpt.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ee172.team2.lpt.model.Activity;

import com.ee172.team2.lpt.repository.ActivityRepository;

import com.ee172.team2.liwen.model.Host;
import com.ee172.team2.liwen.repository.HostRepository;
import com.ee172.team2.lpt.DTO.ActivityDTO;
import com.ee172.team2.lpt.model.Reserve;
import com.ee172.team2.lpt.repository.ReserveRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ActivityService {

	@Autowired
	private ActivityRepository activityDAO;

	@Autowired
	private ReserveRepository reserveDAO;

	@Autowired
	private HostRepository hostDAO;

	public Activity findByActivityId(Integer id) {
		Optional<Activity> optional = activityDAO.findById(id);

		if (optional.isPresent()) {
			return optional.get();
		}

		return null;

	}

	public List<Activity> findAllActivities() {
		return activityDAO.findAll();
	}

	// 由reserveId拿到對於此場地預約之所有舉辦活動並轉DTO
	public List<ActivityDTO> findActivityByReserveId(Integer reserveId) {

		List<Activity> activity = activityDAO.findByReserveReserveId(reserveId);

		if (activity != null && !activity.isEmpty()) {

			List<ActivityDTO> activityDTOs = new ArrayList<>();

			for (Activity a : activity) {
				ActivityDTO activityDTO = new ActivityDTO();
				activityDTO.setActivityId(String.valueOf(a.getActivityId()));
				activityDTO.setActivityName(a.getActivityName());
				activityDTO.setActivityDayStart(String.valueOf(a.getActivityDayStart()));
				activityDTO.setActivityDayEnd(String.valueOf(a.getActivityDayEnd()));
				activityDTO.setActivityPrice(String.valueOf(a.getActivityPrice()));
				activityDTO.setActivityType(a.getActivityType());
				activityDTO.setActivityIntro(a.getActivityIntro());
				activityDTO.setReserveId(String.valueOf(a.getReserve().getReserveId()));
				activityDTO.setHostId(String.valueOf(a.getHost().getHostId()));

				activityDTOs.add(activityDTO);
			}
			return activityDTOs;
		}
		return null;
	}

	// DTO轉物件後舉辦活動
	public void addActivity(ActivityDTO activityDTO) throws ParseException {
		Activity activity = new Activity();

//		activity.setActivityId(Integer.valueOf(activityDTO.getActivityId()));
		activity.setActivityName(activityDTO.getActivityName());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		activity.setActivityDayStart(new Timestamp(dateFormat.parse(activityDTO.getActivityDayStart()).getTime()));
		activity.setActivityDayEnd(new Timestamp(dateFormat.parse(activityDTO.getActivityDayEnd()).getTime()));

		activity.setActivityPrice(Integer.valueOf(activityDTO.getActivityPrice()));
		activity.setActivityType(activityDTO.getActivityType());
		activity.setActivityIntro(activityDTO.getActivityIntro());

		Reserve reserve = reserveDAO.findById(Integer.valueOf(activityDTO.getReserveId()))
				.orElseThrow(() -> new EntityNotFoundException("Reserve not found"));
		activity.setReserve(reserve);

		Host host = hostDAO.findById(Integer.valueOf(activityDTO.getHostId()))
				.orElseThrow(() -> new EntityNotFoundException("Host not found"));
		activity.setHost(host);

		activityDAO.save(activity);
	}

	public void deleteByActivityId(Integer id) {
		activityDAO.deleteById(id);
	}

	// 修改活動，需新增檢查修改邏輯
	public void updateActivity(Activity activity) {
		activityDAO.save(activity);
	}

	public List<Activity> findActivity() {

		return activityDAO.findTheLatesActivity();
	}

}

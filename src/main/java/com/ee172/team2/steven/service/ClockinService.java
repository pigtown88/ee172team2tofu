package com.ee172.team2.steven.service;

import com.ee172.team2.steven.DTO.ApplyDTO;
import com.ee172.team2.steven.DTO.ClockinDTO;
import com.ee172.team2.steven.DTO.ClockinSearchDTO;
import com.ee172.team2.steven.DTO.ClockinSpecifications;
import com.ee172.team2.steven.handler.BusinessException;
import com.ee172.team2.steven.model.*;
import com.ee172.team2.steven.repository.ClockinRepository;
import com.ee172.team2.steven.repository.EmployeeRepository;
import com.ee172.team2.steven.repository.NoticeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClockinService {

    @Autowired
    private ClockinRepository clockinDAO;

    @Autowired
    private EmployeeRepository empDAO;

    @Autowired
    private NoticeRepository noticeDAO;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ClockinRepository clockinRepository;


    public Page<ClockinDTO> searchClockinRecords(ClockinSearchDTO searchDTO, Integer pageNumber) {
        long StartTime = System.currentTimeMillis();

        Pageable pageable = PageRequest.of(pageNumber - 1, 5, Sort.Direction.DESC, "day");
        Specification<Clockin> spec = Specification.where(
                ClockinSpecifications.hasEmpName(searchDTO.getEmpName())
                        .and(ClockinSpecifications.hasDepartment(searchDTO.getDepartment()))
                        .and(ClockinSpecifications.isLate(searchDTO.getLate()))
                        .and(ClockinSpecifications.earlyLeave(searchDTO.getEarlyLeave()))
                        .and(ClockinSpecifications.betweenDates(searchDTO.getStartDate(), searchDTO.getEndDate()))
        );
        System.out.println("spec: =======" + spec);
        Page<Clockin> searchPage = clockinRepository.findAll(spec, pageable);
        System.out.println("searchPage: =======" + searchPage.getContent());
        Page<ClockinDTO> dtoPage = searchPage.map(this::ConverseClockinToClockinDTO);

        long EndTime = System.currentTimeMillis();
        System.out.println("searchClockinRecords: =======" + (EndTime - StartTime));
        return dtoPage;

    }



    public Page<ClockinDTO> findByPage(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, Sort.Direction.DESC, "clockinTime");
        Page<Clockin> clockinPage = clockinDAO.findAll(pageable);


        Page<ClockinDTO> dtoPage = clockinPage.map(this::ConverseClockinToClockinDTO);

        return dtoPage;
    }


public  Page<ClockinDTO> findByPageAndEmpId(Integer empId,Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, Sort.Direction.DESC, "clockinTime");
        Page<Clockin> clockinPage = clockinDAO.findByEmployee_EmpId(empId,pageable);
        return clockinPage.map(this::ConverseClockinToClockinDTO);




}








        //上班打卡
    public Clockin clockIn(Employee employee) {

        Optional<Clockin> existingRecord = clockinDAO.findLatestClockinRecordByEmployeeAndDay(employee, java.sql.Date.valueOf(LocalDate.now()));
        if (existingRecord.isPresent()) {
            // 已存在打卡記錄，不再創建新記錄
            throw new BusinessException("今日已有打卡記錄");
        }

        Clockin clockInRecord = new Clockin();
        clockInRecord.setEmployee(employee);
        clockInRecord.setClockinTime(new Timestamp(System.currentTimeMillis()));
        clockInRecord.setDay(java.sql.Date.valueOf(LocalDate.now()));
        clockInRecord.setClockoutTime(null);


        checkLate(clockInRecord, employee);

        return clockinDAO.save(clockInRecord);
    }

    // 下班打卡
    public Clockin clockOut(Employee employee) {


        LocalDate today = LocalDate.now();
        Optional<Clockin> clockInRecordOptional = clockinDAO.findLatestClockinRecordByEmployeeAndDay(employee, java.sql.Date.valueOf(today));

        Clockin clockInRecord = clockInRecordOptional
                .orElseThrow(() -> new EntityNotFoundException("今日尚未進行上班打卡"));

        if (clockInRecord.getClockoutTime() != null) {
            throw new BusinessException("今日已進行下班打卡");
        }

        clockInRecord.setClockoutTime(new Timestamp(System.currentTimeMillis()));

        checkEarlyLeave(clockInRecord, employee);

        return clockinDAO.save(clockInRecord);
    }



    //驗證遲到

    private void checkLate(Clockin clockInRecord, Employee employee) {
        ScheduleSetting scheduleSetting = employee.getWorkTypes();
        Time shiftStartTime = scheduleSetting.getStartTime();
        LocalDateTime expectedShiftStartTime = LocalDateTime.of(LocalDate.now(), shiftStartTime.toLocalTime());

        LocalDateTime clockInTime = clockInRecord.getClockinTime().toLocalDateTime();

        //判斷是否晚30分鐘
        if (clockInTime.isAfter(expectedShiftStartTime.plusMinutes(30))) {
            clockInRecord.setLate(true);
        } else {
            clockInRecord.setLate(false);
        }
        if (clockInRecord.isLate()) {
            createNoticeForEmployee(employee, "遲到警告", "您於 " + clockInRecord.getClockinTime() + " 遲到。請確保準時上班。");
        }

    }

    //驗證早退
    private void checkEarlyLeave(Clockin clockin, Employee employee) {
        ScheduleSetting scheduleSetting = employee.getWorkTypes();
        Time shiftEndTime = scheduleSetting.getEndTime();
        LocalDateTime expectedShiftEndTime = LocalDateTime.of(LocalDate.now(), shiftEndTime.toLocalTime());

        LocalDateTime clockOutTime = clockin.getClockoutTime().toLocalDateTime();

        // 計算工作時常是否小於9小時(中間一小時休息)
        long hoursWorked = Duration.between(clockin.getClockinTime().toLocalDateTime(), clockOutTime).toHours();
        if (hoursWorked < 9) {
            clockin.setEarlyLeave(true);
        } else {
            clockin.setEarlyLeave(false);
        }
        if (clockin.isEarlyLeave()) {
            createNoticeForEmployee(employee, "早退警告", "您於 " + clockin.getClockoutTime() + " 早退。請確保完整工作時數。");
        }
    }




    private void checkOverTime(Clockin clockin, Employee employee) {
        ScheduleSetting scheduleSetting = employee.getWorkTypes();
        Time shiftEndTime = scheduleSetting.getEndTime();
        LocalDateTime expectedShiftEndTime = LocalDateTime.of(LocalDate.now(), shiftEndTime.toLocalTime());

        LocalDateTime clockOutTime = clockin.getClockoutTime().toLocalDateTime();

        // 計算工作時常是否小於9小時(中間一小時休息)
        long hoursWorked = Duration.between(clockin.getClockinTime().toLocalDateTime(), clockOutTime).toHours();
        if (hoursWorked > 9) {
            clockin.setWorkOvertime((int) (hoursWorked-9));
        } else {
            clockin.setWorkOvertime(0);
        }
        if (clockin.getWorkOvertime()>0){
            createNoticeForEmployee(employee, "加班時數", "您於 " + clockin.getClockoutTime() + " 加班。加班時數為"+clockin.getWorkOvertime()+"小時");
        }
    }



    //通知創建
    private void createNoticeForEmployee(Employee employee, String title, String message) {
        Notice notice = new Notice();
        notice.setEmployee(employee);
        notice.setTitle(title);
        notice.setContext(message);
        notice.setCreateTime(new Timestamp(System.currentTimeMillis()));
        noticeDAO.save(notice);
    }


    public ClockinDTO ConverseClockinToClockinDTO(Clockin clockin){
        ClockinDTO clockinDTO = new ClockinDTO();

        clockinDTO.setEmpName(clockin.getEmployee().getEmpName());
        clockinDTO.setEmpId(String.valueOf(clockin.getEmployee().getEmpId()));
        clockinDTO.setClockinTime(String.valueOf(clockin.getClockinTime()));
        clockinDTO.setClockoutTime(String.valueOf(clockin.getClockoutTime()));
        clockinDTO.setDay(String.valueOf(clockin.getDay()));
        clockinDTO.setDepartment((clockin.getEmployee().getDepartment().getDepName()));
        clockinDTO.setLate(clockin.isLate());
        clockinDTO.setEarlyLeave(clockin.isEarlyLeave());
        return clockinDTO;




    }

    }






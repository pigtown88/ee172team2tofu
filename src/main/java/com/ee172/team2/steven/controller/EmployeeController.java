package com.ee172.team2.steven.controller;

import com.ee172.team2.steven.DTO.*;
import com.ee172.team2.steven.handler.BusinessException;
import com.ee172.team2.steven.model.Department;
import com.ee172.team2.steven.model.Employee;
import com.ee172.team2.steven.model.Position;
import com.ee172.team2.steven.repository.DepartmentRepository;
import com.ee172.team2.steven.repository.EmployeeRepository;
import com.ee172.team2.steven.repository.PositionRepository;
import com.ee172.team2.steven.repository.ScheduleSettingRepository;
import com.ee172.team2.steven.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/backstage/employee")
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository empDAO;

    @Autowired
    private EmployeeService empService;



    @Autowired
    private ScheduleSettingRepository schDAO;

    @Autowired
    private DepartmentRepository departmentDAO;

    @Autowired
    private PositionRepository positionDAO;




    @GetMapping("/getChatJs")
    public  ResponseEntity<List<ChatJsDTO>> getChatJsDTO(){
        List<ChatJsDTO> chatJsDTO = empService.getChatJsDTO();

        return ResponseEntity.ok(chatJsDTO);
    }




    @GetMapping("/info")
    public ResponseEntity<EmployeeDTO> getEmployeeInfo(HttpSession session) {
        EmployeeDTO employeeDTO = (EmployeeDTO) session.getAttribute("employeeDTO");

        if (employeeDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(employeeDTO);
    }





        @GetMapping("/getPhoto")
    public ResponseEntity<String> getEmployeePhoto(HttpSession session) {
        String employeePhotoBase64 = (String) session.getAttribute("employeePhoto");

        if (employeePhotoBase64 == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(employeePhotoBase64);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(empService.getAllEmployees());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(
            @RequestParam String name,
            @RequestParam String department,
            @RequestParam String position) {
        List<Employee> employees = empService.searchEmployees(name, department, position);
        return ResponseEntity.ok(employees);
    }


//    員工自己看詳細資料
    @GetMapping("/EmployeePersonalInfo")
    public ResponseEntity<EmployeePersonalDTO> getEmployeeById2(HttpSession session) {
        EmployeeDTO employeeDTO = (EmployeeDTO) session.getAttribute("employeeDTO");
        if (employeeDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        EmployeePersonalDTO empInfo = empService.getEmployeeById3(employeeDTO.getEmpId());
        return ResponseEntity.ok(empInfo);

    }


//    員工詳細資料
    @GetMapping("/{empId}")
    public ResponseEntity<EmployeeListDTO> getEmployeeById(@PathVariable Integer empId) {
        try {
            EmployeeListDTO employeeListDTO = empService.getEmployeeById2(empId);
            return ResponseEntity.ok(employeeListDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }




    @Transactional
    @Modifying
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Integer id) {
        empService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }


//    @PostMapping("/register")
//    public ResponseEntity<Employee> registerEmployee(@RequestBody Employee employee) {
//        Employee savedEmployee = empService.createNewEmployee(employee);
//        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
//    }

//    註冊
    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(@RequestBody RegisterDTO registrationDTO) {
        try {
            Employee employee = convertDTOToEmployee(registrationDTO);
            Employee savedEmployee = empService.createNewEmployee(employee);
            return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
        } catch (BusinessException ex) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new BusinessException(ex.getMessage()));}
    }






//    員工資料
    @GetMapping("/empListData")
    public Page<EmployeeListDTO> getEmployees(@RequestParam(name = "p", defaultValue = "1") Integer pageNumber) {
        return empService.findByPage2(pageNumber);
    }


//    管理員搜尋會員
    @GetMapping("/searchEmployee")
    public Page<EmployeeListDTO> searchEmployees(
            @RequestParam(name = "p", defaultValue = "1") Integer pageNumber,
            @RequestParam("type") String type,
            @RequestParam("query") String query) {

        return empService.searchEmployees(pageNumber, type, query);
    }



    @GetMapping("/page/all")
    public ResponseEntity<Page<Employee>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employees = empService.getAllEmployees(pageable);
        return ResponseEntity.ok(employees);
    }






//登入
       @PostMapping("/login")
    public String login(HttpServletRequest request, @RequestBody LoginDTO loginDTO, Model model) {
        Employee employee = empService.login(loginDTO.getUsername(), loginDTO.getPassword());
        if (employee != null) {
            EmployeeDTO employeeDTO = convertToEmployeeDTO(employee);
            HttpSession session = request.getSession();
            session.setAttribute("employeeDTO", employeeDTO);

            String employeePhoto = Base64.getEncoder().encodeToString(employee.getEmpPhoto());
            session.setAttribute("employeePhoto", employeePhoto);


            session.setAttribute("employeePhoto", employeePhoto);
            return "redirect:http://localhost:8087/ee172/index";
        } else {
            model.addAttribute("loginError", "Invalid username or password");
            return "login";
        }
    }



//    管理員更新會員資料
    @PutMapping("/employeeInfo/{empId}")
    public ResponseEntity<?> updateEmployee(@PathVariable Integer empId, @RequestBody EmployeeUpdateDTO employeeUpdateDTO) {
        if (!empId.equals(employeeUpdateDTO.getEmpId())) {
            return ResponseEntity.badRequest().body("Path variable empId doesn't match with DTO empId");
        }

        try {
            Employee updatedEmployee = empService.updateEmployee2(employeeUpdateDTO);
            return ResponseEntity.ok(updatedEmployee);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

//    會員更新自己

@PutMapping("/updateMyInfo")
public ResponseEntity<?> updateMyInfo(HttpServletRequest request, @RequestBody EmployeeUpdateDTO employeeUpdateDTO) {
    HttpSession session = request.getSession();
    EmployeeDTO currentEmployee = (EmployeeDTO) session.getAttribute("employeeDTO");


    if (currentEmployee == null || !currentEmployee.getEmpId().equals(employeeUpdateDTO.getEmpId())) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    try {
        Employee updatedEmployee = empService.updateEmployee2(employeeUpdateDTO);
        return ResponseEntity.ok(updatedEmployee);
    } catch (EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}




    private Employee convertDTOToEmployee(RegisterDTO dto) {
        Employee employee = new Employee();
        // 將 DTO 字段設置到 employee 實體
        employee.setEmpAccount(dto.getUserAccount());
        employee.setEmpName(dto.getUsername());
        employee.setEmpPwd(dto.getPassword());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setGender(dto.getGender());
        employee.setAddress(dto.getAddress());
        employee.setPreferType(dto.getPreferType());
        employee.setRole(Integer.valueOf(dto.getRole()));
        employee.setSalary(Integer.valueOf(dto.getSalary()));
        employee.setWorkTypes(schDAO.findById(Integer.valueOf(dto.getWorkTypes()))
                .orElseThrow(() -> new EntityNotFoundException("WorkType not found")));
        Department department = departmentDAO.findById(Integer.valueOf(dto.getDepartment()))
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));
        employee.setDepartment(department);


        Position position = positionDAO.findById(Integer.valueOf(dto.getPosition()))
                .orElseThrow(() -> new EntityNotFoundException("Position not found"));
        employee.setPosition(position);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDay = LocalDate.parse(dto.getBirthdate(), formatter);
        employee.setBirthDate(java.sql.Date.valueOf(birthDay));

        byte[] decodedImage = Base64.getDecoder().decode(dto.getEmpPhoto());
        employee.setEmpPhoto(decodedImage);





        return employee;
    }








    private EmployeeDTO convertToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmpId(employee.getEmpId());
        employeeDTO.setEmpName(employee.getEmpName());
        employeeDTO.setPosition(employee.getPosition() != null ? employee.getPosition().getEmpPos() : null);
        employeeDTO.setDepartment(employee.getDepartment() != null ? employee.getDepartment().getDepName() : null);
        employeeDTO.setRole(employee.getRole());
        return employeeDTO;
    }



}


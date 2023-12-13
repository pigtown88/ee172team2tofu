package com.ee172.team2.steven.service;

import com.ee172.team2.steven.repository.FoodOptionsRepository;
import com.ee172.team2.steven.repository.GroupBuysRepository;
import com.ee172.team2.steven.repository.PurchaseRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupBuyService {

    @Autowired
    private GroupBuysRepository groupBuysRepository;

@Autowired
    private PurchaseRecordsRepository purchaseRecordsRepository;

@Autowired
    private EmployeeService employeeService;

@Autowired
    private FoodOptionsRepository foodOptionsRepository;
















}

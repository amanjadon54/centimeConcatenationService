package com.centime.concatenation.controller;

import com.centime.concatenation.model.occupation.OccupationBulkRequest;
import com.centime.concatenation.service.OccupationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/occupation")
public class OccupationController {

    @Autowired
    OccupationService occupationService;

    @PostMapping("/bulk")
    public Object occupation(@RequestHeader Map<String, String> headers, String logId,
                             @Valid @RequestBody OccupationBulkRequest occupationBulkRequest) {
        return occupationService.saveAll(occupationBulkRequest, logId);
    }

    @GetMapping("/{id}")
    public Object getOccupationById(@RequestHeader Map<String, String> headers, String logId,
                                    @PathVariable("id") Integer id) {
        return occupationService.getEntityById(id, logId);
    }


    @GetMapping("/")
    public Object getAllOccupations(@RequestHeader Map<String, String> headers, String logId) {
        return occupationService.getAllByName(logId);
    }
}

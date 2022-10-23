package com.itgnomes.punditzservice.controller

import com.itgnomes.punditzservice.model.Cycle
import com.itgnomes.punditzservice.model.Exception
import com.itgnomes.punditzservice.service.ExceptionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

@RestController(value="/exceptions")
class ExceptionController(@Autowired private val exceptionService: ExceptionService) {

    @GetMapping("/{cycleNumber}")
    fun get(@PathVariable cycleNumber: Int): Exception {
        return exceptionService.getByCycleNumber(cycleNumber)
    }

    @GetMapping
    fun getAll(): List<Exception> {
        return exceptionService.getAll()
    }

    @PostMapping
    fun create(@RequestBody exception: Exception) {
        exceptionService.insert(exception)
    }

    @PutMapping
    fun update(@RequestBody exception: Exception) {
        exceptionService.update(exception)
    }
}
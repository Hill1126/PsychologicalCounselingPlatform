package org.gdou.controller;

import lombok.extern.slf4j.Slf4j;
import org.gdou.common.result.Result;
import org.gdou.model.dto.PageInfoDto;
import org.gdou.service.impl.FunImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/5/13
 **/
@RestController
@RequestMapping("/funImage")
@Slf4j
@Validated
public class FunImageController {

    @Autowired
    private FunImageService funImageService;

    @RequestMapping(value = "/list")
    public Result listFunImages(PageInfoDto pageInfoDto){
        return funImageService.listFunImages(pageInfoDto);
    }

    @RequestMapping("/{funImageId}")
    public Result getFunImage(@PathVariable Integer funImageId){
        return funImageService.getFunImage(funImageId);
    }

}

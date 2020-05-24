package org.gdou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.gdou.common.constant.CommonDataStatus;
import org.gdou.common.result.Result;
import org.gdou.dao.FunImageMapper;
import org.gdou.model.dto.PageInfoDto;
import org.gdou.model.po.FunImage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/5/12
 **/
@Service
@Slf4j
public class FunImageService {

    public FunImageService(FunImageMapper funImageMapper) {
        this.funImageMapper = funImageMapper;
    }

    private FunImageMapper funImageMapper;

    /**
     * 将封装好的对象插入数据库，同时返回给前端插入后的对象
     * @author: HILL
     * @date: 2020/5/12 23:30
     *
     * @param funImage
     * @return: org.gdou.common.result.Result
    **/
    public Result create(FunImage funImage) {
        funImage.setCreateAt(LocalDateTime.now());
        funImage.setStatus(CommonDataStatus.PUBLIC);

        funImageMapper.insert(funImage);
        return Result.genSuccessResult(funImage);

    }

    public Result update(FunImage funImage) {
        funImageMapper.updateByPrimaryKeySelective(funImage);
        return Result.genSuccessResult();

    }

    public Result deleteFunImage(Integer funImageId) {
        log.info("id为【{}】的趣图被删除",funImageId);
        var funImage = new FunImage();
        funImage.setFunImageId(funImageId);
        funImage.setStatus(CommonDataStatus.DELETE);
        funImageMapper.updateByPrimaryKeySelective(funImage);
        return Result.genSuccessResult();
    }


    public Result listFunImages(PageInfoDto pageInfoDto) {
        PageHelper.startPage(pageInfoDto.getPageNum(),pageInfoDto.getPageSize());
        List<FunImage> funImages = funImageMapper.listFunImages();
        return Result.genSuccessResult(PageInfo.of(funImages));
    }

    public Result getFunImage(Integer funImageId) {
        return Result.genSuccessResult(funImageMapper.selectByPrimaryKey(funImageId));
    }
}

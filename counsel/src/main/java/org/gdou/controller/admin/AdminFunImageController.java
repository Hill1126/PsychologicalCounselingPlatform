package org.gdou.controller.admin;

import org.gdou.common.annotaions.RoleControl;
import org.gdou.common.constant.user.UserType;
import org.gdou.common.result.Result;
import org.gdou.common.result.ResultCode;
import org.gdou.model.dto.PageInfoDto;
import org.gdou.model.dto.article.FunImageDto;
import org.gdou.model.po.FunImage;
import org.gdou.service.impl.BosService;
import org.gdou.service.impl.FunImageService;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * 趣图管理类，用户管理图片的增删改查等
 * @author HILL
 * @version V1.0
 * @date 2020/5/12
 **/
@RestController
@Validated
@RoleControl(userType = UserType.ADMIN)
@RequestMapping("/admin/funImage")
public class AdminFunImageController {

    private BosService bosService;
    private FunImageService funImageService;

    public AdminFunImageController(BosService bosService, FunImageService funImageService) {
        this.bosService = bosService;
        this.funImageService = funImageService;
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public Result createFunImage(@Validated FunImageDto funImageDto) throws IOException {
        FunImage funImage = new FunImage();
        BeanUtils.copyProperties(funImageDto,funImage);
        //上传图片返回图片访问路径
        String imgPath = uploadImg(funImageDto.getImg());
        funImage.setImageUrl(imgPath);
        return funImageService.create(funImage);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result updateFunImage(@NotNull Integer funImageId,FunImageDto funImageDto) throws IOException {
        var funImage = new FunImage();
        BeanUtils.copyProperties(funImageDto,funImage);
        funImage.setFunImageId(funImageId);
        String imgPath = uploadImg(funImageDto.getImg());
        funImage.setImageUrl(imgPath);

        return funImageService.update(funImage);

    }


    @RequestMapping(value = "/delete")
    public Result deleteFunImage(@NotNull Integer funImageId){
        return funImageService.deleteFunImage(funImageId);
    }

    @RequestMapping(value = "/list")
    public Result listFunImages(PageInfoDto pageInfoDto){
        return funImageService.listFunImages(pageInfoDto);
    }



    @Nullable
    private String uploadImg(MultipartFile img ) throws IOException {
        if (img==null){
            return null;
        }
        Result uploadResult = bosService.uploadArticleImg(img);
        //如果上传图片失败，直接返回
        if (uploadResult.getCode() != ResultCode.SUCCESS) {
            return null;
        }
        return uploadResult.getData().toString();
    }


}

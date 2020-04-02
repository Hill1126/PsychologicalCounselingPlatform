package org.gdou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.gdou.common.constant.ProjectConstant;
import org.gdou.common.result.Result;
import org.gdou.common.result.ResultGenerator;
import org.gdou.common.utils.RedisUtil;
import org.gdou.dao.ArticleMapper;
import org.gdou.dao.ConfigMapper;
import org.gdou.model.dto.PageInfoDto;
import org.gdou.model.po.Article;
import org.gdou.model.vo.ArticlePreviewVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/4/1
 **/
@Service
@Slf4j
public class ArticleService {

    private ArticleMapper articleMapper;
    private ConfigMapper configMapper;
    private RedisUtil redisUtil;

    public ArticleService(ArticleMapper articleMapper, ConfigMapper configMapper, RedisUtil redisUtil) {
        this.articleMapper = articleMapper;
        this.configMapper = configMapper;
        this.redisUtil = redisUtil;
    }

    public Result getArticleCategory(){
        List<String> categoryList = configMapper.getValueByName("article", "category");
        return ResultGenerator.genSuccessResult(categoryList);
    }

    public Result getArticlePreview(PageInfoDto pageInfoDto, String category) {
        PageHelper.startPage(pageInfoDto.getPageNum(),pageInfoDto.getPageSize());
        List<ArticlePreviewVo> articlePreview = articleMapper.getArticlePreview(category);
        return Result.genSuccessResult(PageInfo.of(articlePreview));
    }

    public Result getArticleById(Integer articleId){
        var article = (Article)redisUtil.hget(ProjectConstant.ARTICLE_KEY, articleId);
        if (article==null){
            //redis中不存在则从数据库获取拼放入redis中。
            article= articleMapper.selectByPrimaryKey(articleId);
            if (article!=null){
                redisUtil.hset(ProjectConstant.ARTICLE_KEY,articleId.toString(),article,ProjectConstant.ORDER_KEY_EXPIRE);
            }else{
                return Result.genNotFound("您要查看的文内容不存在");
            }
        }
        return Result.genSuccessResult(article);
    }


}

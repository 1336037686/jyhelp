package com.jyadmin.module.blog.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyadmin.domain.PageResult;
import com.jyadmin.domain.Result;
import com.jyadmin.annotation.Idempotent;
import com.jyadmin.annotation.RateLimit;
import com.jyadmin.module.blog.domain.Blog;
import com.jyadmin.module.blog.model.vo.BlogCreateReqVO;
import com.jyadmin.module.blog.model.vo.BlogQueryReqVO;
import com.jyadmin.module.blog.model.vo.BlogUpdateReqVO;
import com.jyadmin.module.blog.service.BlogService;
import com.jyadmin.util.DataUtil;
import com.jyadmin.util.PageUtil;
import com.jyadmin.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;
import java.util.Set;

/**
 * 博客文章 <br>
 * @author jyadmin code generate <br>
 * @version 1.0 <br>
 * Create by 2023-09-12 17:20:12 <br>
 * @description: BlogController <br>
 */
@Slf4j
@Api(value = "博客文章", tags = {"系统：博客文章"})
@RequestMapping("/api/blog")
@RestController
public class BlogController {

    @Resource
    private BlogService blogService;

    @RateLimit @Idempotent
    @ApiOperation(value = "新增博客文章", notes = "")
    @PostMapping("/create")
    @PreAuthorize("@jy.check('blog:create')")
    public Result<Object> doCreate(@RequestBody @Valid BlogCreateReqVO vo) {
        return ResultUtil.toResult(blogService.save(BeanUtil.copyProperties(vo, Blog.class)));
    }

    @RateLimit @Idempotent
    @ApiOperation(value = "更新博客文章", notes = "")
    @PutMapping("/update")
    @PreAuthorize("@jy.check('blog:update')")
    public Result<Object> doUpdate(@RequestBody @Valid BlogUpdateReqVO vo) {
        Blog blog = blogService.getById(vo.getId());
        BeanUtil.copyProperties(vo, blog);
        return ResultUtil.toResult(blogService.updateById(blog));
    }

    @ApiOperation(value = "删除博客文章", notes = "")
    @DeleteMapping("/remove")
    @PreAuthorize("@jy.check('blog:remove')")
    public Result<Object> doRemove(@RequestBody Set<String> ids) {
        return ResultUtil.toResult(blogService.removeByIds(DataUtil.convertToLongForSet(ids)));
    }

    @ApiOperation(value = "根据ID获取当前博客文章", notes = "")
    @GetMapping("/query/{id}")
    @PreAuthorize("@jy.check('blog:queryById')")
    public Result<Object> doQueryById(@PathVariable String id) {
        return Result.ok(blogService.getById(id));
    }

    @ApiOperation(value = "分页查询博客文章", notes = "")
    @GetMapping("/query")
    @PreAuthorize("@jy.check('blog:query')")
    public PageResult<Blog> doQueryPage(BlogQueryReqVO vo) {
        return PageUtil.toPageResult(
                this.blogService.page(new Page<>(vo.getPageNumber(), vo.getPageSize()),
                        new LambdaQueryWrapper<Blog>()
                            .like(StringUtils.isNotBlank(vo.getTitle()), Blog::getTitle, vo.getTitle())
                            .eq(Objects.nonNull(vo.getCategory()), Blog::getCategory, vo.getCategory())
                            .eq(Objects.nonNull(vo.getTag()), Blog::getTag, vo.getTag())
                            .eq(Objects.nonNull(vo.getStatus()), Blog::getStatus, vo.getStatus())
                            .like(StringUtils.isNotBlank(vo.getAuthor()), Blog::getAuthor, vo.getAuthor())
                )
        );
    }
}

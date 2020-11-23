package com.hdax.dm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hdax.dm.dao.DmItemCommentDao;
import com.hdax.dm.entity.item.DmItemComment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(path = {"/item/Comments"})
public class DmitemCommentImpl {
    @Resource
    private DmItemCommentDao dmItemCommentDao;


    /**
     * 商品剧评
     * @param id
     * @return
     */
    @PostMapping(path = "/getComments/{id}")
    public List<DmItemComment> getComments(@PathVariable("id")Long id){
        QueryWrapper<DmItemComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("itemId",id);
        return dmItemCommentDao.selectList(queryWrapper);
    }



}

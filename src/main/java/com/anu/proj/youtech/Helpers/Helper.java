package com.anu.proj.youtech.Helpers;

import com.anu.proj.youtech.Dtos.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    public  static <u,v>PageableResponse<v> getPageableResponse(Page<u> p,Class<v> type)
    {
        List<u> models=p.getContent();
        List<v> dtoList=models.stream().map(object->new ModelMapper().map(object,type)).collect(Collectors.toList());
        PageableResponse<v> res=new PageableResponse<>();
        res.setContent(dtoList);
        res.setPageNumber(p.getNumber());
        res.setPageSize(p.getSize());
        res.setTotalPage(p.getTotalPages());
        res.setLastPage(p.isLast());

        return res;
    }

}

package com.zhugs.auth.model.convert;

import com.zhugs.auth.model.dto.LoginDto;
import com.zhugs.auth.model.vo.LoginVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginConvert {

    LoginVo convert(LoginDto dto);

    LoginDto convert(LoginVo vo);


}

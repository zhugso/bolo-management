package com.zhugs.auth.model.convert;

import com.zhugs.auth.model.dto.LoginDto;
import com.zhugs.auth.model.vo.LoginReqVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginReqConvert {

    LoginReqVo convert(LoginDto dto);

    LoginDto convert(LoginReqVo vo);

}

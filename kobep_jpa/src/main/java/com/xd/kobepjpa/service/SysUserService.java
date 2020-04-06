package com.xd.kobepjpa.service;

import java.util.List;
import java.util.Map;

public interface SysUserService {

    List<Map<String, Object>> findByUserId(int userId) throws Exception;
}

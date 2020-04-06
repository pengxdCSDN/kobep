package com.xd.kobepcommon.core;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 统一API响应结果封装
 */


@Data
@Accessors(chain = true)
@EqualsAndHashCode
@NoArgsConstructor
public class Result<T> implements Serializable {
        /**
         * 返回的代码，200表示成功，其他表示失败
         */
        private int code;
        /**
         * 成功或失败时返回的错误信息
         */
        private String msg;
        /**
         * 成功时返回的数据信息
         */
        private Object data;

        public Result(int code, String msg, Object data){
            this.code = code;
            this.msg = msg;
            this.data = data;
        }

        /**
         * 推荐使用此种方法返回
         * @param resultCode 枚举信息
         * @param data 返回数据
         */
        public Result(ResultCode resultCode, Object data){
            this(resultCode.getCode(), resultCode.getMsg(), data);
        }

        public Result(int code, String msg){
            this(code, msg, null);
        }

        public Result(ResultCode resultCode){
            this(resultCode, null);
        }

        
}
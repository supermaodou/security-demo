package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_user")
@Schema(description = "用户实体")
public class SysUser {

    @TableId
    private Long id;
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "密码")
    private String password;
}

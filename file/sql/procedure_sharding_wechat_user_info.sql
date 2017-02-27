-- // interaction_rule alter column condition
-- Migration SQL that makes the change goes here.

DELIMITER $$
CREATE PROCEDURE p_sharding_wechat_user_info()
BEGIN

  DECLARE fans_total_count int; -- 需要分表的粉丝总数
  DECLARE min_table_name VARCHAR(30); -- 粉丝数最小的表
  DECLARE min_fans_count INT; -- 粉丝数最小的公众号粉丝数
  DECLARE curosr_wechat_public_number VARCHAR(30); -- 游标遍历所指定的公众号
  DECLARE curosr_fans_count INT; -- 游标遍历所指定的粉丝数量
  DECLARE dynamic_sql VARCHAR(1024); -- 动态sql
  DECLARE loop_count INT DEFAULT 0;
  DECLARE cursor_is_done INT DEFAULT 0 ; -- 游标遍历是否完成标志位 0:未完成 1:已完成
  DECLARE accounts_cursor CURSOR FOR -- 所有公众号对应的粉丝数游标
    select `wechat_public_number` , count(`openid`) as cut from `wechat_user_info_0` group by `wechat_public_number` order by cut desc;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET cursor_is_done = 1 ; -- 遇到 FOR NOT FOUND 异常时设置为 1，表示已经完成循环

  set fans_total_count = (select count(`openid`) from `wechat_user_info_0`); -- 设置总数

  -- 创建临时表，用于存放公众号和粉丝数量
  CREATE TEMPORARY TABLE IF NOT EXISTS temp_account_fans(table_name VARCHAR(30),fans_count INT(11));
  INSERT INTO temp_account_fans(table_name, fans_count) VALUES ('wechat_user_info_1',(select count(openid) from wechat_user_info_1));
  INSERT INTO temp_account_fans(table_name, fans_count) VALUES ('wechat_user_info_2',(select count(openid) from wechat_user_info_2));
  INSERT INTO temp_account_fans(table_name, fans_count) VALUES ('wechat_user_info_3',(select count(openid) from wechat_user_info_3));
  INSERT INTO temp_account_fans(table_name, fans_count) VALUES ('wechat_user_info_4',(select count(openid) from wechat_user_info_4));
  INSERT INTO temp_account_fans(table_name, fans_count) VALUES ('wechat_user_info_5',(select count(openid) from wechat_user_info_5));
  INSERT INTO temp_account_fans(table_name, fans_count) VALUES ('wechat_user_info_6',(select count(openid) from wechat_user_info_6));
  INSERT INTO temp_account_fans(table_name, fans_count) VALUES ('wechat_user_info_7',(select count(openid) from wechat_user_info_7));
  INSERT INTO temp_account_fans(table_name, fans_count) VALUES ('wechat_user_info_8',(select count(openid) from wechat_user_info_8));
  INSERT INTO temp_account_fans(table_name, fans_count) VALUES ('wechat_user_info_9',(select count(openid) from wechat_user_info_9));

  OPEN accounts_cursor;
    account_loop:LOOP

      -- 查到最后一个，循环结束
      IF cursor_is_done = 1 THEN
        SELECT CONCAT('数据迁移完成，共 ',loop_count,' 次循环');
        LEAVE account_loop;
      END IF;

      -- 获取分表中粉丝数最少一个表名
      SELECT table_name,fans_count INTO min_table_name,min_fans_count FROM temp_account_fans order by fans_count ASC limit 1;

      -- 查询分表中粉丝数最少的表的粉丝数比总数大，则不再进行分表，循环结束
      IF fans_total_count <= min_fans_count THEN
        SELECT CONCAT('数据迁移完成，共 ',loop_count,' 次循环');
        LEAVE account_loop;
      END IF;

      -- 获取游标中的公众号和粉丝数
      FETCH accounts_cursor INTO curosr_wechat_public_number , curosr_fans_count;

        -- 如果不加判断，会多循环一次。最后一次读取游标时，cursor_is_done已经是1，但是进入循环(FETCH)之前是0，故可以进入循环，会多循环一次。
        IF cursor_is_done = 0 THEN

          set loop_count = loop_count + 1;

          -- 迁移数据
          set dynamic_sql = CONCAT('insert into ',min_table_name,' select * from wechat_user_info_0 where wechat_public_number = \'',curosr_wechat_public_number,'\'');
          set @dynamic_sql = dynamic_sql;
          PREPARE dynamicStmt FROM @dynamic_sql;
          EXECUTE dynamicStmt;
          DEALLOCATE PREPARE dynamicStmt;

          -- 删除原数据
          DELETE FROM `wechat_user_info_0` WHERE `wechat_public_number` = curosr_wechat_public_number;

          -- 总数去除迁移部分的数据
          set fans_total_count = fans_total_count - curosr_fans_count;

          -- 加到分表纪录表中
          DELETE FROM `sharing_config_wechat_user_info` WHERE `wechat_public_number` = curosr_wechat_public_number;
          INSERT INTO sharing_config_wechat_user_info(`wechat_public_number`,`table_name`) VALUES (curosr_wechat_public_number,min_table_name);

          -- 更新临时表的粉丝数据
          UPDATE temp_account_fans SET fans_count = fans_count + curosr_fans_count where table_name = min_table_name;

        END IF;

    END LOOP account_loop;
  CLOSE accounts_cursor;

  SET cursor_is_done = 0; -- 复位

END $$

-- //@UNDO
-- SQL to undo the change goes here.
DROP procedure IF EXISTS p_sharding_wechat_user_info;

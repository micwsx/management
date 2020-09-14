DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getChildTable`(in parentId varchar(32))
BEGIN

    DECLARE sTemp VARCHAR(1000);
    DECLARE sTempChd VARCHAR(1000);
    DECLARE slevel smallint;

	drop table if exists temp_tb;
	create temporary table temp_tb(
			id varchar(32),
            refId varchar(32),
            name varchar(32),
            nickName varchar(256),
            headImgurl varchar(256),
            sharedQRcodeUrl nvarchar(512),
            subscribeTime datetime,
            level smallint
    );


    set slevel=0;
    SET sTemp = '$';
    SET sTempChd =cast(parentId as CHAR);
 insert into temp_tb
        SELECT id,refId,name,nickName, headImgurl,sharedQRcodeUrl,subscribeTime, slevel FROM member where id=parentid;
    WHILE sTempChd is not null DO
        SET sTemp = concat(sTemp,',',sTempChd);
        set slevel=slevel+1;
        insert into temp_tb
        SELECT id,refId,name,nickName,headImgurl,sharedQRcodeUrl,subscribeTime,slevel FROM member where FIND_IN_SET(refid,sTempChd)>0;
		SELECT group_concat(id) INTO sTempChd FROM  member where FIND_IN_SET(refid,sTempChd)>0;
	END WHILE;
    select t.*,r.name as p_name,r.nickName as p_nickName,r.headImgurl as p_headImgurl from temp_tb t left join member r on t.refId=r.id order by t.level;
END$$
DELIMITER ;

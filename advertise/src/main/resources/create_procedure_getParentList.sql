DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getParentList`(in childId varchar(32),out parentSet VARCHAR(1000))
BEGIN

    DECLARE parentList VARCHAR(1000);
    DECLARE tempParent VARCHAR(1000);

    SET parentList = '';
    SET tempParent =cast(childId as CHAR);
    WHILE tempParent is not null DO
        SET parentList = concat(parentList,',',tempParent);
		SELECT refId INTO tempParent FROM  member where id=tempParent;
	END WHILE;
	select substring(parentlist,2) into parentSet;
END$$
DELIMITER ;

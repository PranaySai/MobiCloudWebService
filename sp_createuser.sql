DROP PROCEDURE IF EXISTS lbsdb.sp_createuser;
CREATE PROCEDURE lbsdb.`sp_createuser`( in username varchar(15),in pwd varchar(15),in muid varchar(40),in xcord varchar(20),in ycord varchar(20),in mip varchar(20),out message varchar(15))
begin
  declare v_uid varchar(40);
  declare v_vmid int(11);
  start transaction;
  insert into usertable(username,password,mobileuid,MobileIP) values (username,pwd,muid,mip);
  select userid into v_uid from usertable where mobileuid=muid;
  select VMID into v_vmid from vmdetail where isAllocated=0 limit 1;
  update vmdetail set isAllocated=1 where VMID=v_vmid;
  insert into user_vm values(v_uid,v_vmid);
  insert into location(userid,xcord,ycord,updated_time) values (v_uid,xcord,ycord,now());
  SET message="SUCCESS";
  commit; 
end;

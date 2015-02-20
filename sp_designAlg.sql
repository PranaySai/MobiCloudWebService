CREATE PROCEDURE sp_decisionAlg(IN  fid                     int(11),
                                IN  mid                     varchar(40),
                                OUT v_rdist                 float(15, 5),
                                OUT v_filename              varchar(40),
                                OUT v_filepresent_vmip      varchar(20),
                                OUT v_filereq_vmip          varchar(20),
                                OUT v_filepresentmobileip   varchar(20),
                                OUT v_message               varchar(40))
   BEGIN
      DECLARE done INT DEFAULT FALSE;
      DECLARE v_filereq_user_id       int(11);
      DECLARE v_filepresent_user_id   int(11);
      DECLARE v_nuser_id   int(11);
      DECLARE v_curuser_id   int(11);
      DECLARE v_lat        varchar(20);
      DECLARE v_lon        varchar(20);
      DECLARE v_rlat       varchar(20);
      DECLARE v_rlon       varchar(20);
      DECLARE v_dist       float(15, 5);
      DECLARE v_curStat    varchar(10);
      DECLARE
         cur_loc CURSOR FOR
            SELECT u.userid, xcord, ycord,Current_Status
              FROM location l, userfilemapping u ,usertable t
             WHERE u.fileid = fid AND u.userid = l.userid AND t.userid=u.userid;

      DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

      SET v_rdist = 0;
      SET v_nuser_id = 0;
      SET v_filepresent_vmip = '';
      SET v_filereq_vmip = '';
      SET v_filepresentmobileip = '';

      SELECT filename
        INTO v_filename
        FROM filetable
       WHERE fileid = fid;

      SELECT userid
        INTO v_filereq_user_id
        FROM usertable
       WHERE mobileuid = mid;

      SELECT xcord, ycord
        INTO v_lat, v_lon
        FROM location
       WHERE UserId = v_filereq_user_id;

      OPEN cur_loc;

     read_loop:
      LOOP
         FETCH cur_loc
              INTO v_curuser_id, v_rlat, v_rlon, v_curStat;

         IF done
         THEN
            LEAVE read_loop;
         END IF;

         IF v_curStat = 'ON'
         THEN
            SELECT   3959
                   * acos(
                            cos(radians(CAST(v_rlat AS DECIMAL(10, 6))))
                          * cos(radians(CAST(v_lat AS DECIMAL(10, 6))))
                          * cos(
                                 radians(CAST(v_lon AS DECIMAL(10, 6)))
                               - radians(CAST(v_rlon AS DECIMAL(10, 6))))
                        +   sin(radians(CAST(v_rlat AS DECIMAL(10, 6))))
                          * sin(radians(CAST(v_lat AS DECIMAL(10, 6)))))
              INTO v_dist
              FROM dual;

            IF v_rdist >= v_dist
            THEN
               SET v_rdist := v_dist;
               SET v_nuser_id := v_curuser_id;
            END IF;
         END IF;
      END LOOP;

      CLOSE cur_loc;

      IF v_nuser_id = 0
      THEN
         SET v_nuser_id := v_curuser_id;
         SET v_message := 'VM2VM';
      ELSE
         SET v_message := 'CALCULATE';
      END IF;

      SET v_filepresent_user_id = v_nuser_id;

      SELECT IPAddress
        INTO v_filereq_vmip
        FROM vmdetail
       WHERE vmid = (SELECT VMID
                       FROM user_vm
                      WHERE UserID = v_filereq_user_id);



      SELECT IPAddress
        INTO v_filepresent_vmip
        FROM vmdetail
       WHERE vmid = (SELECT VMID
                       FROM user_vm
                      WHERE UserID = v_filepresent_user_id);

      SELECT MobileIP
        INTO v_filepresentmobileip
        FROM usertable
       WHERE UserID = v_filepresent_user_id;
   END;
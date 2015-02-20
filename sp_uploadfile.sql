CREATE PROCEDURE lbsdb.sp_uploadfile(IN  muid         varchar(40),
                                     IN  v_FileName   varchar(30),
                                     IN  fsize        float,
                                     IN  v_type       varchar(10),
                                     IN  Source       int(11),
                                     IN  SourceType   varchar(6),
                                     IN  xc           double,
                                     IN  yc           double,
                                     IN  v_filetype   varchar(20),
                                     OUT v_message    varchar(20))
   BEGIN
      DECLARE v_user_id   int(11);
      DECLARE v_file_id   int(11);
      DECLARE v_count     int(11);
      START TRANSACTION;

      SELECT userid
        INTO v_user_id
        FROM usertable
       WHERE mobileuid = muid;

      UPDATE Location
         SET xcord = xc, ycord = yc, updated_time = now()
       WHERE userid = v_user_id;

      SELECT count(*)
        INTO v_count
        FROM filetable
       WHERE filename = v_FileName AND FileMIMEType = v_filetype;

      IF v_count = 0
      THEN
         INSERT INTO filetable(filename,
                               filesize,
                               syncstatus,
                               type,
                               source,
                               sourcetype,
                               lastupdatedtime,
                               FileMIMEType)
         VALUES (v_FileName,
                 fsize,
                 'SYNCED',
                 v_type,
                 v_user_id,
                 SourceType,
                 now(),
                 v_filetype);

         SELECT max(fileid) INTO v_file_id FROM filetable;
      ELSE
         SELECT fileid
           INTO v_file_id
           FROM filetable
          WHERE filename = v_FileName AND FileMIMEType = v_filetype;
      END IF;

      INSERT INTO userfilemapping
      VALUES (v_user_id, v_file_id);

      COMMIT;
      SET v_message := 'SUCCESS';
   END;